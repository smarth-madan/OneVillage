package org.oneVillage.group.db;

import java.util.ArrayList;
import java.util.Properties;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.oneVillage.group.data.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class MongoEventDB {
	public static final String sCollection = "mongo.collection";
	public static final String sDB = "mongo.db";
	public static final String sHost = "mongo.host";
	private DBCollection collection;
	private Properties props;
	public MongoEventDB() {
	}

	public MongoEventDB(Properties config) {
		init(config);
	}

	/**
	 * connection setup
	 * 
	 * @param props
	 */
	public void init(Properties props) {
		this.props = props;
	}

	/**
	 * 
	 */
	public void release() {
		collection = null;
	}

	

	public String addEvent (Event p) throws WebApplicationException {
		try {
			DBCollection col = connect();
			DBObject dob = convertToMongoEntry(p,col);
			System.out.println("insertin values");
			org.oneVillage.group.db.MongoGroupDB mbd = new MongoGroupDB();
			Properties conf = new Properties();
			conf.setProperty(MongoGroupDB.sHost, "localhost");
	    	conf.setProperty(MongoGroupDB.sDB, "oneVillageDb");
	    	conf.setProperty(MongoGroupDB.sCollection, "groups");
	    	
			MongoGroupDB mc = new MongoGroupDB(conf);
			
			Group b=mc.getGroup(p.getGroupId());
			if(b.getGroupId()!= null){
				System.out.println("cntl here collection name: "+col.getName());
				col.insert(dob);
			}
			else{
				System.out.println("Exception occurred: Group does not exist");
				return "false";
			}
			
			System.out.println("inserting success ");
		} catch (Exception e) {
			System.out.println("\n\n\n\n\n\nexception ocuured \n\n\n\n\n\n\n");
			e.printStackTrace();
			return "false";
		}
		return "true";
	}
	
	

	
	//#######################CONNECT######################//
	/**
	 * just like a jdbc connect - get me a connection to the data
	 * 
	 * @return
	 */
	private DBCollection connect() {
		try {
			if (collection != null && collection.getName() != null)
				return collection;
		} catch (Exception ex) {
			collection = null;
		}

		try {
			Mongo m = new Mongo(props.getProperty(sHost));
			DB db = m.getDB(props.getProperty(sDB));
			collection = db.getCollection(props.getProperty(sCollection));
			if (collection == null)
				throw new RuntimeException("Missing collection: " + props.getProperty(sCollection));

			return collection;
		} catch (Exception ex) {
			throw new RuntimeException("Unable to connect to mongodb on " + props.getProperty(sHost));
		}
	}

	//##############CONVERT TO MongoEntry##################//
	private DBObject convertToMongoEntry(Event p,DBCollection col) {
		BasicDBObject rtn = new BasicDBObject();
		
		DBCursor c=col.find();
		int event_id = ReadFile.getID(12);
		System.out.println("Appending event id in basic object in convertToMongo");
		rtn.append("eventId", (""+event_id));
		
		System.out.println("Appending groupId in basic object in convertToMongo");
	
		rtn.append("groupId",p.getGroupId());
		rtn.append("content",p.getContent());
		rtn.append("timeStamp",p.getTimestamp());
		rtn.append("userId",p.getUserId());
		rtn.append("topic",p.getTopic());
		rtn.append("startTime",p.getStartTime());
		rtn.append("endTime",p.getEndTime());
		rtn.append("location",p.getLocation());
				
		System.out.println("mongo object \n\n\n\n"+rtn.toString());
		return rtn;
	}
	
	//############# DELETE AN EVENT ############//
	/**
	 * delete an object - 
	 * 
	 * @param
	 */
	public String delEvent( String groupId,  String eventId, String userId) {
		try {
			DBCollection col = connect();
			BasicDBObject rtn = new BasicDBObject();
			rtn.append("groupId", groupId);
			rtn.append("eventId", eventId);
			rtn.append("userId", userId);
			//DBCursor c=col.find();
			BasicDBObject delEvent = (BasicDBObject) col.findAndRemove(rtn);
			if(delEvent != null)
				return ("groupId="+delEvent.get("groupId")+" and userId ="+delEvent.get("userId")
						+" eventId ="+ delEvent.get("eventId"));
			else
			{
				return ("NotFound: Event not found!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ("Exception occured!!!");
		}
	} 
	
	public void delAllEvents(String groupId) {
		try {
			DBCollection col = connect();
			BasicDBObject rtn = new BasicDBObject();
			rtn.append("groupId", groupId);
			//DBCursor c=col.find();
			col.remove(rtn);

		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
//************GET ALL EVENTS**************//
public ArrayList<Event> getEvents(String groupId) {
	
	ArrayList<Event> aEvents = new ArrayList<Event>();
	DBCollection col = connect();
	BasicDBObject bObject=new BasicDBObject();
	bObject.append("groupId", groupId);
	DBObject d=bObject;
	DBCursor c=col.find(d);
	System.out.println("size"+c.size());
	while(c.hasNext()){
		DBObject d1=c.next();
		Event b= new Event();
		b.setGroupId(groupId);
		b.setEventId((String) d1.get("eventId"));
		b.setUserId((String) d1.get("userId"));
		b.setTopic((String) d1.get("topic"));
		b.setStartTime((String) d1.get("startTime"));
		b.setEndTime((String) d1.get("endTime"));
		b.setLocation((String) d1.get("location"));
		b.setContent((String) d1.get("content"));
		b.setTimestamp((String) d1.get("timeStamp"));
		
		aEvents.add(b);	
	}
	
	
	return aEvents;		
	}
//**********************************//

//************GET A PARTICULAR EVENT**************//
public ArrayList<Event> getEvent(String groupId, String eventId) {

	ArrayList<Event> aEvent = new ArrayList<Event>();
	DBCollection col = connect();
	BasicDBObject bObject=new BasicDBObject();
	bObject.append("groupId", groupId);
	bObject.append("eventId", eventId);
	DBObject d=bObject;
	DBCursor c=col.find(d);
	System.out.println("size"+c.size());
	if(c.size()==0)
		return aEvent;
	while(c.hasNext()){
		DBObject d1=c.next();
		Event b= new Event();
		b.setGroupId(groupId);
		b.setEventId((String) d1.get("eventId"));
		b.setUserId((String) d1.get("userId"));
		b.setTopic((String) d1.get("topic"));
		b.setContent((String) d1.get("content"));
		b.setStartTime((String) d1.get("startTime"));
		b.setEndTime((String) d1.get("endTime"));
		b.setLocation((String) d1.get("location"));
		b.setTimestamp((String) d1.get("timeStamp"));

		aEvent.add(b);	
	}
	return aEvent;
}
//*****************************************//
	
//************GET ALL EVENTS BY A PARTICULAR USER**************//
		public ArrayList<Event> getEventsByUserId(String groupId, String userId) {
		
			ArrayList<Event> aEvent = new ArrayList<Event>();
			DBCollection col = connect();
			BasicDBObject bObject=new BasicDBObject();
			bObject.append("groupId", groupId);
			bObject.append("userId", userId);
			DBObject d=bObject;
			DBCursor c=col.find(d);
			System.out.println("I am in mongoEventDB.java.. lets see the size");
			System.out.println("size"+c.size());
			if(c.size()==0)
				return aEvent;
			while(c.hasNext()){
				DBObject d1=c.next();
				Event b= new Event();
				b.setGroupId(groupId);
				b.setEventId((String) d1.get("eventId"));
				b.setUserId((String) d1.get("userId"));
				b.setTopic((String) d1.get("topic"));
				b.setContent((String) d1.get("content"));
				b.setStartTime((String) d1.get("startTime"));
				b.setEndTime((String) d1.get("endTime"));
				b.setLocation((String) d1.get("location"));
				b.setTimestamp((String) d1.get("timeStamp"));
				
				aEvent.add(b);	
			}
			return aEvent;
		}
//*****************************************//
		
//*************UPDATE EVENT****************//	
	public String updateEvent(String groupId, String eventId, String userId, Event event){
		DBCollection col = connect();
		BasicDBObject bObject=new BasicDBObject();
		bObject.append("groupId", groupId);
		bObject.append("eventId", eventId);
		bObject.append("userId",userId);
		
		BasicDBObject rtn = new BasicDBObject();
		rtn.append("eventId",event.getEventId());
			rtn.append("groupId",event.getGroupId());
			rtn.append("content",event.getContent());
			rtn.append("startTime",event.getStartTime());
			rtn.append("endTime",event.getEndTime());
			rtn.append("topic",event.getTopic());
			rtn.append("location",event.getLocation());
			rtn.append("timeStamp",event.getTimestamp());
			rtn.append("userId",event.getUserId());
			System.out.println("mongo object \n\n\n\n"+rtn.toString());

		DBObject newEvent =  rtn;
		
		try{
			BasicDBObject c=(BasicDBObject) col.findAndModify(bObject, rtn);
			if(c.isEmpty())
				return "NotFound";
			else
				return "true";
		}catch (Exception e) {
			e.printStackTrace();
			return ("Exception occured!!!");
		}
	}
//*****************************************//
}


