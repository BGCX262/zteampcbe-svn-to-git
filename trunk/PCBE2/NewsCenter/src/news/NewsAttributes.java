package news;

import java.util.Date;

public class NewsAttributes {
	
	private String domain = null;
	private String type = null;
	private String author = null;
	private String source= null;
	private Date firstPublished = null;
	private Date lastModified = null;
	private String title = null;
	
	public NewsAttributes(String author, String domain, Date firstPublished,
			Date lastModified, String source, String title, String type) {
		
		this.author = author;
		this.domain = domain;
		this.firstPublished = firstPublished;
		this.lastModified = lastModified;
		this.source = source;
		this.title = title;
		this.type = type;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getFirstPublished() {
		return firstPublished;
	}

	public void setFirstPublished(Date firstPublished) {
		this.firstPublished = firstPublished;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSQLSyntaxAttributes() {
		
		String attr = "";
		if(this.type != null) {
			
			attr = attr.concat("NewsType = '" + this.type + "' AND ");
		}
		if(this.domain != null) {
			
			attr = attr.concat("NewsDomain = '" + this.domain + "' AND ");
		}
		if(this.author != null) {
			
			attr = attr.concat("NewsAuthor = '" + this.author + "' AND ");
		}
		if(this.title != null) {
			
			attr = attr.concat("NewsTitle = '" + this.title + "' AND ");
		}
		if(this.source != null) {
			
			attr = attr.concat("NewsSource = '" + this.source + "' AND ");
		}
		if(this.firstPublished != null) {
			
			attr = attr.concat("NewsDate = '" + this.firstPublished.toString() + "' AND ");
		}
		if(this.lastModified != null) {
			
			attr = attr.concat("NewsLastDate = '" + this.lastModified.toString() + "' AND ");
		}
		
		attr = attr.substring(0, attr.lastIndexOf("AND"));
		
		return attr;
	}
}
