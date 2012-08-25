require 'rest-client'
require 'date'
require 'json'

class GroupController < ApplicationController
	
	respond_to :json
	
  def getAllGroups
  	
  	@url = "localhost:8087/oneVillageGroup/group/"
  	@response = RestClient.get(@url, :accept => :json)  
  	puts "\n\n---> list for response\n#{response}\n\n"

	  	@array= JSON.parse(@response, "object_class" => true)
	  	
  end
  
  def getGroup
  
    	@url = "localhost:8087/oneVillageGroup/group/"
    	@groupId = params[:id]
    	
    	puts "\n\n ----The group id is -----> #{@groupId}"
    	
    	@result = RestClient.get(@url+@groupId, :accept => :json,:content_type=>:json)
  	
    	puts"/n/n ------->  #{@result}"
		begin
			@array= JSON.parse(@result, "object_class" => true)
    	rescue JSON::ParserError => e
			redirect_to root_url , :notice => "Group not Found!!!"
		end	  
  				
  				 @name = @array["name"]
  				 @description = @array["description"]
  				 @userId = @array["userId"]
		
	end
	
	def createGroup
	  		
		@url = "localhost:8087/oneVillageGroup/group/"
	  	@name = (params[:name])
	    @userId = cookies["_auth"]
	    @description = (params[:description])
	    @time = Time.now.to_s
	    
	  	@payload = {"timestamp" => @time,
					"userId" => @userId,
					"name"=> @name,
					"description" => @description}.to_json
	
	    @response = RestClient.post(@url, @payload, {"Content-Type" => 'application/json', "Accept" => 'application/json'})
		
		
		redirect_to root_url , :notice => "Group successfully created, What do you want to do next?"
		
   end
   
   def deleteGroup
     	@url = "localhost:8087/oneVillageGroup/group/"
     	
     	@groupId = params[:groupId]
     	puts "\n\n--> group #{@groupId}\n\n"
   	
     	@userId = params[:userId]
   
     	@result = RestClient.delete(@url+"/"+@groupId+"/"+@userId, :accept => :json, :content_type=>:json)
     	puts "\n\n --> result #{@result}\n\n"
     	
     	redirect_to root_url , :notice => "Your selected (Group #{@groupId}) has been deleted. What do you wanna do next??"
     	
  end
  
 
  def getAllEvents
    	
    	@url = "localhost:8087/oneVillageGroup/group/"
    	@groupId = params[:groupId]
    	
    	
    	puts "\n\n---> group id : #{@groupId}"
    	puts "\n\n---> Here I am ........."
    	
    	@response = RestClient.get(@url+"/"+@groupId+"/"+"event", :accept => :json)  
    	puts "\n\n---> list for response for all EVENTS...\n#{@response}\n\n"
		begin
			@array= JSON.parse(@response, "object_class" => true)
		rescue JSON::ParserError => e
			redirect_to root_url , :notice => "No events are present for this groupId: "+@groupId 
		end
  end
  
    def getEvent
    
      	@url = "localhost:8087/oneVillageGroup/group/"
      	@groupId = params[:groupId]
      	@eventId = params[:eventId]
      	
      	puts "\n\n ----groupId #{@groupId}"
      	puts "\n\n ----eventId #{@eventId}"
      	puts "\n\n ----The group id is -----> #{@groupId}"
      	p
      	@result = RestClient.get(@url+"/"+@groupId+"/"+"event"+"/"+@eventId, :accept => :json,:content_type=>:json)
    	
      	puts"\n ------->  #{@result}"
      	@array= (JSON.parse(@result, "object_class" => true))[0]
      	
      		  

    				 @location = @array["location"]
    				 @startTime = @array["startTime"]
    				 @endTime = @array["endTime"]
    				 @topic = @array["topic"]
    				 @content = @array["content"]
    				 @timestamp = @array["timestamp"]
    				 @userId = @array["userId"]
  		
	end
	
	def deleteEvent
	  	
	  	@url = "localhost:8087/oneVillageGroup/group/"
      	@groupId = params[:groupId]
      	@eventId = params[:eventId]
	  	@userId = params[:userId]
	  	
	  	puts "\n\n---> group id : #{@groupId}\n\n---> eventId: #{@eventId}\n\n---> userId: #{@userId}"
	  	
	  	@response = RestClient.delete(@url+"/"+@groupId+"/"+"event"+"/"+@eventId+"/"+@userId, :accept => :json)  
	  	redirect_to root_url , :notice => "#{@response}. What do you wanna do next??"
	  	
	  end

  def updateEvent
  	
        @url = "localhost:8087/oneVillageGroup/group/"
  	
  	@content = params[:content]
      @userId = params[:userId]
      @time = Time.now.to_s
      @groupId = params[:groupId]
  	@eventId = params[:eventId]
  	 @startTime = params[:startTime]
	      @endTime = params[:endTime]
	      @topic = params[:topic]
	      @location = params[:location]
      @content = params[:content]
    	
  	@payload = {"timestamp" => @time,
  				"content" => @content,
  				"userId"=> @userId,
  				"eventId"=>@eventId,
  				"startTime" => @startTime,
				"endTime" => @endTime,
				"topic" => @topic,
				"content"=> @content,
				"location"=> @location,
  				"groupId"=>@groupId}.to_json
  	
    	puts "\n\n---> GroupId : #{@groupId}\n\n---> eventId: #{@eventId}\n\n---> userId: #{@userId}"
    	
    	@response = RestClient.put(@url+"/"+@groupId+"/"+"event/"+@eventId+"/"+@userId, @payload, {"Content-Type" => 'application/json',
  												"Accept" => 'application/json'})
    	redirect_to root_url , :notice => "Event #{@eventId} Updated Successfully. What do you wanna do next??"
    	
  end
  
  def createEvent
    	
      @url = "localhost:8087/oneVillageGroup/group/"
      @topic = params[:topic]
      @userId = params[:userId]
      @startTime = params[:startTime]
      @endTime = params[:endTime]
      @groupId = params[:groupId]
      @location = params[:location]
      @content = params[:content]
      @time = Time.now.to_s
          
  	
      puts "\n\n --> o.O : groupId #{@groupId}\n\n --> userId #{@userId}"
      puts "\n\n ----The group id is -----> #{@groupId}"
     
      
        
    	@payload = {"timestamp" => @time,
    			"startTime" => @startTime,
    			"endTime" => @endTime,
  			"topic" => @topic,
  			"userId"=> @userId,
  			"content"=> @content,
  			"location"=> @location,
  			"content"=> @content,
  			"groupId" => @groupId}.to_json
  
      
	  @response = RestClient.post(@url+"/"+@groupId+"/"+"event", @payload, {"Content-Type" => 'application/json',
  												"Accept" => 'application/json'})
  	
  	redirect_to root_url , :notice => @response+", What do you want to do next?"
  	
  end
  
    def joinGroup
      @url = "localhost:8087/oneVillageGroup/group/"
    	@groupId = params[:groupId]
    	@userId = params[:userId]
    	
    	puts"\n\n join group in group....groupId--->#{@groupId}\n\n user_id--->#{@userId}"
    	
    	@payload = {"userId" => @userId}.to_json
    	
    	@response = RestClient.post(@url+"/groupmember/"+@groupId, @payload ,:accept =>:json , :content_type=>:json)
    	if (@response == "User already joined!!!")
			redirect_to root_url , :notice => "#{@response}. What do you want to do next?"
		else
			redirect_to root_url , :notice => "You have successfully subscribed to the group. What do you want to do next?"
		end	
  
  end
  
  def unjoinGroup
    	@url = "localhost:8087/oneVillageGroup/group/"
    	
    	@groupId = params[:groupId]
    	@userId = params[:userId]
    	
    	puts"\n\n Unjoin group in group\n...\n...\n...Group Id--->#{@groupId}\n\n User Id--->#{@userId}"
    	
    	@response = RestClient.delete(@url+"/groupmember/"+@groupId+"/"+@userId,:accept =>:json , :content_type=>:json)
    	
    	redirect_to root_url , :notice => "#{@response}. What do you want to do next?"
  
    end
    
  def showMembers
  	@url = "localhost:8087/oneVillageGroup/group/"
  	@groupId = params[:groupId]
  	  	puts "\n\n ---->Group Id is ---> #{@groupId}"

    	@response = RestClient.get(@url+"/groupmember/"+@groupId, :accept =>:json , :content_type=>:json)
  	 puts "\n\n---> list for response for all members...\n#{@response}\n\n"
     	@array= JSON.parse(@response, "object_class" => true)
	puts "\n\n---> list for response for all members in array...\n#{@array}\n\n"
	puts "\n\n---> \n\n----->...\n------>#{@array}\n\n"
	puts "\n\n---> \n\n----->...\n------>#{@array[0]["groupId"]}\n\n"
  end
 
 
   def getAllUserEvents
   	@url = "localhost:8087/oneVillageGroup/group/"
   	@groupId = params[:groupId]
   	@userId = params[:userId]
   	puts "\n\n ---->Group Id is @@@---> #{@groupId}"
   	puts "\n\n ---->User Id is @@@---> #{@userId}"
   	
   	@response = RestClient.get(@url+"/"+@groupId+"/event/user/"+@userId , :accept => :json, :content_type => :json)
   	@array= JSON.parse(@response, "object_class" => true)
  end
  
end
