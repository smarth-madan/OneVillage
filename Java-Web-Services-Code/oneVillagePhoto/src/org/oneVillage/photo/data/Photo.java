package org.oneVillage.photo.data;

public class Photo {
	//@XmlAttribute(name = "photoId")
	String photoid;
	
	String id;
	
	String name;
	
	String lat;
	
	String lon;

	//@XmlAttribute(name = "albumId")
	String albumid;

	//@XmlAttribute(name ="userId")
	String userid;
	
	String username;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	//@XmlAttribute(name = "latitude")
	String latitude;

	//@XmlAttribute(name ="longitude")
	String longitude;

	//@XmlAttribute(name ="description")
	String description;

	//@XmlAttribute(name = "createdDate")
	String createdDate;
	
	String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Photo [photoid=" + photoid +",albumid=" + albumid +",userid=" + userid +", desc="	+ description + ", createdDate=" + createdDate +"," +
				" latitude=" + latitude + ", longitude=" + longitude+ "]";
	}

	public String getPhotoid() {
		return photoid;
	}

	public void setPhotoid(String photoid) {
		this.photoid = photoid;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


}
