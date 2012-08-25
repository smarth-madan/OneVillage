package org.oneVillage.group.testcase;

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
		url = "http://localhost:8087/oneVillageGroup/group";
		client = new RestClient();
	}
	
	/*
	public void   testJoinGroup() {
		try{
			System.out.println("Join a group");
			url=url+"/groupmember/6";
			Resource resource = client.resource(url);
			System.out.println("URL: "+url);
			JSONObject sendobject = new JSONObject();
			sendobject.put("userId", "vicky2");
			String jsonStringObj = sendobject.toString();
			ClientResponse created =  resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
			System.out.println(created.getStatusCode());
			System.out.println(created.getEntity(String.class));
			System.out.println();
		}
		catch(ClientWebException e){
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}/*
	
	public void  testUnJoinGroup() {
		try{
			System.out.println("unJoin a group");
			url=url+"/groupmember/5/rocky";
			Resource resource = client.resource(url);
			System.out.println("URL: "+url);
			JSONObject sendobject = new JSONObject();
			sendobject.put("userId", "rocky");
			String jsonStringObj = sendobject.toString();
			ClientResponse created =  resource.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete();
			System.out.println(created.getStatusCode());
			System.out.println(created.getEntity(String.class));
			System.out.println();
		}
		catch(ClientWebException e){
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	*/
	public void   testgetAllMembers() {
		try{
			System.out.println("Get all the members of a group");
			url = url+"/groupmember/6";
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
	
	/*
	public void  testGetAllGroup() {
		try{
			System.out.println("Get All the created Groups");
			Resource resource = client.resource(url);
			System.out.println("URL: "+url);
			String  created = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
			System.out.println("All Groups :\n\n"+created.toString());
			System.out.println();
			System.out.println();
		}
		catch(ClientWebException e){
			//			e.printStackTrace();
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}


	}
	/*
	//***************GET ALL EVENTS***************/
	/*
	public void   testGetAllEvents1() {
		try{
			System.out.println("Get All Events of a Group");
			url = url + "/5/event";
			Resource resource = client.resource(url);
			System.out.println("URL: "+url);
			String  allEvents = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
			System.out.println(allEvents.toString());
			System.out.println();
			System.out.println();
		}
		catch(ClientWebException e){
			//				e.printStackTrace();
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}
	}

/*
	
	public void   testCreateGroup1() {
		System.out.println("Test Case to Create a Group");
		Resource resource1 = client.resource(url);
		System.out.println("URL: "+url);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("name", "CMPE275");
			sendobject.put("userId", "smarth");
			sendobject.put("description", "EST");
			sendobject.put("timestamp", "10:56");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonStringObj = sendobject.toString();
		System.out.println("Create : "+jsonStringObj);
		ClientResponse created = resource1.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).post(jsonStringObj);
		System.out.println(created.getStatusCode());

		System.out.println(created.getEntity(String.class));
		System.out.println();
	}


	/*
	public void   testGetGroup() {
		try{
			System.out.println("Get the Group for the requested Id");
			url=url+"/1";
			Resource resource = client.resource(url);
			System.out.println("URL: "+url);
			String  retGroup = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
			if(retGroup.isEmpty()){
				System.out.println("Group Id does not exist"); 
			}
			else{
				System.out.println(retGroup);
				System.out.println();
				System.out.println();
			}
		}
		catch(ClientWebException e){
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}

	}*/

	/*
	public void   testDeleteGroup1() {
		try{
			System.out.println("Remove a Group by the Group owner");
			url = url + "/5/smarth";
			System.out.println("URL : "+url);
			Resource resource6 = client.resource(url);
			ClientResponse b1 = resource6.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).delete();
			System.out.println("post return value: "+b1);
			System.out.println(b1.getStatusCode());
			System.out.println(b1.getEntity(String.class));
		}
		catch(ClientWebException e){
			//				e.printStackTrace();
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}

	}

	/*
	public void   testCreateEvent1() {
		System.out.println("Test Case to Create an Event");
		url = url + "/5/event";
		Resource resource1 = client.resource(url);
		System.out.println("URL: "+url);
		JSONObject sendobject = new JSONObject();
		try {
			sendobject.put("topic", "event2");
			sendobject.put("startTime", "6:00PM"); 	
			sendobject.put("endTime", "9:00PM");
			sendobject.put("location", "Library");
			sendobject.put("content", "This event is to discuss the...");
			sendobject.put("userId", "rocky");
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

	
	public void  testGetEvent() {
		try{
			System.out.println("Get Event");
			url = url + "/5/event/2";
			Resource resource = client.resource(url);
			System.out.println("URL: "+url);
			String  aEvent = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
			System.out.println(aEvent);
			System.out.println();
			System.out.println();
		}
		catch(ClientWebException e){
			//				e.printStackTrace();
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}
	} 

	
	public void  testGetEventsByUserId() {
		try{
			System.out.println("Get Event");
			url = url + "/5/event/user/rocky";
			Resource resource = client.resource(url);
			System.out.println("URL: "+url);
			String  aEvent = (String)resource.accept(MediaType.APPLICATION_JSON).get(String.class);
			System.out.println(aEvent);
			System.out.println();
			System.out.println();
		}
		catch(ClientWebException e){
			//							e.printStackTrace();
			System.out.println("The Exception message is "+e.getResponse().getMessage());
		}
	} 
	/*
	public void   testDeleteEvent() {
		try{
			System.out.println("Remove an Event from a Group");
			url = url + "/8/event/166/vicky";
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
	
	public void   testUpdateEvent() {
		try{
			System.out.println("Update an Article blog owner");
			url = url + "/8/event/27/smarth";
			System.out.println("URL : "+url);
			Resource resource6 = client.resource(url);

			JSONObject sendobject = new JSONObject();
			try {

				sendobject.put("topic", "event3");
				sendobject.put("eventId", "2");
				sendobject.put("groupId", "5");

				sendobject.put("startTime", "8:00PM"); 	
				sendobject.put("endTime", "9:00PM");
				sendobject.put("location", "LOFT");
				sendobject.put("content", "dance dance dance");
				sendobject.put("userId", "smarth");
				sendobject.put("timestamp", "12:11");

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


}