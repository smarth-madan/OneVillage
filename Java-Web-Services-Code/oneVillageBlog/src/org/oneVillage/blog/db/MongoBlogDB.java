package org.oneVillage.blog.db;



import java.util.ArrayList;

import java.util.Properties;


import org.oneVillage.blog.data.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;



public class MongoBlogDB {
	// TODO need slf4j log setup

	// mongodb setup - each collection should be thought of as a database
	public static final String sCollection = "mongo.collection";
	public static final String sDB = "mongo.db";
	public static final String sHost = "mongo.host";

	private DBCollection collection;
	private Properties props;

	public MongoBlogDB() {
	}

	public MongoBlogDB(Properties config) {
		init(config);
	}

	/**
	 * connection setup
	 * 
	 * @param props
	 */
	public void init(Properties props) {
		// log.debug("---> ExampleClient.init()");

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
	public String addBlog(Blog p) {
		try {
			DBCollection col = connect();
			DBObject dob = convertToMongo(p, col);
			System.out.println("Inserting values");
			
			DBCursor c=col.find();
			System.out.println("size: "+c.size());
			col.insert(dob);
			
			
			while(c.hasNext()){
				System.out.println("values: "+c.next()+"collection name: "+col);
			}
			//System.out.println("result"+w.toString());
			
			System.out.println("inserting success ");
			return (String) dob.get("blogid");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("\n\n\n\n\n\nexception ocuured \n\n\n\n\n\n\n");
			e.printStackTrace();
			return "false";
		}
	}

	/**
	 * delete an object - the email is assumed to be the natural unique key
	 * 
	 * @param email
	 */
	public Blog getBlog(String id) {
		Blog b=new Blog();
		//String s=null;
		try {
			System.out.println("cntl 2");
			DBCollection col = connect();
			BasicDBObject dob = new BasicDBObject();
			dob.append("blogid", id);
			System.out.println(dob.toString());
			DBCursor c=col.find(dob);
			if(c.hasNext()){
				DBObject object=c.next();
				// return b.toString();
				//JSONObject object =(JSONObject) JSON.parse(value);
				b.setBlogid((String) object.get("blogid"));
				b.setDescription((String) object.get("description"));
				b.setSubject((String) object.get("subject"));
				b.setTimestamp((String) object.get("timestamp"));
				b.setUserid((String) object.get("userid"));
				System.out.println("cntl 3");
			}else{
				return null;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return b;
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
			// should never get here unless no directory is available
			throw new RuntimeException("Unable to connect to mongodb on " + props.getProperty(sHost));
		}
	}

	private DBObject convertToMongo(Blog p,DBCollection col) {
		BasicDBObject rtn = new BasicDBObject();
		//if (p.getBlogId() != 0)
		//rtn.append("id",1);
		
		
		int blogid = ReadFile.getID(0);
		rtn.append("blogid",(""+blogid));
		
	//if (p.getDesc() != null)
		rtn.append("description",p.getDescription());
	//if (p.getSubject() != null)
		rtn.append("subject",p.getSubject());
	//if (p.getTimeStamp() != null)
		rtn.append("timestamp",p.getTimestamp());
	//if (p.getUserID() != null)
		rtn.append("userid",p.getUserid());
		System.out.println("mongo object \n\n\n\n"+rtn.toString());
		return rtn;
	
	}

	public ArrayList<Blog> getAllBlog() {
		
		ArrayList<Blog> aBlogs=new ArrayList<Blog>();
		DBCollection col = connect();
		DBCursor cursor=col.find();
		while(cursor.hasNext()){
			DBObject dObject=cursor.next();
			//set blog
			Blog b= new Blog();
			b.setBlogid((String) dObject.get("blogid"));
			b.setDescription((String) dObject.get("description"));
			b.setSubject((String) dObject.get("subject"));
			b.setTimestamp((String) dObject.get("timestamp"));
			b.setUserid((String) dObject.get("userid"));
			aBlogs.add(b);
		}
		return aBlogs;
	}

	public String deleteBlog(String blogId, String userId) {
		
		System.out.println("Reached MongoBlogDb...");
		DBCollection col = connect();
		BasicDBObject dbObject = new BasicDBObject();
		dbObject.append("blogid", blogId);
		dbObject.append("userid", userId);
		DBObject dbObject2=dbObject;
		DBCursor cur = col.find();
		if(cur.hasNext()){
			
			DBCollection colEntry =null;
			try {
				Mongo m = new Mongo(props.getProperty(sHost));
				DB db = m.getDB(props.getProperty(sDB));
				collection = db.getCollection("article");
				if (collection == null)
					throw new RuntimeException("Missing collection: " + props.getProperty(sCollection));

				colEntry= collection;
			} catch (Exception ex) {
				// should never get here unless no directory is available
				throw new RuntimeException("Unable to connect to mongodb on " + props.getProperty(sHost));
			}
			
			//remove blogentry
			BasicDBObject db1 = new BasicDBObject();
			db1.append("blogid", blogId);
			db1.append("userId",userId);
			colEntry.remove(db1);
			System.out.println("deleting articles...");
		}
		BasicDBObject rtn = (BasicDBObject) col.findAndRemove(dbObject2);
		System.out.println("Deleting Blog...");
		if(rtn != null)
			return ("blogid="+rtn.get("blogid")+"and userId ="+rtn.get("userid"));
		else
		{
			return ("Blog not found!!!");
		}
	
	}
}
