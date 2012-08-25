package org.oneVillage.blog.data;

public class Blog {
	
	//@XmlAttribute(name = "blogId")
	String blogid;
	
	//@XmlAttribute(name = "subject")
	String subject;
	
	//@XmlAttribute(name ="description")
	String description;
	
	//@XmlAttribute(name ="userId")
	String userid;
	
	//@XmlAttribute(name = "timeStamp")
	String timestamp;

	@Override
	public String toString() {
		return "Blog [blogId=" + blogid + ", subject=" + subject + ", desc="
				+ description + ", userId=" + userid + ", timeStamp=" + timestamp
				+ "]";
	}

	public String getBlogid() {
		return blogid;
	}

	public void setBlogid(String blogid) {
		this.blogid = blogid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	/*public Blog convertBlog(String s) {
		Blog blog=new Blog();
		String [] values = s.split(",");
		String[] varray;
		varray=values[0].split("=");
		if(varray.length > 1){
			blog.setBlogId(Integer.parseInt(varray[1]));
			
			
		}
		
		return null;
		
		
		
	}*/
	

}
