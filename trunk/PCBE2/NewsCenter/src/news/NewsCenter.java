/*
 * Sistem de stiri
 *
 * Aplicatia implementeaza un sistem de stiri orientat pe evenimente. Un
 * eveniment este aparitia, modificarea sau stergerea unei stiri, iar
 * stirile sunt organizate in domenii si un numar arbitrar de subdomenii.
 * Stirile au si alte atribute cum ar fi: data primei publicari, data
 * ultimei modificari, sursa de informatie, autorul articolului etc.

 * Actorii din sistem sunt de doua tipuri: editori de stiri si cititori.
 * Editorii trebuie sa poata afla in timp real care este numarul de
 * cititori pentru stirile de interes. Pentru aceasta, ei se pot declara
 * interesati de aparitia unui eveniment gen "stire citita". Cititorii se
 * pot abona la una sau mai multe stiri, specificand domeniile de interes
 * si alte atribute (data, sursa etc.).
 * 
 */

package news;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.*;
import javax.jms.*;

import java.util.Properties;

public class NewsCenter {

	 public void init() throws NamingException, JMSException {

	        Properties env = new Properties();
	        //env.setProperty("TopicConnectionFactory", "TopicConnectionFactory");
	        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
	        //env.put(Context.INITIAL_CONTEXT_FACTORY, "javax.jms.TopicConnectionFactory");
	        env.put(Context.PROVIDER_URL,"tcp://localhost:61616");
	        //env.put(Context.PROVIDER_URL,"http://localhost:7676/");
	        //env.put(Context.OBJECT_FACTORIES, "topicConnectionFactory");
	        env.put("topic.NewsCenter", "NewsCenter");
	        env.put("ClientID", "123");
	        Context ctx = new InitialContext(env);

	        //QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) ctx.lookup("QueueConnectionFactory");

	        TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx.lookup("TopicConnectionFactory");

	        Topic myTopic = (Topic) ctx.lookup("NewsCenter");
	        

	        //Queue myQueue = (Queue) ctx.lookup("MyQueue");

	        //QueueConnection queueConnection = queueConnectionFactory.createQueueConnection();

	        TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();

	        //queueConnection.start();

	        

	        TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
	        
	        //Topic myTopic = topicSession.createTemporaryTopic();

	        //QueueSession queueSession = queueConnection.createQueueSession(true, 0);

	        //QueueSender queueSender = queueSession.createSender(myQueue);

	        TopicPublisher topicPublisher = topicSession.createPublisher(myTopic);

	        TopicSubscriber topicSubscriber = topicSession.createDurableSubscriber(myTopic, "user1");
	        

	        TopicListener topicListener = new TopicListener();

	        topicSubscriber.setMessageListener(topicListener);

	        topicConnection.start();

	        TextMessage message = topicSession.createTextMessage();
	        message.setText("Hello");
	        topicPublisher.send(message);
	        
	        //topicConnection.close();
	    }

	 	public void init2() throws JMSException {
	 		
	 		
	 		TopicConnectionFactory tcf = new com.sun.messaging.TopicConnectionFactory();
	 		
	 		TopicConnection tc = tcf.createTopicConnection();
	 		
	 		tc.setClientID("abc123");
	 		
	 		TopicSession ts = tc.createTopicSession(false,	Session.AUTO_ACKNOWLEDGE);
	 		
	 		Topic myTopic = ts.createTopic("NewsCenter");
	 		
	 		TopicPublisher tpub = ts.createPublisher(myTopic);
	 		
	 		TopicSubscriber tsub = ts.createDurableSubscriber(myTopic, "user1");
	 		
	 		TopicListener tl = new TopicListener();
	 		
	 		tsub.setMessageListener(tl);
	 		
	 		tc.start();
	 		
	 		TextMessage msg = ts.createTextMessage();
	 		msg.setText("Hello World!");
	 		tpub.send(msg);
	 	}
	 
	    public static void main(String args[]) {
	        try {
	            new NewsCenter().init2();
	        } catch (JMSException ex) {
	            Logger.getLogger(NewsCenter.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
}
