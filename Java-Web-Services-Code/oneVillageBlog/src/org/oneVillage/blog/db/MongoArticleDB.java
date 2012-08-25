package org.oneVillage.blog.db;



import java.util.ArrayList;

import java.util.Properties;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;



import org.oneVillage.blog.data.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;


public class MongoArticleDB {

	// mongodb setup - each collection should be thought of as a database
	public static final String sCollection = "mongo.collection";
	public static final String sDB = "mongo.db";
	public static final String sHost = "mongo.host";

	private DBCollection collection;
	private Properties props;

	public MongoArticleDB() {
	}

	public MongoArticleDB(Properties config) {
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
	public String addArticle (Article p) throws WebApplicationException {
		try {

			System.out.println("inserting values");
			org.oneVillage.blog.db.MongoBlogDB mbd = new MongoBlogDB();
			System.out.println("id "+p.getBlogid());
			
			BasicDBObject blogSearch = new BasicDBObject();
			blogSearch.append("blogid",p.getBlogid());
			blogSearch.append("userid",p.getUserid());
			System.out.println(p.getBlogid() +"    "+ p.getUserid());
	    	DBObject blog = blogSearch;
			
	    	Mongo mBlog = new Mongo("localhost");
			DB dbBlog = mBlog.getDB("oneVillageDb");
			DBCollection colBlog = dbBlog.getCollection("blog");
	    	
	    	DBCursor c = colBlog.find(blog);
	    	if(c.count()>0){
	    		DBCollection col = connect();
	    		DBObject dob = convertToMongoEntry(p,col);
				WriteResult wr = col.insert(dob);
				wr.getError();
				System.out.println("inserting success ");
				return (String) dob.get("articleId");
	    	}
			else{
				return "false";
			}
			//System.out.println("result"+w.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("\n\n\n\n\n\nexception ocuured \n\n\n\n\n\n\n");
			e.printStackTrace();
			return "false";
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
			// should never get here unless no directory is available
			throw new RuntimeException("Unable to connect to mongodb on " + props.getProperty(sHost));
		}
	}

	private DBObject convertToMongoEntry(Article p,DBCollection col) {
		BasicDBObject rtn = new BasicDBObject();
		
		DBCursor c=col.find();
//		System.out.println("size: "+c.size());
//		int size= c.size()+1;
		int article_id = ReadFile.getID(4);
		rtn.append("articleId", (""+article_id));
		
		
	//if (p.getBlogId() != 0)
		rtn.append("blogid",p.getBlogid());
	//if (p.getSubject() != null)
		rtn.append("content",p.getContent());
	//if (p.getTimeStamp() != null)
		rtn.append("timeStamp",p.getTimestamp());
	//if (p.getUserID() != null)
		rtn.append("userId",p.getUserid());
		System.out.println("mongo object \n\n\n\n"+rtn.toString());
		return rtn;
	}
	
	/**
	 * delete an object - 
	 * 
	 * @param
	 */
	public String delArticle( String blogId,  String articleId, String userId) {
		try {
			DBCollection col = connect();
			BasicDBObject rtn = new BasicDBObject();
			rtn.append("blogid", blogId);
			rtn.append("articleId", articleId);
			rtn.append("userId", userId);
			//DBCursor c=col.find();
			BasicDBObject delArticle = (BasicDBObject) col.findAndRemove(rtn);
			if(delArticle != null)
				return ("blogid="+delArticle.get("blogid")+" and userId ="+delArticle.get("userId")
						+" articleId ="+ delArticle.get("articleId"));
			else
			{
				return ("Article not found!!!");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("Exception occured!!!");
		}
	} 
	
public ArrayList<Article> getArticles(String blogId) {
	
	ArrayList<Article> aArticles = new ArrayList<Article>();
	DBCollection col = connect();
	BasicDBObject bObject=new BasicDBObject();
	bObject.append("blogid", blogId);
	DBObject d=bObject;
	DBCursor c=col.find(d);
	System.out.println("size"+c.size());
	while(c.hasNext()){
		DBObject d1=c.next();
		Article b= new Article();
		b.setBlogid(blogId);
		b.setArticleid((String) d1.get("articleId"));
		b.setContent((String) d1.get("content"));
		b.setTimestamp((String) d1.get("timeStamp"));
		b.setUserid((String) d1.get("userId"));
		
		aArticles.add(b);	
	}
	
	
	return aArticles;		
}

	public ArrayList<Article> getArticle(String blogId, String articleId) {
	
		ArrayList<Article> aArticle = new ArrayList<Article>();
		DBCollection col = connect();
		BasicDBObject bObject=new BasicDBObject();
		bObject.append("blogid", blogId);
		bObject.append("articleId", articleId);
		DBObject d=bObject;
		DBCursor c=col.find(d);
		System.out.println("size"+c.size());
		if(c.size()==0)
			return aArticle;
		while(c.hasNext()){
			DBObject d1=c.next();
			Article b= new Article();
			b.setBlogid(blogId);
			b.setArticleid((String) d1.get("articleId"));
			b.setContent((String) d1.get("content"));
			b.setTimestamp((String) d1.get("timeStamp"));
			b.setUserid((String) d1.get("userId"));
			
			aArticle.add(b);	
		}
		return aArticle;
	}
	
	public String updateArticle(String blogId, String articleId, String userId, Article article){
		DBCollection col = connect();
		BasicDBObject bObject=new BasicDBObject();
		bObject.append("blogid", blogId);
		bObject.append("articleId", articleId);
		bObject.append("userId",userId);
		
		BasicDBObject rtn = new BasicDBObject();
		rtn.append("articleId",article.getArticleid());
		//if (p.getBlogId() != 0)
			rtn.append("blogid",article.getBlogid());
		//if (p.getSubject() != null)
			rtn.append("content",article.getContent());
		//if (p.getTimeStamp() != null)
			rtn.append("timeStamp",article.getTimestamp());
		//if (p.getUserID() != null)
			rtn.append("userId",article.getUserid());
			System.out.println("mongo object \n\n\n\n"+rtn.toString());
		DBObject newArticle =  rtn;
		
		try{
			BasicDBObject c=(BasicDBObject) col.findAndModify(bObject, rtn);
			if(c.isEmpty())
				return "Not Found!!!";
			else
				return "true";
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ("Exception occured!!!");
		}
	}

}


