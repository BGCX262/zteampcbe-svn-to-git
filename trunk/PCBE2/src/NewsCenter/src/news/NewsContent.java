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
	
	public boolean equals(Object object)
        {
         if(object instanceof NewsContent)
             if(((NewsContent)object).getNewsAttributes().getTitle().equals(this.newsAttributes.getTitle()))
                 return true;
        return false;
        }

        public String toString()
        {
            String temp ="";
            temp = temp + newsText+"; "+newsAttributes.toString();
            return temp;
        }
}
