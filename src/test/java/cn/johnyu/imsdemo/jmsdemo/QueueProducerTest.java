package cn.johnyu.imsdemo.jmsdemo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;

public class QueueProducerTest {
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://localhost:61616");
		Destination queue=new ActiveMQQueue("john.queue");
		//Destination queue=new ActiveMQTopic("john.queue");
		Connection con=factory.createConnection();
		con.start();
		Session sen=con.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		MessageProducer producer=sen.createProducer(queue);
		TextMessage msg1=sen.createTextMessage("I am john!");
		TextMessage msg2=sen.createTextMessage("I am john!");
		producer.send(msg1);
		producer.send(msg2);
		//msg2.acknowledge();
		producer.close();
		sen.close();
		con.close();
	}
}
