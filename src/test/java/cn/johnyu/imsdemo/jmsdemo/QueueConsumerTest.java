package cn.johnyu.imsdemo.jmsdemo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;

public class QueueConsumerTest {
	public static void main(String[] args) throws Exception{
		ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://localhost:61616");
		Destination queue=new ActiveMQQueue("john.queue");
		Connection con=factory.createConnection();
		con.start();
		Session sen=con.createSession(true, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer consumer=sen.createConsumer(queue);
		
		TextMessage msg1=(TextMessage) consumer.receive();
		System.out.println(msg1.getText());
		//int m=1/0;
		TextMessage msg2=(TextMessage) consumer.receive();
		System.out.println(msg2.getText());
		sen.commit();
		consumer.close();
		sen.close();
		con.close();
	}
	
	
	public static void main1(String[] args) throws Exception{
		ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://localhost:61616");
		Destination queue=new ActiveMQQueue("john.queue");
		Connection con=factory.createConnection();
		con.start();
		Session sen=con.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageConsumer consumer=sen.createConsumer(queue);
		
		consumer.setMessageListener(new MessageListener() {
			@Override
			public void onMessage(Message arg0) {
				TextMessage msg=(TextMessage)arg0;
				try {
					System.out.println(msg.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
				
			}
		});
		System.out.println("main is here......");
		Thread.sleep(20000);
//		consumer.close();
//		sen.close();
//		con.close();
	}
}
