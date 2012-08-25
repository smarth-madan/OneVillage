package org.oneVillage.photo.resource;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.apache.wink.common.model.multipart.InMultiPart;
import org.apache.wink.common.model.multipart.InPart;
import org.json.JSONException;
import org.json.JSONObject;
import org.oneVillage.photo.data.Album;
import org.oneVillage.photo.data.Photo;
import org.oneVillage.photo.db.MongoAlbumDB;
import org.oneVillage.photo.db.MongoPhotoDB;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PhotoServiceImpl implements PhotoService {

	private Properties conf;
	private Properties conf1;
	private Properties properties;
	String url;
	RestClient client;
	//Logger logger = Logger.getLogger(PhotoServiceImpl.class);

	public PhotoServiceImpl() {
		conf = new Properties();
		conf.setProperty(MongoAlbumDB.sHost, "localhost");
		conf.setProperty(MongoAlbumDB.sDB, "oneVillageDb");
		conf.setProperty(MongoAlbumDB.sCollection, "album");

		conf1 = new Properties();
		conf1.setProperty(MongoAlbumDB.sHost, "localhost");
		conf1.setProperty(MongoAlbumDB.sDB, "oneVillageDb");
		conf1.setProperty(MongoAlbumDB.sCollection, "photo");

		properties = new Properties();
		try {
			properties.load(PhotoServiceImpl.class.getResourceAsStream("photo.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		url=properties.getProperty("guestUrl");
		//url = "http://localhost:8088/onevillage-photo-test/photoStrand/v0";
		client = new RestClient();

	}

	@Override
	public String createAlbum(Album album) {

		/// do the check
		System.out.println("The request to Create an album is received!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
		JSONObject jsonObject = new JSONObject();
		Pattern pattern = Pattern.compile("^[a-t]");
		CharSequence inputStr = album.getUserid();
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		if(matcher.find())
		{
			System.out.println("The data matched...>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			MongoAlbumDB mongoAlbum = new MongoAlbumDB(conf);
			String albumId = mongoAlbum.createAlbum(album);
			try {
				if(!"NC".equals(albumId)){
					//success
					jsonObject.put("album_id", albumId);
				}
				else{
					//failure
					jsonObject.put("album_id", "");

				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{

			url = url+"/albums/album";
			System.out.println("Re - routing to other server: url:"+url);
			Resource resource = client.resource(url);
			JSONObject sendobject = new JSONObject();
			try {
				sendobject.put("name", album.getName());
				sendobject.put("description", album.getDescription());
				sendobject.put("username", album.getUserid());
				String jsonStringObj = sendobject.toString();
				System.out.println("Create : "+jsonStringObj);
				ClientResponse created = resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
				System.out.println("Redirect Response Status: "+created.getStatusCode());
				System.out.println(", Message :" +created.getMessage()+  ", Entity :"+created.getEntity(String.class));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("request was completed");
		}

		return jsonObject.toString();

	}

	@Override
	public String updateAlbum(Album album,String albumId) {

		Response returnResponse=null;
		/// do the check
		System.out.println("The request to update an album is received...");
		Pattern pattern = Pattern.compile("^[a-t]");
		CharSequence inputStr = album.getUserid();
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		if(matcher.find())
		{
			System.out.println("The data matched.");
			MongoAlbumDB mongoAlbum = new MongoAlbumDB(conf);
			returnResponse = mongoAlbum.updateAlbum(album, albumId);
			System.out.println("Reuslt:::"+returnResponse.getEntity().toString());
			return returnResponse.getEntity().toString();
		}else{

			url = url+"/albums/album-"+albumId;
			System.out.println("Re - routing to other server: url:"+url);
			Resource resource = client.resource(url);
			JSONObject sendobject = new JSONObject();
			try {
				sendobject.put("name", album.getName());
				sendobject.put("description", album.getDescription());
				sendobject.put("username", album.getUserid());
				String jsonStringObj = sendobject.toString();
				System.out.println("Create : "+jsonStringObj);
				ClientResponse created = 	resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(jsonStringObj);
				System.out.println("Redirect Response Status: "+created.getStatusCode());
				System.out.println(", Message :" +created.getMessage()+  ", Entity :"+created.getEntity(String.class));
				System.out.println("request was completed");
				if(created.getStatusCode()==200)
					return "Album Updtated Successfully";
				else
					return "Bad Request";

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return null;
	}

	@Override
	public String deleteAlbum(String albumId, String userId) {

		Response returnResponse=null;
		/// do the check
		System.out.println("The request to delete an album is received...");
		System.out.println("albumId="+albumId);
		System.out.println("userId="+userId);
		Pattern pattern = Pattern.compile("^[a-t]");
		CharSequence inputStr = userId;
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		if(matcher.find())
		{
			System.out.println("The data matched.");
			MongoAlbumDB mongoAlbum = new MongoAlbumDB(conf);
			returnResponse = mongoAlbum.deleteAlbum(albumId, userId);
			return returnResponse.getEntity().toString();
		}else{

			url = url+"/albums/album-"+albumId;
			System.out.println("Re - routing to other server: url:"+url);
			Resource resource = client.resource(url);

			ClientResponse delete = resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete();
			System.out.println("Redirect Response Status: "+delete.getStatusCode());
			System.out.println(", Message :" +delete.getMessage()+  ", Entity :"+delete.getEntity(String.class));
			System.out.println("request was completed");
			if(delete.getStatusCode()==204)
				return "Album Deleted Successfully";
			else
				return "Bad Request";
		}

	}

	private ArrayList<Album> getAlbumsFromServer(){
		Gson gson=new Gson();
		Type typeOfSrc = new TypeToken<List<Album>>(){}.getType();
		String ret="";
		url = url+"/albums/albums?";
		try {
		Resource resource = client.resource(url);
		System.out.println("Before fetching all the Albums from....");
		String  created = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		ArrayList<Album> albumList = gson.fromJson(created, typeOfSrc);
		return albumList;
		}catch(Exception e){
			return null;
		}
	}
	
	@Override
	public String getAllAlbums() {

		String albumsAsString= "";
		/// do the check
		System.out.println("The request to get all Albums is received...");
		//Pattern pattern = Pattern.compile("^[a-z]");
		//System.out.println("The data matched.");
		MongoAlbumDB mongoAlbum = new MongoAlbumDB(conf);
		ArrayList<Album> albumList = mongoAlbum.getAllAlbums();
		
		ArrayList<Album> albumList2 =  getAlbumsFromServer();
		
		Iterator<Album> it = albumList2.iterator();
		while(it.hasNext())
		{
			Album tempAlbum = it.next();
			Album newAlbumObject = new Album();
			newAlbumObject.setAlbumid(tempAlbum.getId());
			newAlbumObject.setName(tempAlbum.getName());
			newAlbumObject.setDescription(tempAlbum.getDescription());
			newAlbumObject.setUserid(tempAlbum.getUsername());
			newAlbumObject.setCreatedDate(tempAlbum.getCreatedDate());
			albumList.add(newAlbumObject);
		}
			
		System.out.println("Inside service... albumList size ="+albumList.size());
		if(albumList.size()!=0){
			Gson gson=new Gson();
			Type typeOfSrc = new TypeToken<List<Album>>(){}.getType();
			albumsAsString=gson.toJson(albumList, typeOfSrc);
			System.out.println("Albums as String:\n"+albumsAsString+"\n");
		}

		return albumsAsString;
	}

	@Override
	public String getAlbumsByUser(String userId) {


		/// do the check
		System.out.println("The request to get all albums by user is received...");
		Pattern pattern = Pattern.compile("^[a-t]");
		CharSequence inputStr = userId;
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		String albumsAsString= "";

		if(matcher.find())
		{

			MongoAlbumDB mongoAlbum = new MongoAlbumDB(conf);
			ArrayList<Album> albumList = mongoAlbum.getAllAlbumsByUser(userId);
			System.out.println("Inside service... albumList size ="+albumList.size());
			if(albumList.size()!=0){
				Type typeOfSrc = new TypeToken<List<Album>>(){}.getType();
				Gson gson=new Gson();
				albumsAsString=gson.toJson(albumList, typeOfSrc);
				System.out.println("Albums as String:\n"+albumsAsString+"\n");
			}

			return albumsAsString;
		}else{

			System.out.println("Re - routing to other server");
			ArrayList<Album> albumList = new ArrayList<Album>();
			ArrayList<Album> albumList2 =  getAlbumsFromServer();
			Iterator<Album> it = albumList2.iterator();
			while(it.hasNext())
			{
				Album tempAlbum = it.next();
				Album newAlbumObject = new Album();
				newAlbumObject.setAlbumid(tempAlbum.getId());
				newAlbumObject.setName(tempAlbum.getName());
				newAlbumObject.setDescription(tempAlbum.getDescription());
				newAlbumObject.setUserid(tempAlbum.getUsername());
				newAlbumObject.setCreatedDate(tempAlbum.getCreatedDate());
				albumList.add(newAlbumObject);
			}
			
			if(albumList.size()!=0){
				Gson gson=new Gson();
				Type typeOfSrc = new TypeToken<List<Album>>(){}.getType();
				albumsAsString=gson.toJson(albumList, typeOfSrc);
				System.out.println("Albums as String:\n"+albumsAsString+"\n");
			}
			System.out.println("request was completed");
			return albumsAsString;


		}
	}

	private File rootDir() {
		String path = properties.getProperty("photoLocation");
		File dir = new File(path);
		if (!dir.exists())
			dir.mkdir();

		return dir;
	}

	@Override
	public String addPhoto(String albumId,String fileName,InMultiPart inMultiPart) {
		Photo photo = new Photo();
		photo.setAlbumid(albumId);
		/// do the check
		System.out.println("The request to add a photo is received!");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("albumId = "+albumId);
		System.out.println("fileName = "+fileName);
		if(inMultiPart!=null)
			System.out.println(inMultiPart.hasNext());
		else
			System.out.println("Inmultipart not recieved");
		StringBuilder sb = new StringBuilder();

		System.out.println("---> SVR: file");
		
		try{
		while (inMultiPart!=null && inMultiPart.hasNext()) {
			System.out.println("Inside loop");
			File f = new File(rootDir(), fileName);
			BufferedOutputStream fos = null;
			try {
				fos = new BufferedOutputStream(new FileOutputStream(f));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			InPart part = inMultiPart.next();
						
			//System.out.println("Individual Part:"+part.getHeadersName());
			InputStream is = part.getInputStream();

			// debugging
			MultivaluedMap<String, String> map = part.getHeaders();
			for (String k : map.keySet()) {
				System.out.println("---> part hdr: " + k + " -> " + map.get(k));
				if("userId".equals(k)){
					photo.setUserid( map.getFirst(k));
				}
				else if("latitude".equals(k)){
					photo.setLatitude( map.getFirst(k));
				}
				else if("longitude".equals(k)){
					photo.setLongitude( map.getFirst(k));
				}
				else if("createdDate".equals(k)){
					photo.setCreatedDate( map.getFirst(k));
				}
				else if("description".equals(k)){
					photo.setDescription( map.getFirst(k));
				}
				/*if (part.getHeaders().containsKey("Content-Disposition"))
					HeaderParser.parse(map.get("Content-Disposition"));*/
			}

			if (part.getHeaders().containsKey("Content-Disposition")) {
				ArrayList<String> list = (ArrayList<String>) part.getHeaders().get("Content-Disposition");
				// String filename = list.get("filename");
				// if (filename != null)
				{
					try {
						int size = FileCopy.copy(is, fos);

						sb.append("uploaded a file, ").append(fileName).append(", size = ").append(size).append(" bytes\n");
						photo.setUrl(f.getAbsolutePath());
						System.out.println("---> " + String.format("%s, created", f.getAbsolutePath()));
					} finally {
						try {
							fos.flush();
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}
			}
		}
	} catch(Exception e){
		e.printStackTrace();
	}
		System.out.println("Photo Data");
		System.out.println("albumId="+photo.getAlbumid());
		System.out.println("userId="+photo.getUserid());
		System.out.println("desc="+photo.getDescription());
		System.out.println("lat="+photo.getLatitude());
		System.out.println("lon="+photo.getLongitude());
		photo.setUrl(fileName);
		MongoPhotoDB mongoPhoto = new MongoPhotoDB(conf1);
		mongoPhoto.addPhoto(photo);
		return sb.toString();
	}
	
	private ArrayList<Photo> getPhotosFromServer(String albumId){
		Gson gson=new Gson();
		Type typeOfSrc = new TypeToken<List<Photo>>(){}.getType();
		String ret="";
		url = url+"/albums/album-"+albumId+"/photos?";
		try {
		Resource resource = client.resource(url);
		System.out.println("Before fetching all the Albums from....");
		String  created = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
		ArrayList<Photo> photoList = gson.fromJson(created, typeOfSrc);
		return photoList;
		}catch(Exception e){
			return null;
		}
	}
	

	@Override
	public String getAllPhotosFromAlbum(String albumId,String userid) {

		/// do the check
		System.out.println("The request to get all photos from an Album is received...");
		Pattern pattern = Pattern.compile("^[a-t]");
		ArrayList<Photo> photoList = new ArrayList<Photo>();
		CharSequence inputStr = userid;
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		String photosAsString = "";
		if(matcher.find())
		{
			System.out.println("The data matched.");
			MongoPhotoDB mongoPhoto = new MongoPhotoDB(conf1);
			photoList = mongoPhoto.getAllPhotosFromAlbum(albumId);
			System.out.println("Inside service... albumList size ="+photoList.size());

		}else{

			System.out.println("Re - routing to other server");
			ArrayList<Photo> photoList2 =  getPhotosFromServer(albumId);
			Iterator<Photo> it = photoList2.iterator();
			while(it.hasNext())
			{
				Photo tempPhoto = it.next();
				Photo newPhotoObject = new Photo();
				newPhotoObject.setPhotoid(tempPhoto.getId());
				newPhotoObject.setAlbumid(albumId);
				newPhotoObject.setDescription(tempPhoto.getDescription());
				newPhotoObject.setUserid(tempPhoto.getUsername());
				newPhotoObject.setCreatedDate(tempPhoto.getCreatedDate());
				newPhotoObject.setLatitude(tempPhoto.getLat());
				newPhotoObject.setLongitude(tempPhoto.getLon());
				newPhotoObject.setUrl(tempPhoto.getUrl());
				photoList.add(newPhotoObject);
			}
			System.out.println("request was completed");

		}
		
		if(photoList.size()!=0){
			Type typeOfSrc = new TypeToken<List<Photo>>(){}.getType();
			Gson gson=new Gson();
			photosAsString=gson.toJson(photoList, typeOfSrc);
			System.out.println("Albums as String:\n"+photosAsString+"\n");
		}


		return photosAsString;
	}

	@Override
	public String updatePhoto(Photo photo,@PathParam("albumId") String albumId,@QueryParam("userId") String userId) {
		/// do the check
		Response returnResponse = null;
		if(photo.getUserid()==null){
			photo.setUserid(userId);
		}
		System.out.println("The request to update a photo is received...");
		Pattern pattern = Pattern.compile("^[a-t]");
		CharSequence inputStr = photo.getUserid();
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		if(matcher.find())
		{
			System.out.println("The data matched.");
			MongoPhotoDB mongoPhoto = new MongoPhotoDB(conf1);
			returnResponse = mongoPhoto.updatePhoto(photo,albumId);
			return returnResponse.getEntity().toString();
		}else{

			url = url+"/albums/album-"+albumId+"/photo-"+photo.getPhotoid();
			System.out.println("Re - routing to other server: url:"+url);
			Resource resource = client.resource(url);
			JSONObject sendobject = new JSONObject();
			try {
				sendobject.put("description", photo.getDescription());
				sendobject.put("lat", photo.getLatitude());
				sendobject.put("lon", photo.getLongitude());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String jsonStringObj = sendobject.toString();
			System.out.println("Send Object:" + sendobject.toString());
			ClientResponse update =  resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(jsonStringObj);
			System.out.println("Redirect Response Status: "+update.getStatusCode());
			System.out.println("request was completed");
			if (update.getStatusCode()==200)
				return "Photo Updtated Successfully";
			else
				return "Bad Request: Needs UserId, PhotoId and albumId";
		}
	}


	@Override
	public Response deletePhoto(@PathParam("albumId") String albumId, @QueryParam("userId") String userId, @QueryParam("photoId") String photoId) {

		/// do the check
		Response returnResponse = null;
		System.out.println("The request to delete a photo is received...");
		Pattern pattern = Pattern.compile("^[a-t]");
		System.out.println("albumId ="+albumId);
		System.out.println("userId ="+userId);
		System.out.println("photoId ="+photoId);
		CharSequence inputStr = userId;
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		if(matcher.find())
		{
			System.out.println("The data matched.");
			MongoPhotoDB mongoPhoto = new MongoPhotoDB(conf1);
			returnResponse = mongoPhoto.deletePhoto(albumId, userId, photoId);
			return returnResponse;
		}else{
			System.out.println("Re - routing to other server");
			url = url+"/albums/album-"+albumId+"/photo-"+photoId;
			System.out.println("Re - routing to other server: url:"+url);
			Resource resource = client.resource(url);

			ClientResponse delete = resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete();
			System.out.println("Redirect Response Status: "+delete.getStatusCode());
			System.out.println(", Message :" +delete.getMessage()+  ", Entity :"+delete.getEntity(String.class));
			System.out.println("request was completed");
			if(delete.getStatusCode()==204)
				return Response.status(200).entity("Photo Deleted Successfully").build();
			else
				return Response.status(200).entity("Photo not deleted on the other strand").build();
		}

	}

	@Override
	public String associatePhotoToArticle(@PathParam("albumId") String albumId,String request) {
		/// do the check
		System.out.println("The request to tag a photo to article is received...");
		System.out.println("albumId="+albumId);
		Response returnResponse = null;
		JSONObject json;
		String photoId = null;
		String articleId = null;
		String tagger = null;
		try {
			json = new JSONObject(request);
			photoId = (String)json.get("photoId");
			articleId = (String)json.get("articleId");
			tagger = (String)json.get("articleTagger");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println("photoId="+photoId);
		System.out.println("articleId="+articleId);
		System.out.println("tagger="+tagger);

		Pattern pattern = Pattern.compile("^[a-t]");

		CharSequence inputStr = tagger;
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		if(matcher.find())
		{
			System.out.println("The data matched.");
			MongoPhotoDB mongoPhoto = new MongoPhotoDB(conf1);
			returnResponse = mongoPhoto.associatePhotoToArticle(albumId,photoId,articleId,tagger);
			System.out.println("Result:" +returnResponse.getEntity().toString());
			return returnResponse.getEntity().toString();
		}else{

			System.out.println("Re - routing to other server");
			System.out.println("request was completed");
			return "This feature not available !!!";
		}

	}

	@Override
	public String associatePhotoToCommunity(@PathParam("albumId") String albumId,String request) {
		/// do the check
		String communityId = null;
		Response returnResponse = null;
		System.out.println("The request to tag a photo to community is received...");
		System.out.println("albumId="+albumId);
		System.out.println("communityId="+communityId);
		JSONObject json;
		String photoId = null;
		String tagger = null;
		try {
			json = new JSONObject(request);
			photoId = (String)json.get("photoId");
			if(communityId==null)
				communityId = (String)json.get("communityId");
			tagger = (String)json.get("communityTagger");
		} catch (JSONException e) {
			e.printStackTrace();
		}

		System.out.println("photoId="+photoId);
		System.out.println("communityId="+communityId);
		System.out.println("tagger="+tagger);

		Pattern pattern = Pattern.compile("^[a-t]");

		CharSequence inputStr = tagger;
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		if(matcher.find())
		{
			System.out.println("The data matched.");
			MongoPhotoDB mongoPhoto = new MongoPhotoDB(conf1);
			returnResponse = mongoPhoto.associatePhotoToCommunity(albumId,photoId,communityId,tagger);
			return returnResponse.getEntity().toString();
		}else{

			System.out.println("Re - routing to other server");
			System.out.println("request was completed");
			return "This feature not available !!!";
		}

	}

	/*
	@Override
	public String getPhotoFromAlbum(@PathParam("albumId") String albumId,@QueryParam("photoId") String photoId) {

		/// do the check
		System.out.println("The request to get a photo from album is received...");
		Pattern pattern = Pattern.compile("^[a-z]");
		CharSequence inputStr = "";//photo.getUserid();
		Matcher matcher = pattern.matcher(inputStr);
		System.out.println("before decision");
		String photoAsString = null;
		System.out.println("album id :"+albumId+" photoId:"+photoId);
		if(matcher.find())
		{
			System.out.println("The data matched.");
			MongoPhotoDB mongoPhoto = new MongoPhotoDB(conf);
			Photo photo = mongoPhoto.getPhotoFromAlbum(albumId,photoId);
			if(photo!=null){
				Type typeOfSrc = new TypeToken<Photo>(){}.getType();
				Gson gson=new Gson();
				photoAsString=gson.toJson(photo, typeOfSrc);
				System.out.println("Photos as String:\n"+photoAsString+"\n");
			}
			return photoAsString;
		}else{

			System.out.println("Re - routing to other server");
			System.out.println("request was completed");
			return null;
		}
	}*/

	@Override
	public String getPhotoByCommunity(String communityId) {


		/// do the check
		System.out.println("The request to get photos by community is received...");
		
		String photosAsString = "";

		MongoPhotoDB mongoPhoto = new MongoPhotoDB(conf1);
		ArrayList<Photo>  photoList = mongoPhoto.getPhotoByCommunity(communityId);
		System.out.println("Inside service... albumList size ="+photoList.size());
		if(photoList.size()!=0){
			Type typeOfSrc = new TypeToken<List<Photo>>(){}.getType();
			Gson gson=new Gson();
			photosAsString=gson.toJson(photoList, typeOfSrc);
			System.out.println("Photos as String:\n"+photosAsString+"\n");
		}

		return photosAsString;
		}

	@Override
	public String getPhotoByArticle(String articleId) {


		/// do the check
		System.out.println("The request to get photos by article is received...");
		String photosAsString = "";

			MongoPhotoDB mongoPhoto = new MongoPhotoDB(conf1);
			ArrayList<Photo>  photoList = mongoPhoto.getPhotoByArticle(articleId);
			System.out.println("Inside service... albumList size ="+photoList.size());
			if(photoList.size()!=0){
				Type typeOfSrc = new TypeToken<List<Photo>>(){}.getType();
				Gson gson=new Gson();
				photosAsString=gson.toJson(photoList, typeOfSrc);
				System.out.println("Albums as String:\n"+photosAsString+"\n");
			}

			return photosAsString;
				
	}
}
