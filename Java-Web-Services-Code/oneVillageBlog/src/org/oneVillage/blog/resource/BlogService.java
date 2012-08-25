package org.oneVillage.blog.resource;

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






import org.oneVillage.blog.data.Blog;
import org.oneVillage.blog.data.Article;

/**
 * @author zilof
 *
 */

@Path("/blog")
public interface BlogService {
	
	
	//for creating Blog
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBlog(Blog blog);
	
	
	
	//for getting blog
	@Path("/{blogId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Blog getBlog(@PathParam("blogId") String id);
	
	
	//for getting all blogs
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAllBlogs();
	
	//deleting
	@Path("/{blogid}/{userid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBlog(@PathParam("blogid") String blogId, @PathParam("userid") String userId);

	
	//for creating Blog Entry
	@POST
	@Path("/{blogid}/article")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createArticle(@PathParam("blogid") String blogId, Article article);
	
	//for getting all blog entries
	@GET
	@Path("/{blogid}/article")
	@Produces(MediaType.APPLICATION_JSON)
	public String getArticles(@PathParam("blogid") String blogId);
	
	//for getting all blog entries
	@GET
	@Path("/{blogid}/article/{articleid}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getArticle(@PathParam("blogid") String blogId,@PathParam("articleid") String articleId);
	
	//delete entry
	@Path("/{blogid}/article/{articleid}/{userid}")
	@DELETE
	public Response deleteArticle(@PathParam("blogid") String blogId, @PathParam("articleid") String articleId, @PathParam("userid") String userId);
	
	@PUT
	@Path("/{blogid}/article/{articleid}/{userid}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateArticle(@PathParam("blogid") String blogId, @PathParam("articleid") String articleId, @PathParam("userid") String userId, Article article);
	
}
