package wk.jmschat;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.awt.event.*;
import java.util.Arrays;

/**
 * The JMSTopicControl class handles the communication between this Application and the Chat Server.
 * To do that, it utilises the <code>JMS Topic</code> technique, in order to simulate a behaviour similar to IRC Chatrooms.
 *
 * Once a message has arrived, the underlying JMSModel is getting updated.
 * This also happens in the case of an error/exception.
 *
 * @author Andreas Willinger
 * @version 0.6
 */
public class JMSTopicControl
    extends WindowAdapter
    implements MessageListener, Runnable, ActionListener
{
    // connection to the activemq server itself
	private Connection topicConnection;
    // a unique session, running on the connection above
	private Session topicSession;
    // used to send messages
	private MessageProducer topicSender;
    // used to receive messages
	private MessageConsumer topicReceiver;

    // general stuff
	private JMSOptions options;
	private JMSModel model;
	private Text text;

    @SuppressWarnings("UnusedDeclaration")
    public static String[] chatCommands = new String[]{"EXIT"};

	public JMSTopicControl(JMSModel model, Text textContainer, JMSOptions options)
    {
        this.options = options;
        this.model = model;
        this.text = textContainer;
	}


    /**
     *  Disconnect from the Chat Server
     */
	public void stop()
    {
        try
        {
            String sendMessage = String.format("%s@%s %s", this.options.getUsername(), this.options.getIp(), "hat den Chat verlassen.");
            TextMessage message = this.topicSession.createTextMessage(sendMessage);

            this.topicSender.send(message);

            this.topicReceiver.close();
            this.topicSender.close();
            this.topicSession.close();
            this.topicConnection.close();
        }
        catch(JMSException | NullPointerException e)
        {
            this.model.appendMessage("SYSTEM: Konnte Verbindung nicht trennen!");
        }
	}

    /**
     * Invoked when an action occurs.
     *
     * @param e An Object containing the Source and similar data
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String currentContent = this.text.getText();
        String[] split = currentContent.split(" ");

        if(split.length == 0) return;

        if(currentContent.equals("") || Arrays.asList(JMSMailControl.KEYWORDS).contains(split[0].trim().toUpperCase()))
        {
            // ignore it
            //noinspection UnnecessaryReturnStatement
            return;
        }
        else if(split[0].trim().equalsIgnoreCase("exit"))
        {
            this.text.close();
        }
        // regular chat message
        else
        {
            if(currentContent.length() < 1000)
            {
                try
                {
                    String sendMessage = String.format("%s@%s: %s", this.options.getUsername(), this.options.getIp(), currentContent);
                    TextMessage message = this.topicSession.createTextMessage(sendMessage);

                    this.topicSender.send(message);
                    this.text.clearText();
                }
                catch(JMSException | NullPointerException ex)
                {
                    this.model.appendMessage("SYSTEM: Fehler beim Senden der Nachricht! Bitte ueberpruefen Sie Ihre Netzwerkverbindung!");
                }
            }
            else
            {
                this.model.appendMessage("Ihre Nachricht ist zu lang ("+currentContent.length()+" Zeichen, erlaubt: 1000 Zeichen)!");
            }
        }
    }

    /**
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */

    @Override
    public void onMessage(Message message)
    {
        TextMessage msg = (TextMessage) message;

        if(msg != null)
        {
            try
            {
                String text = msg.getText();
                this.model.appendMessage(text);
            }
            catch(JMSException e)
            {
                this.model.appendMessage("SYSTEM: Fehler beim Empfangen der Nachricht!");
            }
        }
    }

    /**
     * @see java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent)
     */
    @Override
    public void windowClosing(WindowEvent e)
    {
        this.stop();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        try
        {
            // set up connection
            this.topicConnection = new ActiveMQConnectionFactory(this.options.getHost()).createConnection();

            // create a sesssion & destination
            this.topicSession = this.topicConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination topicDestination = this.topicSession.createTopic(this.options.getChannel());

            // open up sender & receiver streams and set the delivery mode to non-persistent, since we use JMS as a Chatroom
            this.topicReceiver = this.topicSession.createConsumer(topicDestination);
            this.topicSender = this.topicSession.createProducer(topicDestination);
            this.topicSender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // finally, fire up the connection
            this.topicReceiver.setMessageListener(this);
            this.topicConnection.start();

            String sendMessage = String.format("%s@%s %s", this.options.getUsername(), this.options.getIp(), "ist dem Chat beigetreten.");
            TextMessage message = this.topicSession.createTextMessage(sendMessage);

            this.topicSender.send(message);
        }
        catch(JMSException | NullPointerException e)
        {
            this.model.appendMessage("SYSTEM: Fehler beim Herstellen der Verbindung zum Chat-Server!");
        }
    }
}
