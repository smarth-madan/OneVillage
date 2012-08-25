package org.oneVillage.photo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.wink.common.model.multipart.InMultiPart;
import org.oneVillage.photo.data.Album;
import org.oneVillage.photo.data.Photo;

@Path("/photo")
public interface PhotoService {
	
	
	//1. for creating Album
	@Path("/album")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createAlbum(Album album);
	
	//2. for updating Album
	@Path("/album/{albumId}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updateAlbum(Album album,@PathParam("albumId") String albumId);
	
	//3. deleting an album
	@Path("/album/album_id={albumId}/user_id={userid}")
	@DELETE
	public String deleteAlbum(@PathParam("albumId") String albumId, @PathParam("userid") String userId);
		
	//4. for getting all albums
	@Path("/album")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllAlbums();
	
	//5. for getting Albums for a particular user
	@Path("/album/user_id={userid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAlbumsByUser(@PathParam("userid") String userid);
	
	//6. for adding a photo
	@POST
	@Path("/album/{albumId}/{fileName}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public String addPhoto(@PathParam("albumId") String albumId,@PathParam("fileName") String fileName,InMultiPart inMultiPart);
	
	//7. for getting album
	@Path("/album/{albumId}/user_id={userid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllPhotosFromAlbum(@PathParam("albumId") String albumId,@PathParam("userid") String userid);
		
	
	//8. for updating Photo
	@Path("/album/{albumId}/photo")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public String updatePhoto(Photo photo,@PathParam("albumId") String albumId,@QueryParam("userId") String userId);
	
	
	//9. deleting a photo
	@Path("/album/{albumId}")
	@DELETE
	public Response deletePhoto(@PathParam("albumId") String albumId, @QueryParam("userId") String userId, @QueryParam("photoId") String photoId);
	
	
	//10. For Tagging a photo to an article
	@Path("/album/{albumId}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String associatePhotoToArticle(@PathParam("albumId") String albumId,String request);
	
	//11. For Tagging a photo to a Group
	@Path("/album/{albumId}/community")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String associatePhotoToCommunity(@PathParam("albumId") String albumId,String request);
	
	//12. For getting photo to a Group from Album
	/*@Path("/album/{albumId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPhotoFromAlbum(@PathParam("albumId") String albumId,@QueryParam("photoId") String photoId);*/
	
	//13. For getting photo by community
	@Path("/album/communityId={communityId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPhotoByCommunity(@PathParam("communityId") String communityId);
	
	//14. For getting photo by Article
	@Path("/album/articleId={articleId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getPhotoByArticle(@QueryParam("articleId") String articleId);
	
	
}
