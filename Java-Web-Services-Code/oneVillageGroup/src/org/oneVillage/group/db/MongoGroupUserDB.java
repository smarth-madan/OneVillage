package org.oneVillage.group.db;

import java.util.ArrayList;
import java.util.Properties;

import org.oneVillage.group.data.Group;
import org.oneVillage.group.data.GroupUser;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoGroupUserDB {
	public static final String sCollection = "mongo.collection";
	public static final String sDB = "mongo.db";
	public static final String sHost = "mongo.host";

	private DBCollection collection;
	private Properties props;
	
	public MongoGroupUserDB(Properties config) {
		init(config);
	}
	
	public void init(Properties props) {
		// log.debug("---> ExampleClient.init()");

		this.props = props;
	}
	
	public void release() {
		collection = null;
	}
	
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
			// should never get here unless no directory is available
			throw new RuntimeException("Unable to connect to mongodb on " + props.getProperty(sHost));
		}
	}
	
	public int joinGroup(String groupId,String userId) {
		String ret;
		try {
			DBCollection col = connect();
			BasicDBObject dob = new BasicDBObject();
			System.out.println("insertin values");
			dob.append("groupId", groupId);
			dob.append("userId", userId);
			DBObject user = dob;
			if(col.getCount(user)>0)
				return 404;
			else
			{
				int groupUserId = ReadFile.getID(0);
				dob.append("groupUserId", (""+groupUserId));
				user = dob;
				col.insert(user);
				System.out.println("insertin success ");
				return groupUserId;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("\n\n\n\n\n\nexception ocuured \n\n\n\n\n\n\n");
			e.printStackTrace();
			return 500;
		}
	}
	
	public int unJoinGroup(String groupId,String userId) {
		String ret;
		try {
			DBCollection col = connect();
			BasicDBObject dob = new BasicDBObject();
			System.out.println("insertin values");
			
			dob.append("groupId", groupId);
			dob.append("userId", userId);
			DBObject user = dob;
			
			if(col.getCount(user)>0)
			{
				col.remove(user);
				return 200;
			}
			else
			{
				return 404;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("\n\n\n\n\n\nexception ocuured \n\n\n\n\n\n\n");
			e.printStackTrace();
			return 500;
		}
	}
	
	public ArrayList<GroupUser> getMembers(String groupId){
		ArrayList<GroupUser> members = new ArrayList<GroupUser>();
		try {
				DBCollection col = connect();
				BasicDBObject dob = new BasicDBObject();
				System.out.println("insertin values");
			
				dob.append("groupId", groupId);
				DBObject user = dob;
				
				DBCursor c = col.find(user);
				
				System.out.println("size"+c.size());
				while(c.hasNext()){
					DBObject d1=c.next();
					GroupUser tempUser = new GroupUser();
					tempUser.setGroupUserId((String)d1.get("groupUserId"));
					tempUser.setGroupId(groupId);
					tempUser.setUserId((String) d1.get("userId"));
					members.add(tempUser);
				}
				return members;
		}
		catch (Exception e){
			System.out.println("\n\n\n\n\n\nexception ocuured \n\n\n\n\n\n\n");
			e.printStackTrace();
			return null;
		}
	}
	
	public void delAllUsers(String groupId) {
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
	
	public int isMember(String groupId, String userId) {
		try {
			DBCollection col = connect();
			BasicDBObject rtn = new BasicDBObject();
			rtn.append("groupId", groupId);
			rtn.append("userId",userId);
			//DBCursor c=col.find();
			DBCursor c = col.find(rtn);
			if(c.count() >= 1)
				return 1;
			else
				return 0;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	} 
	
}
