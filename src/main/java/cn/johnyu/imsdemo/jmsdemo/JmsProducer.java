package cn.johnyu.imsdemo.jmsdemo;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProducer {

  public static void main(String[] args) throws Exception{
		//JEE规范需要从JNDI上获取，线程安全
		//failover协议表示断开后将自动重新连接
		ConnectionFactory factory=new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
		//线程安全
		Connection con=factory.createConnection();
		con.start();//尝试连接，获取clientID,在获取session时也会自动start();
		Session sen=con.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//JEE规范需要从JNDI上获取
		Destination queue=sen.createQueue("financial001");
		Destination queue1=sen.createQueue("financial002");
		MessageProducer producer=sen.createProducer(queue);
		//producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//非持久化消息发送
		producer.setPriority(2);//0~9，高代表优先
		Message msg=sen.createTextMessage("this is 5th Message!!!");
		//msg.setJMSCorrelationID("ID:bogon-54681-1456208991021-1:1:1:1:1"); 设置关联的消息ID
		//msg.setJMSReplyTo(queue1);//通知消费者，使用某一Destination应答
		msg.setJMSRedelivered(true); //此属性只是表明消息是否被重传了（当消息接收未被确认--事务提交、消息确认，此是将会由MQ自动重传消息）
		msg.setJMSType("attac3");// 可选
		msg.setStringProperty("uname", "john");//传递消息另一种方式,byte,short,long,int,double..
		msg.setObjectProperty("uname", "john");//只能使以上类型，不能传递业务对象
		producer.send(msg);
		//sen.commit();//事务
		//sen.rollback();
		//msg.getJMSMessageID(); 发送后，拥有自己的ID
		con.close();

	}

}
