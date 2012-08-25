package org.oneVillage.group.data;

public class Event{
	

		//@XmlAttribute(name = "groupId")
		String groupId;
		
		//@XmlAttribute(name = "eventId")
		String eventId;

		//@XmlAttribute(name = "content")
		String content;
		
		//@XmlAttribute(name = "userId")
		String userId;
		
		//@XmlAttribute(name ="timeStamp")
		String timestamp;
		
		//@XmlAttribute(name ="topic")
		String topic;
		
		//@XmlAttribute(name ="startTime")
		String startTime;
		
		//@XmlAttribute(name ="endTime")
		String endTime;
		
		//@XmlAttribute(name ="location")
		String location;

		@Override
		public String toString() {
			return "event [groupId=" + groupId + ", eventId=" + eventId
					+ ", content=" + content + ", userId=" + userId
					+ ", topic=" + topic + ", startTime=" + startTime
					+ ", endTime=" + endTime + ", location=" + location
					+ ", timeStamp=" + timestamp + "]";
		}
				
		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
		}

		public String getEventId() {
			return eventId;
		}

		public void setEventId(String eventId) {
			this.eventId = eventId;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
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

		public String getTopic() {
			return topic;
		}

		public void setTopic(String topic) {
			this.topic = topic;
		}

		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}
	
	}