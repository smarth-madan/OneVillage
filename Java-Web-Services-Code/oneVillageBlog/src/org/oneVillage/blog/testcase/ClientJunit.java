package org.oneVillage.blog.testcase;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.apache.wink.client.RestClient;





import javax.ws.rs.core.MediaType;

import junit.framework.TestCase;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.ClientWebException;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;


public class ClientJunit extends TestCase {
	
	RestClient client;
	String url;
	 protected void setUp() throws Exception {
		 url = "http://localhost:8087/oneVillageBlog/blog";
		 client = new RestClient();
	}
	
	
	public void   testcreateblog1() {
		System.out.println("Test Case to Create a Blog");
		Resource resource1 = client.resource(url);
		System.out.println("URL: "+url);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("subject", "cmpe275");
			sendobject.put("description", "Apache Wink");
			sendobject.put("userid", "Smarth");
			sendobject.put("timestamp", "10:54");
			
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Create : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
		 System.out.println(created.getStatusCode());
		 System.out.println(created.getEntity(String.class));
	}
	
	public void   testcreateblog2() {
		System.out.println("Test Case to Create a Blog");
		Resource resource1 = client.resource(url);
		System.out.println("URL: "+url);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("subject", "sjsu_cmpe202");
			sendobject.put("description", "XML");
			sendobject.put("userid", "Shaunak");
			sendobject.put("timestamp", "11:53");
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Create : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
		 System.out.println(created.getStatusCode());
		 System.out.println(created.getEntity(String.class));
	}

	/*
	public void  testgetAllBlog() {
		try{
		System.out.println("Get All the created Groups");
		Resource resource = client.resource(url);
		System.out.println("URL: "+url);
		 String  created = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		 System.out.println("All Blogs :\n\n"+created.toString());
		 System.out.println();
		 System.out.println();
		}
		catch(ClientWebException e){
//			e.printStackTrace();
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}

		
	}
		
	
	public void   testgetBlog() {
		try{
			System.out.println("Get the Group for the requested Id");
			url=url+"/23";
			 Resource resource = client.resource(url);
			 System.out.println("URL: "+url);
			 String  created = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
			 if(created.equals("")){
				 System.out.println("Blog not Found!!!");
			 }
			 else{
				 System.out.println(created.toString());
				 System.out.println();
				 System.out.println();
			 }
		}
		catch(ClientWebException e){
//			e.printStackTrace();
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}
		
	}*/

	 /*
	public void   testcreateArticle1() {
	System.out.println("Test Case to Create a Article");
	url = url + "/3/article";
	Resource resource1 = client.resource(url);
	System.out.println("URL: "+url);
	JSONObject sendobject = new JSONObject();
	try {
		
		sendobject.put("content", "Article 1: abc basd odf sefh mans esfhe wehnsdfu uiwehf sdfjhhiwefd" +
				"wefjnwef jwef kjdsf huihsdf hew mdfh wefhwefm oehf sdfhweiwef jsdfsdfs");
		sendobject.put("userid", "Smarth");
		sendobject.put("timestamp", "12:44");
		
	
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String jsonStringObj = sendobject.toString();
	System.out.println("Create : "+jsonStringObj);
	ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
	 System.out.println(created.getStatusCode());
	 System.out.println(created.getEntity(String.class));
	}
	
	/*
	public void   testcreateArticle2(){
		System.out.println("Test Case to Create a Article");
		url = url + "/4/article";
		Resource resource1 = client.resource(url);
		System.out.println("URL: "+url);
		JSONObject sendobject = new JSONObject();
		try {
			
			sendobject.put("content", "Article 2: abc basd odf sefh mans esfhe wehnsdfu uiwehf sdfjhhiwefd" +
					"wefjnwef jwef kjdsf huihsdf hew mdfh wefhwefm oehf sdfhweiwef jsdfsdfs");
			sendobject.put("userid", "Shaunak");
			sendobject.put("timestamp", "12:44");
			
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String jsonStringObj = sendobject.toString();
		System.out.println("Create : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
		 System.out.println(created.getStatusCode());
		 System.out.println(created.getEntity(String.class));
	}
	
	/*
	public void  testgetAllArticles() {
		try{
		System.out.println("Get All the created Blog Entries");
		url = url + "/15/article";
		Resource resource = client.resource(url);
		System.out.println("URL: "+url);
		 String  created = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		 System.out.println(created.toString());
		 System.out.println();
		 System.out.println();
		}
		catch(ClientWebException e){
//			e.printStackTrace();
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}	
	}
	
	public void  testgetArticle() {
			try{
			System.out.println("Get Article");
			url = url + "/1/article/14";
			Resource resource = client.resource(url);
			System.out.println("URL: "+url);
			 String  created = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
			 System.out.println(created);
			 System.out.println();
			 System.out.println();
			}
			catch(ClientWebException e){
//				e.printStackTrace();
				System.out.println("The Exception message is "+e.getResponse().getMessage());
			}
	 }
	 
	
	public void   testremoveArticle() {
			try{
		        System.out.println("Remove a blog by the blog owner");
				url = url + "/1/article/14/Shaunak";
				System.out.println("URL : "+url);
				Resource resource6 = client.resource(url);
				ClientResponse b1 = resource6.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete();
				System.out.println("post return value: "+b1);
				System.out.println(b1.getStatusCode());
				System.out.println(b1.getEntity(String.class));
			}
			catch(ClientWebException e){
	//			e.printStackTrace();
				System.out.println("The Exception message is "+e.getResponse().getMessage());
			}
				
	}
	
	
	public void   testUpdateArticle() {
			try{
		        System.out.println("Update an Article blog owner");
				url = url + "/1/article/14/Smarth";
				System.out.println("URL : "+url);
				Resource resource6 = client.resource(url);
				
				JSONObject sendobject = new JSONObject();
				try {
					
					sendobject.put("content", "My Article 1: lsdhfisudhfiushdfiu");
					sendobject.put("userid", "Smarth");
					sendobject.put("timestamp", "12:44");
					sendobject.put("blogid", "1");
					sendobject.put("articleid","14");
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String send = sendobject.toString();
				ClientResponse b1 = resource6.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(send);
				System.out.println("post return value: "+b1);
				System.out.println(b1.getStatusCode());
				System.out.println(b1.getEntity(String.class));
			}
			catch(ClientWebException e){
	//			e.printStackTrace();
				System.out.println("The Exception message is "+e.getResponse().getMessage());
			}
				
	}*/
	
	 /*
	public void   testremoveBlog1() {
		try{
	        System.out.println("Remove a blog by the blog owner");
			url = url + "/3/Smarth";
			System.out.println("URL : "+url);
			Resource resource6 = client.resource(url);
			ClientResponse b1 = resource6.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete();
			System.out.println("post return value: "+b1.getStatusCode());
			System.out.println(b1.getEntity(String.class));
		}
		catch(ClientWebException e){
//			e.printStackTrace();
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}
			
	}*/

}
		