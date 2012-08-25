package org.oneVillage.group.resource;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.oneVillage.group.data.Group;
import org.oneVillage.group.data.Event;

/**
 * @author mycomputer
 *
 */

@Path("/group")
public interface GroupService {
	
	
	//for creating Group
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createGroup(Group group);
	
	//for getting a particular group
	@Path("/{groupid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getGroup(@PathParam("groupid") String id);
	
	
	//for getting all groups
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllGroups();
	
	//deleting
	@Path("/{groupid}/{userid}")
	@DELETE
	public Response deleteGroup(@PathParam("groupid") String groupId, @PathParam("userid") String userId);

	
	//for creating an Event
		@POST
		@Path("/{groupId}/event")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response createEvent(@PathParam("groupId") String groupId, Event event);
	
	//for getting all events of a group
		@GET
		@Path("/{groupId}/event")
		@Produces(MediaType.APPLICATION_JSON)
		public String getEventsByGroupId(@PathParam("groupId") String groupId);
		
	//for getting a particular Event
		@GET
		@Path("/{groupId}/event/{eventId}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getEvent(@PathParam("groupId") String groupId,@PathParam("eventId") String eventId);
		
	//for getting a all Events from a group for a user
		@GET
		@Path("/{groupId}/event/user/{userId}")
		@Produces(MediaType.APPLICATION_JSON)
		public String getEventsByUserId(@PathParam("groupId") String groupId,@PathParam("userId") String userId);
				
	//delete event
		@Path("/{groupId}/event/{eventId}/{userId}")
		@DELETE
		public Response deleteEvent(@PathParam("groupId") String groupId, @PathParam("eventId") String eventId, @PathParam("userId") String userId);
	
	//update event
		@PUT
		@Path("/{groupId}/event/{eventId}/{userId}")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response updateEvent(@PathParam("groupId") String groupId, @PathParam("eventId") String eventId, @PathParam("userId") String userId, Event event);

	//join group
		@Path("/groupmember/{groupid}")
		@POST
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response joinGroup(@PathParam("groupid") String groupId, String userId);
		
	//unjoin group		
		@Path("/groupmember/{groupid}/{userid}")
		@DELETE
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public Response unjoinGroup(@PathParam("groupid") String groupId,@PathParam("userid") String userId);
	
	//get all members of a group
		@Path("/groupmember/{groupid}")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public String getGroupMembers(@PathParam("groupid") String groupId);
}
