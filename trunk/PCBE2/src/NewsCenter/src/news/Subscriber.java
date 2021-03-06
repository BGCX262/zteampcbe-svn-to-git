package news;

import java.util.ArrayList;
import java.util.Date;

import javax.jms.*;

public class Subscriber extends Thread{
	
	protected String name = null;
	
	protected TopicConnection topicConnection = null;
	protected TopicConnectionFactory topicConnectionFactory = null;
	protected TopicSession topicSession = null;
	protected Topic newsTopic = null;
	protected TopicSubscriber topicSubscriber = null;
	private NewsListener topicListener = null;
	
	protected TopicPublisher topicPublisher = null;
	
	protected ArrayList<NewsAttributes> newsAttr = new ArrayList<NewsAttributes>();

    private SubFrame subFrame;
	
	private class NewsListener implements MessageListener{

		@Override
		public void onMessage(Message msg) {

            String txt = "";
			try {

                txt = txt.concat("\n----------------------------------------------------------\n" +
						name + " - Event Received - News " + msg.getStringProperty("NewsType") + "\n" +
						msg.getStringProperty("NewsTitle") + " by " + msg.getStringProperty("NewsAuthor") + "\n" +
						msg.getStringProperty("NewsDomain") + "\n" +
						"published on: " + msg.getStringProperty("NewsDate") + " / last modified: " + msg.getStringProperty("NewsLastDate"));

				if(msg instanceof TextMessage) {
				
                    txt = txt.concat("\n" + ((TextMessage)msg).getText());
				}
                subFrame.appendText(txt);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
			try {
				if(!msg.getStringProperty("NewsType").equals(NewsSelector.NEWS_TYPES[2]) && !msg.getStringProperty("NewsType").equals(NewsSelector.NEWS_TYPES[3])) {
					
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
	
	
	public Subscriber(String name, ArrayList<NewsAttributes> newsAttr) {
	
		if(newsAttr != null) {
			this.newsAttr.addAll(newsAttr);
		}
		this.name = name;
				
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
				
		for(NewsAttributes attr : this.newsAttr) {
			
			selector = selector.concat("(");
			selector = selector.concat(attr.getSQLSyntaxAttributes());
			selector = selector.concat(") OR ");
		}
		
		if(selector.equals("")) {
			selector = "TRUE = FALSE";
		} else {
			selector = selector.substring(0, selector.lastIndexOf("OR"));
		}
		
		try {
            this.stopSubscriber();
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
            if(this.topicSubscriber != null) {
                this.topicSubscriber.close();
            }
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void finish() {
		
		if(this.topicConnection != null) {
			
			try {
                if(this.topicSubscriber != null) {
                    this.topicSubscriber.close();
                    this.topicSession.unsubscribe(this.name);
                }
				this.topicConnection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

    public String getUsername() {

        return this.name;
    }

    public void addNewSubscription(String author, String domain, Date firstPublished,
			Date lastModified, String source, String title, String type) {

        if(this.topicSubscriber != null) {
            this.stopSubscriber();
        }
        this.newsAttr.add(new NewsAttributes(author, domain, firstPublished, lastModified, source, title, type));
        this.startSubscriber();

    }
	
    @Override
	public void run() {

        this.subFrame = new SubFrame(this);
        this.subFrame.setVisible(true);
	}
}
