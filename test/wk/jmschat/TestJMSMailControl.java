package wk.jmschat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.jms.Message;
import java.awt.event.ActionEvent;

/**
 * Unittest fpr JMSMailControl
 *
 * @author Jakob Klepp
 */
public class TestJMSMailControl {
    JMSModel model;
    Text text;
    JMSOptions options;

    JMSMailControl mailControl;
    @Before
    public void before() {
        //create JMSModel stub
        model = ;
        //create Text stub
        text = ;
        //create JMSOptions stub
        options = ;
        //create an instance of the tested object
        mailControl = new JMSMailControl(model, text, options);
    }

    @Test
    public void test_stop() {
        Assert.fail("Not implemented!");
    }

    @Test
    public void test_actionPerformed(ActionEvent e) {
        Assert.fail("Not implemented!");
    }

    @Test
    public void test_onMessage(Message message) {
        Assert.fail("Not implemented!");
    }

    @Test
    public void test_run() {
        Assert.fail("Not implemented!");
    }

    @After
    public void after() {
        Assert.fail("Not implemented!");
    }
}
