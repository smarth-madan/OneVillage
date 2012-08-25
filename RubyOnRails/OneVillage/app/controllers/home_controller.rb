require 'rest-client'
require 'test_controller'

class HomeController < ApplicationController
  #
  # => Blog starts here
  #

   def photo
		puts "Cookies = "+cookies["_auth"]
		if (cookies["_auth"] == nil or cookies["_auth"] == "")
			redirect_to root_url , :notice => "Please sign in first..."
		end
   end
   
   def group
		puts "Cookies = "+cookies["_auth"]
		if (cookies["_auth"] == nil or cookies["_auth"] == "")
			redirect_to root_url , :notice => "Please sign in first..."
		end
   end
   
   def blog
		puts "Cookies = "+cookies["_auth"]
		if (cookies["_auth"] == nil or cookies["_auth"] == "")
			redirect_to root_url , :notice => "Please sign in first..."
		end
   end
  
  def createblog
	redirect_to("/blog/new")
  end

  def deleteblog
  	redirect_to("/blog/deleteBlog")
  end
  
  def getblog
  	redirect_to("/blog/getAllBlogs")
  end	

  def getallblogs
  	redirect_to("/blog/getAllBlogs")
  end	
  
  def createblogentry
  	redirect_to("/blog/newEntry")
  end	
  
  def getblogentries
  	redirect_to("/blog/getBlogEntries")
  end	
  
  def deleteblogentry
  	redirect_to("/blog/getBlogEntries")
  end	
  
  #
  # => Group starts here
  #
  
  def createevent
  	redirect_to("/group/newEvent")
  end
  	
  def creategroup
  	redirect_to("/group/newGroup")
  end
  
  def deleteevent
  	redirect_to("/group/deleteEvent")
  end
  
  def getallevents
  	redirect_to("/group/getAllEvents")
  end
  
  def getallgroups
  	redirect_to("/group/getAllGroups")
  end
  
  def getalluserevents
  	redirect_to("/group/getAllUserEvents")
  end
  
  def getevent
  	redirect_to("/group/getAllEvents")
  end

  def getgroup
  	redirect_to("/group/getAllGroups")
  end
  
  def joinevent
  	redirect_to("/group/user")
  end
  
  def showmembers
  	redirect_to("/group/showMembers")
  end

  
 #
  # => Photo starts here
  #
  
  
  def createalbum
  	redirect_to("/photo/createAlbumPage")
  end	

  def updatealbum
  	redirect_to("/photo/updateAlbumPage")
  end
  def deletealbum
	redirect_to("/photo/deleteAlbumPage")
  end
  def getAllAlbums
	redirect_to("/photo/getAllAlbums")
  end
  def getAllAlbumsByUser
  	redirect_to("/photo/getAllAlbumsByUserPage")
  end
  def getallphotosfromalbum
	redirect_to("/photo/getAllPhotosFromAlbumPage")
  end
  def getphotofromalbum
  	redirect_to("/photo/getPhotoFromAlbumPage")
  end	  
  def addphoto
	redirect_to("/photo/addPhotoPage")
  end
  
  def updatephoto
  	redirect_to("/photo/updatePhotoPage")
  end	

  def deletephoto
  	redirect_to("/photo/deletePhotoPage")
  end	
  def associatephototoarticle
	redirect_to("/photo/associatePhotoToArticlePage")
  end
  
  def associatephototocommunity
  	redirect_to("/photo/associatePhotoToCommunityPage")
  end	

  def getphotobycommunity
  	redirect_to("/photo/getPhotoByCommunityPage")
  end	
  def getphotobyarticle
	redirect_to("/photo/getPhotoByArticlePage")
  end
  

end
