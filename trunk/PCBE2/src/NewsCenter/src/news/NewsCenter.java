package news;

public class NewsCenter {
	
	public static void main(String args[]) {
		

        Publisher pub1 = new Publisher("Publisher01", null);
        Publisher pub2 = new Publisher("Publisher02", null);
        Subscriber sub1 = new Subscriber("Subscriber01", null);
        Subscriber sub2 = new Subscriber("Subscriber02", null);
		
		sub1.start();
		sub2.start();
		pub1.start();
		pub2.start();
		
	}

}
