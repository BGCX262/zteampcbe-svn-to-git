package news;

import javax.jms.*;

public class Subscriber {
	
	protected String name = null;
	
	protected TopicConnection topicConnection = null;
	protected TopicConnectionFactory topicConnectionFactory = null;
	protected TopicSession topicSession = null;
	protected Topic newsTopic = null;
	protected TopicSubscriber topicSubscriber = null;
	private NewsListener topicListener = null;
	
	protected TopicPublisher topicPublisher = null;
	
	protected NewsAttributes newsAttributes[];
	
	
	private class NewsListener implements MessageListener{

		public void onMessage(Message msg) {
					
			try {
				System.out.println("\n----------------------------------------------------------\n" + 
						name + " - News Received - " + msg.getStringProperty("NewsType") + "\n" +
						msg.getStringProperty("NewsTitle") + " by " + msg.getStringProperty("NewsAuthor") + "\n" + 
						msg.getStringProperty("NewsDomain") + "\n" +
						"published on: " + msg.getStringProperty("NewsDate") + " / last modified: " + msg.getStringProperty("NewsLastDate"));
				
				if(msg instanceof TextMessage) {
					System.out.println(((TextMessage)msg).getText());
				}
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			try {
				if(!msg.getStringProperty("NewsType").equals(NewsSelector.NEWS_TYPES[2])) {
					
					Message readNews = this.copyMessageProps(msg);
					readNews.setStringProperty("NewsType",NewsSelector.NEWS_TYPES[3]);	
					topicPublisher.publish(readNews);
				}
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private Message copyMessageProps(Message msg) {
			
			Message copy = null;
			try {
				copy = topicSession.createMessage();
				
				copy.setStringProperty("NewsType", msg.getStringProperty("NewsType"));
				copy.setStringProperty("NewsTitle", msg.getStringProperty("NewsTitle"));
				copy.setStringProperty("NewsDomain", msg.getStringProperty("NewsDomain"));
				copy.setStringProperty("NewsAuthor", msg.getStringProperty("NewsAuthor"));
				copy.setStringProperty("NewsDate", msg.getStringProperty("NewsDate"));
				copy.setStringProperty("NewsLastDate", msg.getStringProperty("NewsLastDate"));
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return copy;
		}
		
	}
	
	
	public Subscriber(String name, NewsAttributes newsAttributes[]) {
	
		this.name = name;
		this.newsAttributes = newsAttributes;
		
		try {

			this.topicConnectionFactory = new com.sun.messaging.TopicConnectionFactory();
			this.topicConnection = this.topicConnectionFactory.createTopicConnection();
			this.topicConnection.setClientID(this.name);
			
			this.topicSession = this.topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			this.newsTopic = this.topicSession.createTopic("NewsCenter");
			
			this.topicPublisher = this.topicSession.createPublisher(this.newsTopic);
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			if(this.topicConnection != null) {
				try {
					this.topicConnection.close();
				} catch (JMSException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	
	public void startSubscriber() {
		
		String selector = "";
		for(int i = 0; i < this.newsAttributes.length; i++){
			
			NewsAttributes attr = this.newsAttributes[i];
			
			selector = selector.concat("(");
			selector = selector.concat(attr.getSQLSyntaxAttributes());
			selector = selector.concat(")");
			if(i < (this.newsAttributes.length - 1) ) {
				selector = selector.concat(" OR ");
			}
		}
		
		try {
			this.topicConnection.stop();
			
			this.topicSubscriber = this.topicSession.createDurableSubscriber(this.newsTopic, this.name, selector, false);
			this.topicListener = new NewsListener();
			this.topicSubscriber.setMessageListener(this.topicListener);
			
			this.topicConnection.start();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stopSubscriber() {
		
		try {
			this.topicSubscriber.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finish() {
		
		if(this.topicConnection != null) {
			
			try {
				this.topicSession.unsubscribe(this.name);
				this.topicConnection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
