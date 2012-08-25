package org.oneVillage.blog.data;

public class Article{
	
	//@XmlAttribute(name = "blogId")
	String blogid;
	
	//@XmlAttribute(name = "articleId")
	String articleid;
	
	public String getArticleid() {
		return articleid;
	}

	//@XmlAttribute(name = "content")
	String content;
	
	//@XmlAttribute(name = "userId")
	String userid;
	
	//@XmlAttribute(name ="timeStamp")
	String timestamp;

	@Override
	public String toString() {
		return "Article [blogId=" + blogid + ", articleId=" + articleid
				+ ", content=" + content + ", userId=" + userid
				+ ", timeStamp=" + timestamp + "]";
	}

	public String getBlogid() {
		return blogid;
	}

	public void setBlogid(String blogId2) {
		this.blogid = blogId2;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String articleid() {
		return articleid;
	}

	public void setArticleid(String articleid) {
		this.articleid = articleid;
	}
	

}