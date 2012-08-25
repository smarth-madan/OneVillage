package org.oneVillage.group.resource;



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


import org.oneVillage.group.data.Group;
import org.oneVillage.group.data.Event;
import org.oneVillage.group.data.GroupUser;
import org.oneVillage.group.db.MongoEventDB;
import org.oneVillage.group.db.MongoGroupDB;
import org.oneVillage.group.db.MongoGroupUserDB;

public class GroupServiceImpl implements GroupService {
	
	
	private Properties conf;
	private Properties conf1;
	private Properties confGUser;
	RestClient client;
	public GroupServiceImpl() {
		conf = new Properties();
		conf.setProperty(MongoGroupDB.sHost, "localhost");
    	conf.setProperty(MongoGroupDB.sDB, "oneVillageDb");
    	conf.setProperty(MongoGroupDB.sCollection, "groups");
    	
    	conf1 = new Properties();
		conf1.setProperty(MongoGroupDB.sHost, "localhost");
    	conf1.setProperty(MongoGroupDB.sDB, "oneVillageDb");
    	conf1.setProperty(MongoGroupDB.sCollection, "event");
    	

    	confGUser = new Properties();
    	confGUser.setProperty(MongoGroupDB.sHost, "localhost");
    	confGUser.setProperty(MongoGroupDB.sDB, "oneVillageDb");
    	confGUser.setProperty(MongoGroupDB.sCollection, "groupUser");
    	
		 client = new RestClient();
    	
	}
	
	
	
	
	
	//*************** CREATE GROUP *****************//
	@Override
	public Response createGroup(Group group) {
		System.out.println("The request is received to create group...");
		MongoGroupDB mc = new MongoGroupDB(conf);
		String ret = mc.addGroup(group);
			
		if(ret.equals("false"))
			return Response.status(400).entity("Error Occurred!!!").build();	
		else{
			MongoGroupUserDB mcg = new MongoGroupUserDB(confGUser);
			int rtn = mcg.joinGroup(ret,group.getUserId());
			if(rtn != 404 && rtn != 500)
				return Response.status(200).entity("Group Created!!!").build();
			else
			{
				String rtn2=mc.deleteGroup(ret,group.getUserId());
				return Response.status(400).entity("Error Occurred in joining the group by user!!!").build();	
			}
		}
			
	}

	
	//*************** DELETE GROUP *****************//
	@Override
	public Response deleteGroup(@PathParam("groupid") String groupId, @PathParam("userid") String userId) {
		System.out.println("The request is received to delete group...");
				
		MongoGroupDB mc = new MongoGroupDB(conf);
		
		String rtn=mc.deleteGroup(groupId,userId);	
		
		System.out.println(rtn.substring(0, 7));

		if(rtn.substring(0, 7).equals("groupId")){
			MongoEventDB mc2 = new MongoEventDB(conf1);
			mc2.delAllEvents(groupId);
			MongoGroupUserDB mcg = new MongoGroupUserDB(confGUser);
			mcg.delAllUsers(groupId);
			return Response.status(200).entity("Group Deleted!!! \n" + rtn).build();
		}
		if(rtn.substring(0, 7).equals("NotFoun"))
			return Response.status(200).entity("GroupId-UserId combination not found").build();
		else
			return Response.status(200).entity("Error Occured!!!").build();
	}
	
	//*************** GET A PARTICULAR GROUP *****************//
	@Override
	public String  getGroup(@PathParam("groupId") String id) {
		
			System.out.println("cntl in our get group with id "+id);
			MongoGroupDB mc = new MongoGroupDB(conf);
			Group b=mc.getGroup(id);
			if(b!=null){
				Type typeOfSrc = new TypeToken<Group>(){}.getType();
				Gson gson=new Gson();
				String Data=gson.toJson(b, typeOfSrc);
				return Data;
			}
			else
				return null;
	}

 


	//*************** GET ALL GROUPS *****************//
	@Override
	public String getAllGroups() {
		
		MongoGroupDB mc = new MongoGroupDB(conf);
		System.out.println("calling getAllGroup");
		ArrayList<Group> list=mc.getAllGroups();
		if(list.isEmpty()){
			return "There are no groups. List is empty";
		}
		Type typeOfSrc = new TypeToken<List<Group>>(){}.getType();
		Gson gson=new Gson();
		String firstData=gson.toJson(list, typeOfSrc);
		System.out.println("Data:"+firstData+"\n");
		return firstData;
		
	}


	//*************** CREATE EVENT *****************//
		  @Override
		public Response createEvent(@PathParam("groupId") String groupId, Event event) throws WebApplicationException {
				System.out.println("The request is received...");
				System.out.println("In GroupServiceImpl....");
				MongoGroupUserDB mcg = new MongoGroupUserDB(confGUser);
				int isMember = mcg.isMember(groupId, event.getUserId());
				if(isMember == 0)
					return Response.status(200).entity("GroupId does not exist or user is not a member of this group").build();
				event.setGroupId(groupId);
				System.out.println("Just set the group Id...");
				MongoEventDB mc1 = new MongoEventDB(conf1);
				System.out.println("Going to Mongo addEvent()...");
				String ret = mc1.addEvent(event);

				if(ret=="true")
					return Response.status(200).entity("Event Created!!!").build();
				if(ret=="false")
					return Response.status(200).entity("GroupId does not exist!!!").build();
				else
					return Response.status(200).entity("Error Occured!!!").build();
			}

//*************** GET ALL EVENTS *****************//
		  @Override
			public String getEventsByGroupId(@PathParam("groupId") String groupId) {

				MongoEventDB mc = new MongoEventDB(conf1);
				System.out.println("I am here in  GroupServiceImpl");
				System.out.println("array list: "+mc.getEvents(groupId).toString());
				System.out.println("calling getAll Events");
				ArrayList<Event> list = mc.getEvents(groupId);
				if(list.isEmpty()){
					return "There are no events created in this group with groupId: "+ groupId;
				}
				Type typeOfSrc = new TypeToken<List<Event>>(){}.getType();
				Gson gson=new Gson();
				String firstData=gson.toJson(list, typeOfSrc);

				return firstData;
		  }
//************************************************//
		

//*************** GET AN EVENT BY ID ************//
		    @Override
	public String getEvent(@PathParam("groupId") String groupId,@PathParam("eventId") String eventId) {

				MongoEventDB mc = new MongoEventDB(conf1);
				System.out.println("array list: "+mc.getEvents(groupId).toString());
				ArrayList<Event> list = mc.getEvent(groupId,eventId);
				System.out.println("calling getAll Events");
				if(list.isEmpty())
					return "No Record Found !!!";
				Type typeOfSrc = new TypeToken<List<Event>>(){}.getType();
				Gson gson=new Gson();
				String firstData=gson.toJson(list, typeOfSrc);

				return firstData;
			}		  
//***********************************************//
		    
//*************** GET ALL EVENTS FOR A USER IN A GROUP ************//
		    @Override
	public String getEventsByUserId(@PathParam("groupId") String groupId,@PathParam("userId") String userId) {

				MongoEventDB mc = new MongoEventDB(conf1);
				System.out.println("////////////////groupId is: "+groupId+"//////////userId is: "+ userId);
				System.out.println("array list: "+mc.getEvents(groupId).toString());
				ArrayList<Event> list = mc.getEventsByUserId(groupId,userId);
				System.out.println("calling getEventsByUserId");
				if(list.isEmpty())
					return "No Record Found !!!";
				Type typeOfSrc = new TypeToken<List<Event>>(){}.getType();
				Gson gson=new Gson();
				String firstData=gson.toJson(list, typeOfSrc);

				return firstData;
			}		  
//***********************************************//		    
		    

		  
//**************** DELETE AN EVENT ***************//

			@Override
			public Response deleteEvent(@PathParam("groupId") String groupId,
					@PathParam("eventid") String eventId, @PathParam("userid") String userId) {
				System.out.println("The request is received...");
				
				MongoEventDB mc1 = new MongoEventDB(conf1);
				String rtn = mc1.delEvent(groupId, eventId, userId);

				System.out.println(rtn.substring(0, 7));
				if(rtn.substring(0, 7).equals("groupId"))
					return Response.status(200).entity("Event Deleted!!! \n" + rtn).build();
				if(rtn.substring(0, 7).equals("NotFoun"))
					return Response.status(200).entity("Event not found").build();
				else
					return Response.status(200).entity("Error Occured!!!").build();
			}
//************************************************//	

//*************UPDATE AN EVENT********************//
			public Response updateEvent(String groupId, String eventId, String userId, Event event){
				System.out.println("The request is received...");
				MongoEventDB mc1 = new MongoEventDB(conf1);
				String rtn = mc1.updateEvent(groupId, eventId, userId, event);
				System.out.println("return from db:" + rtn);
				if(rtn.equals("true"))
					return Response.status(200).entity("Event Updated Successfully!!! \n" + rtn).build();
				if(rtn.equals("NotFound"))
					return Response.status(200).entity("Event not found!!! \n" + rtn).build();
				else
					return Response.status(200).entity("Error Occured!!!").build();
			}

//************************************************//

//*************JOIN GROUP*************************//
			@SuppressWarnings("unused")
			@Override
			public Response joinGroup(@PathParam("groupId") String groupId, String userId) {
				System.out.println("The request has reached here");
				System.out.println("groupId = "+groupId+", userId = "+userId);
				try {
					String u = new JSONObject(userId).getString("userId");
					System.out.println(u);

					MongoGroupDB mc = new MongoGroupDB(conf);
					Group b=mc.getGroup(groupId);
					if (b == null)
					{
						return Response.status(404).entity("Group Not Found!!!").build();
					}
					
					MongoGroupUserDB mcg = new MongoGroupUserDB(confGUser);
					int rtn = mcg.joinGroup(groupId,u);
					if (rtn == 404)
						return Response.status(200).entity("User already joined!!!").build();
					else if (rtn == 500)
						return Response.status(400).entity("Exception occurred!!!").build();
					else
						return Response.status(200).entity("User has joined the group Successfully").build();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return Response.status(500).entity("Exception Occured!!!").build();
				}
			}
//************************************************//
//*****************UNJOIN A GROUP*****************//			
			@Override
			public Response unjoinGroup(@PathParam("groupid") String groupId, @PathParam("userid") String userId){
				System.out.println("The request has reached here");
				System.out.println("groupId = "+groupId+", userId = "+userId);
				
				MongoGroupDB mc = new MongoGroupDB(conf);
				Group b=mc.getGroup(groupId);
				if (b == null)
				{
					return Response.status(404).entity("Group Not Found!!!").build();
				}
				else if(b.getUserId().equals(userId))
					return Response.status(400).entity("Cannot unjoin the owner of group!!!. The owner can delete the group though.").build();
				
				MongoGroupUserDB mcg = new MongoGroupUserDB(confGUser);
				int rtn = mcg.unJoinGroup(groupId,userId);
				
				if (rtn == 404)
					return Response.status(200).entity("User is not a member of this group!!!").build();
				else if (rtn == 500)
					return Response.status(200).entity("Exception occurred!!!").build();
				else
					return Response.status(200).entity("User has unsubscribed successfully").build();
			}
			
//************GET MEMBERS OF A GROUP ***************//
			@Override
			public String getGroupMembers(@PathParam("groupid") String groupId){
				System.out.println("The request has reached here");
				System.out.println("groupId = "+groupId);
				
				MongoGroupDB mc = new MongoGroupDB(conf);
				Group b=mc.getGroup(groupId);
				//System.out.println("Group id in serv impl is: " + b.getGroupId());
				if (b==null)
				{
					//return Response.status(404).entity("Group Not Found!!!").toString();
					return "Group Not Found!!!";
				}
				
				MongoGroupUserDB mcg = new MongoGroupUserDB(confGUser);
				ArrayList<GroupUser> list = mcg.getMembers(groupId);
				Type typeOfSrc = new TypeToken<List<GroupUser>>(){}.getType();
				Gson gson=new Gson();
				String Data=gson.toJson(list, typeOfSrc);
				return Data;
			}
//**************************************************//

}
