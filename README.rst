#######
JMSChat
#######

======
Angabe
======


Implementieren Sie eine Chatapplikation mit Hilfe des Java Message Service. 
Verwenden Sie Apache ActiveMQ (http://activemq.apache.org) als Message Broker 
Ihrer Applikation. Das Programm soll folgende Funktionen beinhalten:

- Benutzer meldet sich mit einem Benutzernamen und dem Namen des Chatrooms an. 
  Beispiel für einen Aufruf:

.. code:: plain

	vsdbchat <ip_message_broker> <benutzername> <chatroom>

- Der Benutzer kann in dem Chatroom (JMS Topic) Nachrichten an alle Teilnehmer 
  eine Nachricht senden und empfangen.
  Die Nachricht erscheint in folgendem Format:

.. code:: plain

	<benutzername> [<ip_des_benutzers>]: <Nachricht>

- Zusätzlich zu dem Chatroom kann jedem Benutzer eine Nachricht in einem 
  persönlichen Postfach (JMS Queue) hinterlassen werden. Der Name des Postfachs
  ist die IP Adresse des Benutzers (Eindeutigkeit).

- Nachricht an das Postfach senden:

.. code:: plain

	MAIL <ip_des_benutzers> <nachricht>

- Eignes Postfach abfragen:

.. code:: plain

	MAILBOX

- Der Chatraum wird mit den Schlüsselwort EXIT verlassen. Der Benutzer 
  verlaesst den Chatraum, die anderen Teilnehmer sind davon nicht betroffen.

Gruppenarbeit: Die Arbeit ist in einer 2er-Gruppe zu lösen und über das 
Netzwerk zu testen! Abnahmen, die nur auf localhost basieren sind unzulässig 
und werden mit 6 Minuspunkten benotet!

================
Designüberlegung
================

- GUI

  - Großes Textfeld (mitte, oben) für alle Nachrichten in diesem Topic
  - Einzeiliges Textfeld unten für Benutzereingabe
  - Button rechts von Eingabefeld zum Absenden
- Channel (JMS-Topic)

  - Sender & Receiver laufen in einem extra Thread, um die GUI nicht zu blockieren
  - Verwendung des Observer Patterns mithilfe von onMessage (bei MessageConsumer)
    Wenn Nachricht ankommt: Aktualisierung des großen Textfeldes in der GUI
  - Extra Thread stellt Methode zum Senden von Nachrichten zur Verfügung, Controller hat Referenz auf dessen Objekt
- Mailbox (JMS-Queue)

  - Sender & Receiver laufen wie bei Topic in einem extra Thread
  - Wenn eine neue Nachricht (über onMessage) empfangen wurde, wird diese in einer ArrayList abgelegt
  - Bei Abfrage der Mailbox (mithilfe des Kommandos "MAILBOX" in der GUI), wird die ArrayList ausgelesen und deren 
    Inhalt im großen Textfeld farbig hinterlegt angezeigt.
  - Da es auf einer IP auch mehrere Benutzer geben kann, muss der Benutzername beim Senden einer Nachricht mit 
    angegeben werden.
  Aufruf demnach: MAIL <benutzername>@<ip_des_benutzers> <nachricht>
- Beenden
  
  Beim Beenden müssen alle offenen Verbindungen in den jeweiligen Threads geschlossen werden, anschließend kann 
  erst das Programm beendet werden.

.. image:: doc/JMSChat.png
   :width: 90%

================
Aufwandschätzung
================

+-------------------------------+---------------+-------------+--------------------+
| Arbeitspaket                  | Geplante Zeit |   Aufwand   | Wer                |
+-------------------------------+---------------+-------------+--------------------+
|                               |     [H:MM]    |             |                    |
+===============================+===============+=============+====================+
| UML                           |      1:30     |   Mittel    | Jakob Klepp        |
+-------------------------------+---------------+-------------+--------------------+
| GUI                           |      1:00     |   Gering    | Andreas Willinger  |
+-------------------------------+---------------+-------------+--------------------+
| Chat Befehle                  |      0:30     |   Gering    | Andreas Willinger  |
+-------------------------------+---------------+-------------+--------------------+
| Channel (JMS-Topic)           |      2:00     |   Mittel    | Andreas Willinger  |
+-------------------------------+---------------+-------------+--------------------+
| Option parsing                |      0:40     |   Gering    | Jakob Klepp        |
+-------------------------------+---------------+-------------+--------------------+
| Mailbox (JMS-Queue)           |      2:00     |   Mittel    | Jakob Klepp        |
+-------------------------------+---------------+-------------+--------------------+
| Testing                       |      0:30     |   Gering    | Jakob Klepp /      |
|                               |               |             | Andreas Willinger  |
+-------------------------------+---------------+-------------+--------------------+

================
Zeitaufzeichnung
================

+----------------------------+--------------+---------+---------+-----------+--------------------+
| Arbeitspaket               | Datum        | Start   | Ende    | Dauer     | Wer                |
+----------------------------+--------------+---------+---------+-----------+--------------------+
|                            | [YYYY-MM-DD] | [HH:MM] | [HH:MM] |    [H:MM] |                    |
+============================+==============+=========+=========+===========+====================+
| UML                        |  2014-02-11  |  13:00  |  13:40  |     0:40  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| UML überarbeitet           |  2014-02-11  |  13:40  |  14:30  |     0:40  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| UML überarbeitet           |  2014-02-11  |  13:40  |  14:30  |     0:40  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Importing stuff from Astah |  2014-02-11  |  15:10  |  15:35  |     0:25  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Junit stubs                |  2014-02-11  |  15:35  |  16:00  |     0:25  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Argumentparser             |  2014-02-11  |  16:00  |  17:20  |     1:20  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| GUI                        |  2014-02-11  |  17:00  |  17:30  |     0:30  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Ant Buildfile              |  2014-02-11  |  17:40  |  18:40  |     1:20  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Channel(JMS-Topic)         |  2014-02-11  |  17:40  |  18:00  |     0:20  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Channel(JMS-Topic)         |  2014-02-11  |  18:05  |  18:30  |     0:30  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Chat Befehle (Topic)       |  2014-02-11  |  18:30  |  18:35  |     0:05  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Channel(JMS-Topic)         |  2014-02-12  |  09:35  |  09:55  |     0:20  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Channel(JMS-Topic)/GUI     |  2014-02-12  |  11:20  |  11:35  |     0:15  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Mail system                |  2014-02-11  |  17:40  |  18:00  |     0:20  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Testing                    |  2014-02-12  |  17:10  |  18:00  |     0:50  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Mail system                |  2014-02-12  |  21:50  |  24:00  |     1:10  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Mail system                |  2014-02-13  |  00:00  |  00:15  |     0:15  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Channel/Mail/GUI (fixing)  |  2014-02-13  |  07:55  |  08:50  |     0:55  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| UML                        |  2014-02-13  |  13:55  |  14:10  |     0:15  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Testing                    |  2014-02-13  |  20:00  |  20:30  |     0:30  | Andreas Willinger  |
+----------------------------+--------------+---------+---------+-----------+--------------------+
| Testing                    |  2014-02-13  |  20:00  |  20:30  |     0:30  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+

=========
Umsetzung
=========

~~~~~~~~
ActiveMQ
~~~~~~~~

Wir verwenden als Host für ActiveMQ einen von uns gemieteten vServer in Deutschland, da das Schulnetzwerk bei VMs
sehr unzuverlässig ist.

Zu aller erst muss Java installiert werden:

.. code:: bash

    apt-get install openjdk-7-jre-headless

Nun kann ActiveMQ heruntergeladen & entpackt werden.
Wir verwenden die bereits vorkompilierte (binäre) Variante.

.. code:: bash

    mkdir /root/activemq && cd /root/activemq
    wget http://tweedo.com/mirror/apache/activemq/apache-activemq/5.9.0/apache-activemq-5.9.0-bin.tar.gz
    tar xfvz apache-activemq-5.9.0-bin.tar.gz

Anschließend wird noch die Standard Konfigurationsdatei angelegt und wie folgt bearbeitet:
Dies wird benötigt, da Java standardmäßig IPv6 benutzt, falls es verfügbar ist.

.. code:: bash

    cd apache-activemq-5.9.0/bin/
    ./activemq setup /etc/default/activemq
    
    vim /etc/default/activemq

.. code:: plain

    [..]
    ACTIVEMQ_OPTS="$ACTIVEMQ_OPTS -Djava.net.preferIPv4Stack=true"
    [..]
    
Zum Schluss kann ActiveMQ gestartet werden:

.. code:: bash

    ./activemq start

**Ausgabe**:

.. code:: bash

    INFO: Loading '/etc/default/activemq'
    INFO: Using java '/usr/bin/java'
    INFO: Starting - inspect logfiles specified in logging.properties and log4j.properties to get details
    INFO: pidfile created : '/root/activemq/apache-activemq-5.9.0/data/activemq-mail.f-o-g.eu.pid' (pid '2136')
    INFO: Loading '/etc/default/activemq'
    INFO: Using java '/usr/bin/java'
    ActiveMQ is running (pid '2136')


~~~~~~~~~~~~~~~~~~~~~~
Synchronized ArrayList
~~~~~~~~~~~~~~~~~~~~~~

Da wir zwei verschiedene Threads für den Chatroom selbst und die Mailbox verwenden und diese auf
dasselbe JMSModel zugreifen, müssen wir eine Thread-safe Collection verwenden.

Unsere Wahl fiel dazu auf eine Synchronized ArrayList, welche wie folgt (ähnlich zu einer normalen ArrayList)
deklariert & initialisert werden kann:

.. code:: java

    public List<String> myList = Collections.synchronizedList(new ArrayList<String>());

Anschließend kann myList ganz normal wir eine ArrayList verwendet werden, mit dem Unterschied, dass alle
Abfragen (einfügen, löschen, abrufen) synchronized (d.h. blocking) ablaufen.

~~~~~~~~~~
JCommander
~~~~~~~~~~

Zum parsen von Kommandozeilen Argumenten verwenden wir JCommander.
Die Entscheidung fiel auf JCommander da er noch aktiv weiter entwickelt wird
und die einbindung nur eine Minimale menge Code benötigt.

Verwendungsbeispiel aus der offiziellen Dokumentation _[10]

**Argumentparser**

.. code:: java

    public class JCommanderTest {
        @Parameter
        public List<String> parameters = Lists.newArrayList();

        @Parameter(names = { "-log", "-verbose" }, description = "Level of verbosity")
        public Integer verbose = 1;

        @Parameter(names = "-groups", description = "Comma-separated list of group names to be run")
        public String groups;

        @Parameter(names = "-debug", description = "Debug mode")
        public boolean debug = false;

        @DynamicParameter(names = "-D", description = "Dynamic parameters go here")
        public Map<String, String> dynamicParams = new HashMap<String, String>();

    }

**Verwendung des Parsers**

.. code:: java

    JCommanderTest jct = new JCommanderTest();
    String[] argv = { "-log", "2", "-groups", "unit1,unit2,unit3",
                        "-debug", "-Doption=value", "a", "b", "c" };
    new JCommander(jct, argv);

    Assert.assertEquals(2, jct.verbose.intValue());
    Assert.assertEquals("unit1,unit2,unit3", jct.groups);
    Assert.assertEquals(true, jct.debug);
    Assert.assertEquals("value", jct.dynamicParams.get("option"));
    Assert.assertEquals(Arrays.asList("a", "b", "c"), jct.parameters);

~~~~~~~~
Redesign
~~~~~~~~

Im Laufe der Entwicklung haben wir festgestellt, dass das schließend (via EXIT) ein kleines Redesign benötigt.
Dazu verwenden wir nun einen WindowListener (bzw. WindowAdapater, da wir nur eine Methode benötigen) um das windowClosing Event zu verarbeiten.
Außerdem wurde das Text Interface (welches vom JMSView implementiert wird) erweitert, um clearText() und close() Methoden hinzuzufügen.

Bei Aufruf der close() Methode wird ein windowClosing Event manuell ausgelöst, dadurch werden die stop() Methoden in der Channel und Mailbox Klasse aufgerufen.
Anschließend wird das Fenster geschlossen.
Beide Klassen werden außerdem in Threads ausgeführt, um das Programm selbst nicht zu behindern.

Die clearText() Methode löscht einfach das Kommandofeld.

=======
Testing
=======

Da wir ja einen VPS im Internet verwenden, haben wir das Testen von zu Hause aus durchgeführt. 
Der Grund dahinter ist, dass die Schul-Firewall den Port 61616 sperrt und dieser nur über einen SSH Tunnel erreichbar wäre.
Dadurch hätten wir beide auch die selbe Ip (MAILBOX Test wäre aufgrund dessen nicht aussagekräftig).

Zugewiesene IPs (obwohl die benutzernamen relativ eindeutig sein sollten):

**213.47.167.184** - Andreas Willinger

**62.178.207.178** - Jakob Klepp

~~~~~~~
Starten
~~~~~~~

Der Aufruf zum starten ist bei beiden beinahe der selbe, mit dem Unterschied, dass die Benutzernamen unterschiedlich sind.
Anschließend wird direkt eine Nachricht an alle bereits vorhandenen Teilnehmer gesendet, um den neuen Teilnehmer anzukündigen:

.. image:: images/Klepp_Join.jpg
   :width: 757px
   :height: 394px
   
.. image:: images/Willinger_Start.jpg
   :width: 677px
   :height: 342px

.. image:: images/Willinger_Join.jpg
   :width: 800px
   :height: 600px

Nun kann freudig losgechattet werden und sobald einer den Raum wieder verlässt, wird dies auch angekündigt:

.. image:: images/Klepp_Leave.jpg
   :width: 800px
   :height: 600px

.. image:: images/Willinger_Leave.jpg
   :width: 800px
   :height: 600px

Es können auch Nachrichten direkt an einen anderen Benutzer gesandt werden:

**Absender**

.. image:: images/Klepp_Mail.jpg
   :width: 758px
   :height: 396px

**Empfänger**

.. image:: images/Willinger_Mailbox.jpg
   :width: 800px
   :height: 600px

Der Chat kann auch verlassen werden, ohne die anderen Teilnehmer zu beeinträchtigen (Nachrichten an die Mailbox können später
abgerufen werden):
(für Beweis, siehe 2. Punkt)

.. image:: images/Klepp_Exit.jpg
   :width: 754px
   :height: 396px

Statstiken im Webinterface:

**Queues**

.. image:: images/Willinger_Queues.jpg
   :width: 630px
   :height: 211px

**Topics**

.. image:: images/Willinger_Topics.jpg
   :width: 626px
   :height: 242px

=======
Quellen
=======


.. _1:

[1]  Homepage ActiveMQ
     http://activemq.apache.org/index.html
     zuletzt besucht am: 

.. _2:

[2]  
     http://www.academictutorials.com/jms/jms-introduction.asp
     zuletzt besucht am: 

.. _3:

[3]  
     http://docs.oracle.com/javaee/1.4/tutorial/doc/JMS.html#wp84181
     zuletzt besucht am: 

.. _4:

[4]  
     http://www.openlogic.com/wazi/bid/188010/How-to-Get-Started-with-ActiveMQ
     zuletzt besucht am: 

.. _5:

[5]  
     http://jmsexample.zcage.com/index2.html
     zuletzt besucht am: 

.. _6:

[6]  http://www.onjava.com/pub/a/onjava/excerpt/jms_ch2/index.html
     zuletzt besucht am: 

.. _7:

[7]  http://www.oracle.com/technetwork/systems/middleware/jms-basics-jsp-135286.html
	 zuletzt besucht am: 

.. _8:

[8]  Java JMS With A Queue Programming Reference and Examples
     http://www.fluffycat.com/Java/JMS-With-A-Queue/
     zuletzt besucht am: 10.02.2014

.. _9:

[9]  Java Message Service: Chapter 2: Developing a Simple Example
     http://oreilly.com/catalog/javmesser/chapter/ch02.html
     zuletzt besucht am: 10.02.2014

.. _10:

[10] JCommander
     http://www.jcommander.org/
     zuletzt besucht am: 11.02.2014

.. header::

    +-------------+-------------------+------------+
    | Titel       | Autor             | Date       |
    +=============+===================+============+
    | ###Title### | Andreas Willinger | 13.02.2014 |
    |             | -- Jakob Klepp    |            |
    +-------------+-------------------+------------+

.. footer::

    ###Page### / ###Total###
