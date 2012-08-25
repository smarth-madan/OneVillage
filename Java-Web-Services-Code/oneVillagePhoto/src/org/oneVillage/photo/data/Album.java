package org.oneVillage.photo.data;

public class Album {
	
	//@XmlAttribute(name = "albumId")
	String albumid;
	
	//@XmlAttribute(name = "name")
	String name;

	//@XmlAttribute(name ="description")
	String description;
	
	//@XmlAttribute(name ="userId")
	String userid;
	
	String userId;
	
	String username;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	String id;

	//@XmlAttribute(name = "createdDate")
	String createdDate;

	@Override
	public String toString() {
		return "Album [albumid=" + albumid +",name=" + name +", desc="	+ description + ", timeStamp=" + createdDate
		+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getAlbumid() {
		return albumid;
	}

	public void setAlbumid(String albumid) {
		this.albumid = albumid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
