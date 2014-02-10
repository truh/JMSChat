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

.. code:: 

	vsdbchat <ip_message_broker> <benutzername> <chatroom>

- Der Benutzer kann in dem Chatroom (JMS Topic) Nachrichten an alle Teilnehmer 
  eine Nachricht senden und empfangen.
  Die Nachricht erscheint in folgendem Format:

.. code:: 

	<benutzername> [<ip_des_benutzers>]: <Nachricht>

- Zusätzlich zu dem Chatroom kann jedem Benutzer eine Nachricht in einem 
  persönlichen Postfach (JMS Queue) hinterlassen werden. Der Name des Postfachs
  ist die IP Adresse des Benutzers (Eindeutigkeit).

- Nachricht an das Postfach senden:

.. code:: 

	MAIL <ip_des_benutzers> <nachricht>

- Eignes Postfach abfragen:

.. code:: 

	MAILBOX

- Der Chatraum wird mit den Schlüsselwort EXIT verlassen. Der Benutzer 
  verlaesst den Chatraum, die anderen Teilnehmer sind davon nicht betroffen.

Gruppenarbeit: Die Arbeit ist in einer 2er-Gruppe zu lösen und über das 
Netzwerk zu testen! Abnahmen, die nur auf localhost basieren sind unzulässig 
und werden mit 6 Minuspunkten benotet!

================
Designüberlegung
================

================
Aufwandschätzung
================

+-------------------------------+---------------+-------------+
| Arbeitspaket                  | Geplante Zeit |   Aufwand   |
+-------------------------------+---------------+-------------+
|                               |     [H:MM]    |             |
+===============================+===============+=============+
|                               |      0:40     |             |
+-------------------------------+---------------+-------------+

================
Zeitaufzeichnung
================

+----------------------------+--------------+---------+---------+-----------+
| Arbeitspaket               | Datum        | Start   | Ende    | Dauer     |
+----------------------------+--------------+---------+---------+-----------+
|                            | [YYYY-MM-DD] | [HH:MM] | [HH:MM] |    [H:MM] |
+============================+==============+=========+=========+===========+
|                            |  2013-01-23  |  12:50  |  13:20  |     0:30  |
+----------------------------+--------------+---------+---------+-----------+ 

=========
Umsetzung
=========

~~~~~~~~
ActiveMQ
~~~~~~~~

=======
Testing
=======

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

.. header::

    +-------------+-------------------+------------+
    | Titel       | Autor             | Date       |
    +=============+===================+============+
    | ###Title### | Andreas Willinger | 10.02.2014 |
    |             | -- Jakob Klepp    |            |
    +-------------+-------------------+------------+

.. footer::

    ###Page### / ###Total###