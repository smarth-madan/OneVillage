package org.oneVillage.photo.testcase;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import javax.ws.rs.core.MediaType;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.common.model.multipart.BufferedOutMultiPart;
import org.apache.wink.common.model.multipart.OutPart;
import org.apache.wink.json4j.JSONException;
import org.json.JSONObject;

public class ClientJunitPhoto extends TestCase{

	RestClient client;
	String url;
	Logger logger = Logger.getLogger(ClientJunitPhoto.class);
	public void setUp() throws Exception {
		url = "http://localhost:8087/OneVillagePhoto/photo";
		client = new RestClient();
	}

	/*
	public void   testCreateAlbum() {
		logger.info("Test Case to Create an album");
		logger.info("URL: "+url);
		String actualUrl = url+"/album";
		Resource resource1 = client.resource(actualUrl);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("name", "Friends");
			sendobject.put("description", "SJSU Friends");
			sendobject.put("userid", "vikas@gmail.com");
			sendobject.put("createdDate", "12:53");


		}  catch (org.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Create : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
		System.out.println(created.getStatusCode());
		System.out.println(created.getEntity(String.class));
	}*/
	
	/*
	public void testUpdateAlbum() {
		logger.info("Test Case to Create an album");
		logger.info("URL: "+url);
		String actualUrl = url+"/album/4";
		Resource resource1 = client.resource(actualUrl);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("name", "GOA");
			sendobject.put("description", "Go GOaaaa3333333a.....");
			sendobject.put("userid", "vikas@gmai.com");
		} catch (org.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Update : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(jsonStringObj);
		System.out.println(created.getStatusCode());
		System.out.println(created.getEntity(String.class));
	}
	
	*//*
	public void testDeleteAlbum() {
		System.out.println("Test Case to Create an album");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/4/vikas@gmail.com";
		Resource resource1 = client.resource(actualUrl);
		System.out.println("Before deleting the Album....");
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete();
		System.out.println(created.getStatusCode());
		System.out.println(created.getEntity(String.class));
	}
	/*
	public void testGetAllAlbums() {
		System.out.println("Test Case to get all albums");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album";
		Resource resource1 = client.resource(actualUrl);
		System.out.println("Before fetching all the Albums....");
		String  created = (String)resource1.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("Albums: "+created);
	}
	
	/*
	public void testGetAlbumsByUser() {
		System.out.println("Test Case to get albums by user");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/999";
		Resource resource1 = client.resource(actualUrl);
		System.out.println("Before fetching all the Albums....");
		String  created = (String)resource1.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("Albums: "+created);
		}
	

	/*public void   testAddPhoto() {
		System.out.println("Test Case to Add a Photo");
		String actualUrl = url+"/album/12";
		System.out.println("URL: "+url);
		Resource resource1 = client.resource(actualUrl);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("userid", "shaunakkhedkar@gmail.com");
			sendobject.put("latitude", "105 N");
			sendobject.put("longitude", "158 E");
			sendobject.put("createdDate", "12:53");


		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("PHOTO: "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.MULTIPART_FORM_DATA).accept(MediaType.MULTIPART_FORM_DATA).post(jsonStringObj.toString());
		System.out.println(created.getStatusCode());
		System.out.println(created.getEntity(String.class));
	}*/
	
	/*
	public void testAddPhoto1(){
		System.out.println("Test Case to Add a Photo");
		String actualUrl = url+"/album/1/book8.gif";
		System.out.println("URL: "+url);
		Resource resource1 = client.resource(actualUrl);
		String boundary = "simple boundary";
		// Convert  file into byte array
		File file = new File("C:\\book8.gif");
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte[] buf = new byte[1024];
	        try {
	            for (int readNum; (readNum = fin.read(buf)) != -1;) {
	                bos.write(buf, 0, readNum); 
	                //no doubt here is 0
	                //Writes len bytes from the specified byte array starting at offset 
	                //off to this byte array output stream.
	                System.out.println("read " + readNum + " bytes,");
	            }
	        } catch (IOException ex) {
	            //Logger.getLogger(ConvertImage.class.getName()).log(Level.SEVERE, null, ex);
	        }
	        byte[] bytes = bos.toByteArray();
	           
	        
	        
		// Create Request for File
		String fileName = file.getName();
		String userId = "shaunakkhedkar@gmail.com";
		String latitude = "104 N";
		String longitude = "108 W";
		String description = "Sexy Photo";
		String timeTaken = "12:00";
		
		System.out.println("fileName="+fileName);
		BufferedOutMultiPart requestEntity = new BufferedOutMultiPart();
		OutPart outPart = new OutPart();
		outPart.setContentType("application/octet-stream; name="+fileName);
		
		//System.out.println("File data ="+rwCh.);
		outPart.setBody(bytes);
		outPart.addHeader("Content-Transfer-Encoding", "binary");
		outPart.addHeader("Content-Disposition", "form-data; name=\"" + fileName + "\"; fileName=\"" + fileName + "\"");
		outPart.addHeader("userId",userId);
		outPart.addHeader("latitude",latitude);
		outPart.addHeader("longitude",longitude);
		outPart.addHeader("description",description);
		outPart.addHeader("timeTaken",timeTaken);
		//outPart.addHeader("Content-Disposition", "form-data; name=\"" + userId + "\"; userId=\"" + userId + "\"");
		requestEntity.addPart(outPart);
		
	
		Iterator<OutPart> itr = requestEntity.getIterator();
		while(itr.hasNext()){
			System.out.println("data =" +itr.next().getHeaders().toString());
		}
		System.out.println("Before Sending the request");
		String response = resource1.header("Content-Type", "multipart/form-data; boundary="+boundary).post(String.class, requestEntity);
		
		System.out.println("Response ="+response);
	}
	
	/*public void testGetAllPhotosFromAlbum() {
		System.out.println("Test Case to get all photos from album");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/12";
		Resource resource1 = client.resource(actualUrl);
		System.out.println("Before fetching all the photos....");
		String  created = (String)resource1.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("Albums: "+created);
		}
	*//*
	public void testUpdatePhoto() {
		System.out.println("Test Case to Update photo");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/5/photo?userId=zzilof@gmail.com";
		Resource resource1 = client.resource(actualUrl);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("description", "some desc");
			sendobject.put("photoid", "5");
			sendobject.put("userid", "zzilof@gmail.com");
			sendobject.put("latitude", "12.34");
			sendobject.put("longitude", "25.34");

		} catch (org.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Update : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(jsonStringObj);
		System.out.println(created.getStatusCode());
		System.out.println(created.getEntity(String.class));
	}*/
	
	
	public void testUpdatePhoto2() {
		System.out.println("Test Case to Update photo");
		System.out.println("URL: "+url);
		String actualUrl = "http://localhost:8088/onevillage-photo-test/photoStrand/v0/albums/album-5/photo-5";
		Resource resource1 = client.resource(actualUrl);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("description", "some descc22");
			sendobject.put("lat","2.4");
			sendobject.put("lon", "-3");

		} catch (org.json.JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Update : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(jsonStringObj);
		System.out.println(created.getStatusCode());
		System.out.println(created.getEntity(String.class));
	}

	/*public void testDeletePhoto() {
		System.out.println("Test Case to delete a photo");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/12?userId=shaunakkhedkar@gmail.com&photoId=28";
		Resource resource1 = client.resource(actualUrl);
		System.out.println("Before deleting the Photo....");
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete();
		System.out.println(created.getStatusCode());
		System.out.println(created.getEntity(String.class));
	}*/
	
	/*public void testAssociatePhotoToArticle() {
		System.out.println("Test Case to associate a photo to article");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/12";
		Resource resource1 = client.resource(actualUrl);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("articleId", "A1000");
			sendobject.put("photoId", "21");
			sendobject.put("articleTagger", "vDahuja@gmail.com");
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Update : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
		System.out.println(created.getStatusCode());
		System.out.println(created.getEntity(String.class));
	}
	*/
	/*public void testAssociatePhotoToCommunity() {
		System.out.println("Test Case to associate a photo to community");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/12/community";
		Resource resource1 = client.resource(actualUrl);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("communityId", "C9991");
			sendobject.put("photoId", "21");
			sendobject.put("communityTagger", "vDahuja@gmail.com");
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Update : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
		System.out.println(created.getStatusCode());
		System.out.println(created.getEntity(String.class));
	}*/
	
	/*public void testGetPhotoFromAlbum() {
		System.out.println("Test Case to get albums by user");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/1?photo_id=4";
		Resource resource1 = client.resource(actualUrl);
		System.out.println("Before fetching all the Albums....");
		String  created = (String)resource1.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("Albums: "+created);
		}*/
	
	/*public void testGetPhotosByCommunityId() {
		System.out.println("Test Case to get photos by community Id");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/communityId=C1000";
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("communityId", "C1000");
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Update : "+jsonStringObj);
		Resource resource1 = client.resource(actualUrl);
		System.out.println("Before fetching all the Albums....");
		String  created = (String)resource1.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("Albums: "+created);
		}
	
	public void testGetPhotosByArticleId() {
		System.out.println("Test Case to get photos by Article Id");
		System.out.println("URL: "+url);
		String actualUrl = url+"/album/articleId=C1000";
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("communityId", "C1000");
			} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Update : "+jsonStringObj);
		Resource resource1 = client.resource(actualUrl);
		System.out.println("Before fetching all the Albums....");
		String  created = (String)resource1.accept(MediaType.APPLICATION_JSON).get(String.class);
		System.out.println("Albums: "+created);
		}*/
}