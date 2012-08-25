package org.oneVillage.blog.resource;



import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.ClientWebException;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.json4j.JSON;
import org.apache.wink.json4j.JSONArray;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.oneVillage.blog.db.MongoBlogDB;
import org.oneVillage.blog.db.MongoArticleDB;
import org.oneVillage.blog.data.Blog;
import org.oneVillage.blog.data.Article;

public class BlogServiceImpl implements BlogService {
	
	
	private Properties conf;
	private Properties conf1;
	String url;
	RestClient client;
	public BlogServiceImpl() {
		conf = new Properties();
		conf.setProperty(MongoBlogDB.sHost, "localhost");
    	conf.setProperty(MongoBlogDB.sDB, "oneVillageDb");
    	conf.setProperty(MongoBlogDB.sCollection, "blog");
    	
    	conf1 = new Properties();
		conf1.setProperty(MongoBlogDB.sHost, "localhost");
    	conf1.setProperty(MongoBlogDB.sDB, "oneVillageDb");
    	conf1.setProperty(MongoBlogDB.sCollection, "article");
    	
    	 url = "http://localhost:8087/OneVillage/blog";
		 client = new RestClient();
    	
	}
	

	@Override
	public Response createBlog(Blog blog) {

	/// do the check
		System.out.println("The request is received...");
		MongoBlogDB mc = new MongoBlogDB(conf);
		
		String ret = mc.addBlog(blog);
		
		//success
		if(ret == "false")
			return Response.status(400).entity("Error Occurred!!!").build();
		else
			return Response.status(200).entity("Blog Created with blogId:"+ret).build();
			
				
	}



	@Override
	public Blog getBlog(@PathParam("blogId") String id) {
		
			System.out.println("cntl in our get blog with id "+id);
		
			MongoBlogDB mc = new MongoBlogDB(conf);
		
			Blog b=mc.getBlog(id);
			if(b==null)
				return null;
			else
				return b;
	}


	@Override
	public String getAllBlogs() {
		
		MongoBlogDB mc = new MongoBlogDB(conf);
		
		ArrayList<Blog> list=mc.getAllBlog();
		System.out.println("calling getAllBlogs");
		Type typeOfSrc = new TypeToken<List<Blog>>(){}.getType();
		Gson gson=new Gson();
		String firstData=gson.toJson(list, typeOfSrc);
		System.out.println("Data:"+firstData+"\n");
		return firstData;
		
	}

	@Override
	public Response deleteBlog(@PathParam("blogid") String blogId, @PathParam("userid") String userId) {
		/// do the check
		System.out.println("The request is received...");
		
		MongoBlogDB mc = new MongoBlogDB(conf);
		
		String response=mc.deleteBlog(blogId,userId);
		System.out.println("Response ="+response);

		if(response.substring(0,4).equals("Blog"))
			return Response.status(400).entity(response).build();
		else
			return Response.status(200).entity(response).build();
		
	}

	// Create Article
	  @Override
		public Response createArticle(@PathParam("blogid") String blogId, Article article) throws WebApplicationException {
		/// do the check
			System.out.println("The request is received...");
			article.setBlogid(blogId);
			
			MongoArticleDB mc1 = new MongoArticleDB(conf1);
			String ret = mc1.addArticle(article);
			
			if(ret.equals("false"))
				return Response.status(400).entity("Error Occured!!!").build();
			else
				return Response.status(200).entity("Article Created with articleId :"+ret).build();
		}
	  
	  // Get all Articles
	  @Override
		public String getArticles(@PathParam("blogid") String blogId) {
			
			MongoArticleDB mc = new MongoArticleDB(conf1);
			System.out.println("array list: "+mc.getArticles(blogId).toString());
			ArrayList<Article> list = mc.getArticles(blogId);
			if(list.isEmpty())
				return "No Record Found !!!";
			System.out.println("calling getAll Articles");
			Type typeOfSrc = new TypeToken<List<Article>>(){}.getType();
			Gson gson=new Gson();
			String firstData=gson.toJson(list, typeOfSrc);
			return firstData;
				
		}
	  
	    @Override
		public String getArticle(@PathParam("blogid") String blogId,@PathParam("articleid") String articleId) {
			
			MongoArticleDB mc = new MongoArticleDB(conf1);
			System.out.println("array list: "+mc.getArticles(blogId).toString());
			ArrayList<Article> list = mc.getArticle(blogId,articleId);
			System.out.println("calling getAll Articles");
			if(list.isEmpty())
				return "No Record Found !!!";
			Type typeOfSrc = new TypeToken<List<Article>>(){}.getType();
			Gson gson=new Gson();
			String firstData=gson.toJson(list, typeOfSrc);
			return firstData;
		}
		
		//Delete Article
		
		@Override
		public Response deleteArticle(@PathParam("blogid") String blogId,
				@PathParam("articleid") String articleId, @PathParam("userid") String userId) {
			
			/// do the check
			System.out.println("The request is received...");
			
			MongoArticleDB mc1 = new MongoArticleDB(conf1);
			String rtn = mc1.delArticle(blogId, articleId, userId);
			System.out.println(rtn.substring(0, 6));
			if(rtn.substring(0, 6).equals("blogid"))
				return Response.status(200).entity("Article Deleted!!! \n" + rtn).build();
			else
				return Response.status(400).entity("Error Occured!!!").build();
		}
		
		
		public Response updateArticle(String blogId,String articleId, String userId, Article article){
			System.out.println("The request is received...");
			MongoArticleDB mc1 = new MongoArticleDB(conf1);
			String rtn = mc1.updateArticle(blogId, articleId, userId,article);
			System.out.println("return from db:" + rtn);
			if(rtn.equals("true"))
				return Response.status(200).entity("Article Updated Successfully!!! \n" + rtn).build();
			else
				return Response.status(400).entity("Error Occured!!!").build();
		}
	


}
