package org.oneVillage.group.data;

public class Group {
	
	//@XmlAttribute(name = "groupid")
	String groupId;
	//@XmlAttribute(name = "name")
	String name;
	//@XmlAttribute(name ="description")
	String description;
	//@XmlAttribute(name ="userId")
	String userId;
	//@XmlAttribute(name = "timeStamp")
	String timestamp;

	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ",name=" + name +  ", desc="
				+ description +  ", timeStamp=" + timestamp + ", userId=" + userId
				+ "]";
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	

	
}
