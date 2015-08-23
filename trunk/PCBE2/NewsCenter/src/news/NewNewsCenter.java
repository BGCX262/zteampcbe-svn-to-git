package news;

import java.util.Date;

public class NewNewsCenter {
	
	public static void main(String args[]) {
		
		NewsAttributes attr1[] = {new NewsAttributes(null, null, null, null, null, null, NewsSelector.NEWS_TYPES[3])};
		Publisher pub1 = new Publisher("Publisher001", attr1);
		
		NewsAttributes attr2[] = {new NewsAttributes("Publisher001", null, null, null, null, null, NewsSelector.NEWS_TYPES[3])};
		Publisher pub2 = new Publisher("Publisher002", attr2);
		
		NewsAttributes attr3[] = {new NewsAttributes(null, NewsSelector.DOMAINS[0], null, null, null, null, NewsSelector.NEWS_TYPES[0])};
		Subscriber sub1 = new Subscriber("Subscriber001", attr3);
		
		NewsAttributes attr4[] = {new NewsAttributes(null, NewsSelector.DOMAINS[5], null, null, null, null, NewsSelector.NEWS_TYPES[0]),
				new NewsAttributes(null, NewsSelector.DOMAINS[0], null, null, null, null, NewsSelector.NEWS_TYPES[2])};
		Subscriber sub2 = new Subscriber("Subscriber002", attr4);
		
		sub1.startSubscriber();
		sub2.startSubscriber();
		pub1.startSubscriber();
		pub2.startSubscriber();
		
		pub1.publishNews(new NewsContent(new NewsAttributes("Publisher001", NewsSelector.DOMAINS[0],
										new Date(), new Date(), "originala", "SENZATIONAL", NewsSelector.NEWS_TYPES[0]),
						"jfdsdf fd  hg gf"));
		sub1.stopSubscriber();
		pub2.publishNews();
		sub1.startSubscriber();
		
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pub1.stopSubscriber();
		pub2.stopSubscriber();
		sub1.stopSubscriber();
		sub2.stopSubscriber();
		sub1.finish();
		sub2.finish();
		pub1.finish();
		pub2.finish();
		
	}

}
