package news;

import java.util.Date;

import javax.jms.*;

public class Publisher extends Subscriber {

	private ReadNewsListener topicListener = null;
	
	private class ReadNewsListener implements MessageListener{

		
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
		}
		
		
	}
	
	public Publisher(String name, NewsAttributes[] newsAttributes) {
		super(name, newsAttributes);
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
			this.topicListener = new ReadNewsListener();
			this.topicSubscriber.setMessageListener(this.topicListener);
			
			this.topicConnection.start();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Message createMessage(String author, String domain, Date firstPublished,
			Date lastModified, String source, String title, String type, String textMessage) throws JMSException {
		
		Message msg;
		
		// TODO check null parameters
		
		if(type.equals(NewsSelector.NEWS_TYPES[3])) {
		
			msg = this.topicSession.createMessage();
		} else {
			
			msg = this.topicSession.createTextMessage();
			((TextMessage)msg).setText(textMessage);
		}
		
		msg.setStringProperty("NewsAuthor", author);
		msg.setStringProperty("NewsDomain", domain);
		msg.setStringProperty("NewsDate", firstPublished.toString());
		msg.setStringProperty("NewsLastDate", lastModified.toString());
		msg.setStringProperty("NewsSource", source);
		msg.setStringProperty("NewsTitle", title);
		msg.setStringProperty("NewsType", type);
		
		return msg;
	}
	
	public void publishNews() {
		
		try {
			Message msg1 = this.createMessage(this.name, NewsSelector.DOMAINS[0], new Date(), new Date(), 
					"originala", "Titlu Stire Fotbal", NewsSelector.NEWS_TYPES[0], "bla bla");
			this.topicPublisher.publish(msg1);
			
			Message msg2 = this.createMessage(this.name, NewsSelector.DOMAINS[5], new Date(), new Date(), 
					"originala", "Titlu Stire Basket", NewsSelector.NEWS_TYPES[0], "bla bla");
			this.topicPublisher.publish(msg2);
			
			msg1.setStringProperty("NewsType", NewsSelector.NEWS_TYPES[1]);
			((TextMessage)msg1).setText("bla lalal");
			this.topicPublisher.publish(msg1);
			
			msg1.setStringProperty("NewsType", NewsSelector.NEWS_TYPES[2]);
			this.topicPublisher.publish(msg1);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}	
	
	public void publishNews(NewsContent newsContent) {
		
		Message msg1;
		try {
			msg1 = this.createMessage(newsContent.getNewsAttributes().getAuthor(),
					newsContent.getNewsAttributes().getDomain(),
					newsContent.getNewsAttributes().getFirstPublished(),
					newsContent.getNewsAttributes().getLastModified(),
					newsContent.getNewsAttributes().getSource(),
					newsContent.getNewsAttributes().getTitle(),
					newsContent.getNewsAttributes().getType(),
					newsContent.getNewsText());
			this.topicPublisher.publish(msg1);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
