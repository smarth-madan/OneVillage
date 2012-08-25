require 'rest-client'
require 'date'
require 'net/http'


class PhotoController < ApplicationController
	
	respond_to :json
	
  def createAlbum
  		
	@url = "http://localhost:8087/oneVillagePhoto/photo/album"
  	@name = (params[:name])
    @userId = cookies["_auth"]
    @description = (params[:description])
    @createdDate = Time.now.to_s
    
  	@payload = {"createdDate" => @createdDate,
				"name" => @name,
				"userid"=> @userId,
				"description" => @description}.to_json

    response = RestClient.post(@url, @payload, {"Content-Type" => 'application/json',
												"Accept" => 'application/json'})
	
	
	redirect_to root_url , :notice => "Album successfully created, What do you want to do next?"
	
   end

  def updateAlbum
  
	@name = (params[:name])
    @userId = (params[:userId])
    @description = (params[:description])
	@albumId = (params[:albumId])

  	@url = "http://localhost:8087/oneVillagePhoto/photo/album/" + @albumId
  	@payload = {"albumid" => @albumId,
				"name" => @name,
				"userid"=> @userId,
				"description" => @description}.to_json
				
	response = RestClient.put(@url, @payload, {"Content-Type" => 'application/json',
												"Accept" => 'application/json'})
  	
	if response == "Album Not found" then
  	redirect_to root_url , :notice => response + " Could not update the Album :("
	end
	if  response == "Bad Request" then
	redirect_to root_url , :notice => "Your rquest was not sent/recieved properly. " + response
	end
	if response == "Album Updtated Successfully" then
	redirect_to root_url , :notice => "Your album with Album Id: "+ @albumId +" has been updated. What do you wanna do next??"
	end
	
   end
   
    def deleteAlbum
	@albumId = params[:albumId]
	@userid = params[:userId]
  	@url = "http://localhost:8087/oneVillagePhoto/photo/album/album_id=" + @albumId +"/user_id=" + @userid
  	
  	response = RestClient.delete(@url, :accept => :json, :content_type=>:json)
  	
	if response == "Album Not found" then
  	redirect_to root_url , :notice => response + " Could not delete the Album :("
	end
	if  response == "Bad Request" then
	redirect_to root_url , :notice => "Your rquest was not sent/recieved properly. " + response
	end
	if response == "Album Deleted Successfully" then
	redirect_to root_url , :notice => "Your album with Album Id:"+ @albumId +" has been deleted. What do you wanna do next??"
	end
	
  end

   def getAllAlbums
  	
  	@url = "http://localhost:8087/oneVillagePhoto/photo/album"
  	@response = RestClient.get(@url, :accept => :json)  
  	puts "\n\n---> list for response\n#{response}\n\n"
  	if @response!= "" then
		@a= JSON.parse(@response, "object_class" => true)
		@response=@response[1..-1]
		logger.debug "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Response:  #{@response}"
		@array = @a
		#@b = @a.split(',');
		@a.each do |b|
			logger.debug "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ b: #{b["albumid"]}"
		end
	else
		redirect_to root_url , :notice => "No Albums created yet"
	end
  	
  end

 def getAllAlbumsByUser
 
	@userid = params[:userId]
	@url = "http://localhost:8087/oneVillagePhoto/photo/album/user_id="
	if @userid!=nil then
		@url = @url + @userid
	
		@response = RestClient.get(@url, :accept => :json)  
		puts "\n\n---> list for response\n#{response}\n\n"
		if @response!= "" then
			@a= JSON.parse(@response, "object_class" => true)
			@response=@response[1..-1]
			logger.debug "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Response:  #{@response}"
			@array = @a
			#@b = @a.split(',');
			@a.each do |b|
				logger.debug "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ b: #{b["albumid"]}"
			end
		else
			redirect_to root_url , :notice => "No Albums created by User Id: "+ @userid
		end
		
	else
		redirect_to root_url , :notice => "Please provide a correct user Id!!!"
	end 
	
 end

  def addPhoto
	@url = "http://localhost:8087/oneVillagePhoto/photo/album/"
  	@albumId = params[:albumId]
    @file = params[:file]
	@userId = params[:userId]
    @latitude = params[:latitude]
	@longitude = params[:longitude]
    @decription = params[:decription]
	@createdDate = params[:createdDate]
	@fileName = @file.to_s
	@filePath =  params[:filePath]
	@url = @url +@albumId + "/" + @fileName
	
	if @file!=nil then
		
		########################################## uploading file ############################
		
		boundary = 'simple boundary'

		parts = []
		streams = []
		
		#parts << StringPart.new("--" + boundary);
		#parts << StringPart.new("Content-Disposition: form-data; name=\""+@fileName+"\";filename=\""+@fileName)
		#parts << StringPart.new("Content-Transfer-Encoding=\""+"binary\"")
		#parts << StringPart.new("Content-Type=application/octet-stream; name=\""+@fileName+"\"")
		#parts << StringPart.new("description=\""+@decription+"\"")
		#parts << StringPart.new("latitude=\""+@latitude+"\"")
		#parts << StringPart.new("longitude=\""+@longitude+"\"")
		#parts << StringPart.new("timeTaken=\""+@createdDate+"\"")
		#parts << StringPart.new("userId=\""+@userId+"\"")
		
		parts << StringPart.new("--" + boundary + "\r\n"+
		"Content-Disposition: form-data; Content-Transfer-Encoding=binary,Content-Type=application/octet-stream, name=\""+@fileName+"\";filename=\""+@fileName+"\"\r\n"+
		"Content-Type: application/octet-stream \r\n"+
		"description:"+@decription+"\r\n"+
		"latitude:"+@latitude+ "\r\n"+
		"longitude:"+@longitude+"\r\n"+
		"createdDate:"+@createdDate+"\r\n"+
		"userId:"+@userId+"\r\n\r\n")
				  				  	 
		stream = File.open(@filePath, "rb") 
		streams << stream
		parts << StreamPart.new(stream, File.size(@filePath)) 
		parts << StringPart.new("\r\n--" + boundary + "--\r\n")  
		
		post_stream = MultipartStream.new( parts)

		url = URI.parse( @url )
		req = Net::HTTP::Post.new(url.path)
		
		#req.add_field 'Content-Transfer-Encoding','binary'
		#req.add_field 'Content-Transfer-Encoding','binary'
		#req.add_field 'description',@decription
		#req.add_field 'latitude',@latitude
		#req.add_field 'longitude',@longitude
		#req.add_field 'timeTaken',@createdDate
		#req.add_field 'userId',@userId
		
		req.content_length = post_stream.size
		req.content_type = 'multipart/form-data; boundary=' + boundary
		req.body_stream = post_stream
		res = Net::HTTP.new(url.host, url.port).start {|http| http.request(req) }

		streams.each do |stream|
		  stream.close();
		end
		debugger
		###################################################################################
		
	end	
    if @file==nil then
		redirect_to root_url , :notice => "Please select a File"
	end
		
		redirect_to root_url , :notice =>"Photo Uploaded Successfully!!!"
		
	end
	
	def updatePhoto
  
	@name = (params[:name])
    @userId = (params[:userId])
	@latitude = params[:latitude]
	@longitude = params[:longitude]
	@createdDate = params[:createdDate]
    @description = (params[:description])
	@albumId = (params[:albumId])
	@photoId = (params[:photoId])

  	@url = "http://localhost:8087/oneVillagePhoto/photo/album/"+ @albumId +"/photo?userId=" + @userId
  	@payload = {"photoid" => @photoId,
				"latitude" => @latitude,
				"longitude" => @longitude,
				"userid"=> @userId,
				"description" => @description,
				"createdDate" => @createdDate}.to_json
				
	response = RestClient.put(@url, @payload, {"Content-Type" => 'application/json',
												"Accept" => 'application/json'})
  	
	if response == "Photo Not found" then
  	redirect_to root_url , :notice => response + " Could not update the Album :("
	end
	if  response == "Bad Request: Needs UserId, PhotoId and albumId" then
	redirect_to root_url , :notice => "Your rquest was not sent/recieved properly. " + response
	end
	if response == "Photo Updtated Successfully" then
	redirect_to root_url , :notice => "Your Photo with Photo Id:"+ @photoId +" has been updated. What do you wanna do next??"
	end
	
   end

   
   def deletePhoto
	@albumId = params[:albumId]
	@userid = params[:userId]
	@photoId = params[:photoId]
  	@url = "http://localhost:8087/oneVillagePhoto/photo/album/"+ @albumId +"?userId="+ @userid +"&photoId=" + @photoId
  	
  	response = RestClient.delete(@url, :accept => :json, :content_type=>:json)
  	
	if response == "Photo Not found. Incorrect combination of PhotoId, albumId and userId" then
  	redirect_to root_url , :notice => response + " Could not delete the Photo :("
	end
	if  response == "Bad Request: Needs UserId, PhotoId and albumId to delete" then
	redirect_to root_url , :notice => "Your rquest was not sent/recieved properly. " + response
	end
	if response == "Photo Deleted Successfully" then
	redirect_to root_url , :notice => "Your Photo with Photo Id:"+ @photoId +" has been deleted." + response + " What do you wanna do next??"
	end
	
  end
   
   def associatePhotoToArticle
  		
	
  	@articleId = (params[:articleId])
    @userId = (params[:userId])
    @photoId = (params[:photoId])
	@albumId = (params[:albumId])

   
    @url = "http://localhost:8087/oneVillagePhoto/photo/album/" + @albumId
  	@payload = {"articleId" => @articleId,
				"photoId" => @photoId,
				"articleTagger"=> @userId}.to_json

    @response = RestClient.post(@url, @payload, {"Content-Type" => 'application/json',
												"Accept" => 'application/json'})
	
	
	redirect_to root_url , :notice => @response
	
   end

    def associatePhotoToCommunity 
  		
	
  	@communityId = (params[:communityId])
    @userId = (params[:userId])
    @photoId = (params[:photoId])
	@albumId = (params[:albumId])

   
    @url = "http://localhost:8087/oneVillagePhoto/photo/album/" + @albumId +"/community"
  	@payload = {"communityId" => @communityId,
				"photoId" => @photoId,
				"communityTagger"=> @userId}.to_json

    response = RestClient.post(@url, @payload, {"Content-Type" => 'application/json',
												"Accept" => 'application/json'})
	
	
	if response == "Photo Not found" then
  	redirect_to root_url , :notice => response + " Could not tag Photo :("
	end
	if  response == "Bad Request: Needs communityId, PhotoId, albumId and tagger" then
	redirect_to root_url , :notice => "Your rquest was not sent/recieved properly. " + response
	end
	if response == "Photo associated to community " then
	redirect_to root_url , :notice => "Your Photo with Photo Id:"+ @photoId +" has been associated to community id." + @communityId
	end
	
   end
   
   def getPhotoByCommunity
  	
	@communityId = (params[:communityId])
  	@url = "http://localhost:8087/oneVillagePhoto/photo/album/communityId=" + @communityId
  	@response = RestClient.get(@url, :accept => :json)  
  	puts "\n\n---> list for response\n#{response}\n\n"
  	if @response!= "" then
		@a= JSON.parse(@response, "object_class" => true)
		@response=@response[1..-1]
		logger.debug "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Response:  #{@response}"
		@array = @a
		#@b = @a.split(',');
		@a.each do |b|
			logger.debug "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ b: #{b["albumid"]}"
		end
  	else
		redirect_to root_url , :notice => "No Photos associated with the Community Id: "+ @communityId
	end
  	
   end
   
   def getPhotoByArticle
  	
	@articleId = (params[:articleId])
  	@url = "http://localhost:8087/oneVillagePhoto/photo/album/articleId=" + @articleId
  	@response = RestClient.get(@url, :accept => :json)  
  	puts "\n\n---> list for response\n#{response}\n\n"
	if @response != "" then
		@a= JSON.parse(@response, "object_class" => true)
		@response=@response[1..-1]
		logger.debug "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Response:  #{@response}"
		@array = @a
		#@b = @a.split(',');
		@a.each do |b|
			logger.debug "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ b: #{b["albumid"]}"
		end
  	else
		redirect_to root_url , :notice => "No Photos associated with the Article Id: "+ @articleId
	end
  end
   
    def getAllPhotosFromAlbum
  	
	@albumId = (params[:albumId])
  	@url = "http://localhost:8087/oneVillagePhoto/photo/album/" + @albumId+"/user_id="+(params[:userId])
  	@response = RestClient.get(@url, :accept => :json)  
  	puts "\n\n---> list for response\n#{response}\n\n"
	if @response != "" then
		@a= JSON.parse(@response, "object_class" => true)
		@response=@response[1..-1]
		logger.debug "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Response:  #{@response}"
		@array = @a
		#@b = @a.split(',');
		@a.each do |b|
			logger.debug "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ b: #{b["albumid"]}"
		end
	else
		redirect_to root_url , :notice => "No Photos present in Album Id: "+ @albumId
	end
  end
 end

##################################### ADD PHOTO CODE ############################################

class StreamPart
  def initialize( stream, size )
    @stream, @size = stream, size
  end

  def size
    @size
  end

  def read ( offset, how_much )
    @stream.read ( how_much )
  end
end

class StringPart
  def initialize ( str )
    @str = str
  end

  def size
    @str.length
  end

  def read ( offset, how_much )
    @str[offset, how_much]
  end
end

class MultipartStream
  def initialize( parts )
    @parts = parts
    @part_no = 0;
    @part_offset = 0;
  end

  def size
    total = 0
    @parts.each do |part|
      total += part.size
    end
    total
  end

  def read ( how_much )

    if @part_no >= @parts.size
      return nil;
    end

    how_much_current_part = @parts[@part_no].size - @part_offset

    how_much_current_part = if how_much_current_part > how_much
      how_much
    else
      how_much_current_part
    end

    how_much_next_part = how_much - how_much_current_part

    current_part = @parts[@part_no].read(@part_offset, how_much_current_part )

    if how_much_next_part > 0
      @part_no += 1
      @part_offset = 0
      next_part = read ( how_much_next_part  )
      current_part + if next_part
        next_part
      else
        ''
      end
    else
      @part_offset += how_much_current_part
      current_part
    end
  end
end

################################################################################################# 