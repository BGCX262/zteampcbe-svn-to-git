package news;

public class NewsContent {
	
	private String newsText;
	private NewsAttributes newsAttributes;
	
	public NewsContent(NewsAttributes newsAttributes, String newsText) {

		this.newsAttributes = newsAttributes;
		this.newsText = newsText;
	}

	public String getNewsText() {
		return newsText;
	}

	public void setNewsText(String newsText) {
		this.newsText = newsText;
	}

	public NewsAttributes getNewsAttributes() {
		return newsAttributes;
	}

	public void setNewsAttributes(NewsAttributes newsAttributes) {
		this.newsAttributes = newsAttributes;
	}
	
	

}
