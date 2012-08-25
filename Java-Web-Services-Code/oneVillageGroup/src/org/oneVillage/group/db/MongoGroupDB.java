package org.oneVillage.group.db;



import java.util.ArrayList;

import java.util.Properties;


import org.oneVillage.group.data.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;



public class MongoGroupDB {
	public static final String sCollection = "mongo.collection";
	public static final String sDB = "mongo.db";
	public static final String sHost = "mongo.host";

	private DBCollection collection;
	private Properties props;

	public MongoGroupDB() {
	}

	public MongoGroupDB(Properties config) {
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

	

	/**
	 * insert data into the collection - note this does not enforce unique
	 * records
	 * 
	 * @param p
	 */
	public String addGroup(Group g) {
		try {
			DBCollection col = connect();
			DBObject dob = convertToMongo(g, col);
			System.out.println("insertin values");
			
			DBCursor c=col.find();
			System.out.println("size: "+c.size());
			col.insert(dob);
			
			System.out.println("size after insertion: "+c.size());
			while(c.hasNext()){
				System.out.println("values: "+c.next()+"collection name: "+col);
			}
			System.out.println("insertin success ");
			return (String) dob.get("groupId");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("\n\n\n\n\n\nexception ocuured \n\n\n\n\n\n\n");
			e.printStackTrace();
			return "false";
		}
	}

	
	public Group getGroup(String id) {
		Group b=new Group();
		try {
			System.out.println("In getGroup Method: cntl 2");
			DBCollection col = connect();
			BasicDBObject dob = new BasicDBObject();
			dob.append("groupId", id);
			System.out.println("Displaying the JSON object only with GroupId");
			System.out.println(dob.toString());
			
			DBCursor c=col.find(dob);
			
			if (c.hasNext()){
			DBObject object=c.next();
			 
			b.setGroupId((String) object.get("groupId"));
			b.setName((String) object.get("name"));
			b.setDescription((String) object.get("description"));
			b.setTimestamp((String) object.get("timestamp"));
			b.setUserId((String) object.get("userId"));
			
			System.out.println("the object is ready to be returned back to MongoEventDB: cntl 3");
			return b;
			}
			else{
				System.out.println("Object not found!!");
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
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

	private DBObject convertToMongo(Group g,DBCollection col) {
		BasicDBObject rtn = new BasicDBObject();
		
		int groupId = ReadFile.getID(8);
		rtn.append("groupId",(""+groupId));
		rtn.append("name",g.getName());
		rtn.append("description",g.getDescription());
		rtn.append("timestamp",g.getTimestamp());
		rtn.append("userId",g.getUserId());
		System.out.println("mongo object \n\n\n\n"+rtn.toString());
		return rtn;
	
	}

	public ArrayList<Group> getAllGroups() {
		
		ArrayList<Group> aGroup=new ArrayList<Group>();
		DBCollection col = connect();
		DBCursor cursor=col.find();
		
		while(cursor.hasNext()){
			DBObject dObject=cursor.next();
			Group b= new Group();
			b.setGroupId((String) dObject.get("groupId"));
			b.setDescription((String) dObject.get("description"));
			b.setName((String) dObject.get("name"));
			b.setTimestamp((String) dObject.get("timestamp"));
			b.setUserId((String) dObject.get("userId"));
			aGroup.add(b);
		}
		return aGroup;
	}

	public String deleteGroup(String groupId, String userId) {
		
		DBCollection col = connect();
		BasicDBObject rtn = new BasicDBObject();
		rtn.append("groupId", groupId);
		rtn.append("userId", userId);
		
		BasicDBObject deletedGroup = (BasicDBObject) col.findAndRemove(rtn);
		if(deletedGroup!=null){
			return ("groupId="+deletedGroup.get("groupId")+
					" and userId ="+deletedGroup.get("userId"));
		}
		else
		{
			return ("NotFound - groupId-userId combination to be deleted was not found!!!");		
		}	
	}
}
