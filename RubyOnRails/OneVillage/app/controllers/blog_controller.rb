require 'rest-client'
require 'date'
require 'json'

class BlogController < ApplicationController
	respond_to :json
	
  
  def createBlog
  		
	@url = "http://localhost:8087/oneVillageBlog/blog"
  	@name = cookies["_auth"]
    @subject = (params[:subject])
    @description = (params[:description])
    @time = Time.now.to_s
    
  	@payload = {"timestamp" => @time,
				"subject" => @subject,
				"userid"=> @name,
				"description" => @description}.to_json

    @response = RestClient.post(@url, @payload, {"Content-Type" => 'application/json',
												"Accept" => 'application/json'})
	
	logger.debug "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ attributes hash:  #{@response}"
	redirect_to root_url , :notice => "Blog successfully created, What do you want to do next?"
	
   end

  def getBlog
  	@url = "http://localhost:8087/oneVillageBlog/blog"
  	
  	@blogid = params[:id]
  	puts "\n\n--> blog #{@blogid}\n\n"
  	
  	@result = RestClient.get(@url+"/"+@blogid, :accept => :json , :content_type => :json)
  	puts "\n\n --> result #{@result}\n\n"
  	@array=JSON.parse(@result, "object_class" => true);
	logger.debug "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ blogId: #{@array}"
	@blogid = @array['blogid']
	@subject = @array['subject']  
	@description = @array['description']  
	@userid = @array['userid']  
	@timestamp = @array['timestamp']
		
		puts "\n\n---> blog id : #{@id}\n\n---> userid: #{@userid}"
   end

  def getAllBlogs
  	
  	@url = "http://localhost:8087/oneVillageBlog/blog"
  	@response = RestClient.get(@url, :accept => :json)  
  	puts "\n\n---> list for response\n#{response}\n\n"
	logger.debug "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Response:  #{@response}"
  	@array = JSON.parse(@response, "object_class" => true)
	#@b = @a.split(',');
	@array.each do |b|
		logger.debug "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ b: #{b["blogid"]}"
	end
  	
  end

  def deleteBlog
  	@url = "http://localhost:8087/oneVillageBlog/blog"
  	
  	@blog_id = params[:id]
  	puts "\n\n--> blog #{@blog_id}\n\n"
	
  	@userid = params[:userid] ||""

  	@result = RestClient.delete(@url+"/"+@blog_id+"/"+@userid, :accept => :json, :content_type=>:json)
  	puts "\n\n --> result #{@result}\n\n"
  	
  	redirect_to root_url , :notice => "Your selected blog has been deleted. What do you wanna do next??"
  	
  end

  def createArticle
  	
	@url = "http://localhost:8087/oneVillageBlog/blog"
  	@content = params[:content]
    @userid = params[:userid]
    @time = Time.now.to_s
    @blogid = params[:id]
    
    puts "\n\n --> blogid #{@blogid}\n\n --> userid #{@userid}"
   
    
      
  	@payload = {"timestamp" => @time,
				"content" => @content,
				"userid"=> @userid}.to_json

    response = RestClient.post(@url+"/"+@blogid+"/"+"article", @payload, {"Content-Type" => 'application/json',
												"Accept" => 'application/json'})
	
	redirect_to root_url , :notice => "Article successfully Created, What do you want to do next?"
	
  end

  def getArticles
  	
  	@url = "http://localhost:8087/oneVillageBlog/blog"
  	@blogid = params[:id]
  	@userid = params[:userid]
  	
  	puts "\n\n---> blog id : #{@blogid}\n\n---> userid: #{@userid}"
  	
  	@response = RestClient.get(@url+"/"+@blogid+"/"+"article", :accept => :json)  
  	puts "\n\n---> list for response\n#{@response}\n\n"
  	
	begin
		@array = JSON.parse(@response, "object_class" => true)
	rescue JSON::ParserError => e
		redirect_to root_url , :notice => @response
  	end
  	
  end
  
  def error
		@error = params[:error];
  end

  def deleteArticle
  	
  	@url = "http://localhost:8087/oneVillageBlog/blog"
  	@blogid = params[:id]
  	@blogentryid = params[:entryid]
  	@userid = params[:userid]
  	
  	puts "\n\n---> blog id : #{@blogid}\n\n---> blogentryid: #{@blogentryid}\n\n---> userid: #{@userid}"
  	
  	@response = RestClient.delete(@url+"/"+@blogid+"/"+"article"+"/"+@blogentryid+"/"+@userid, :accept => :json)  
  	redirect_to root_url , :notice => "Your selected blog entry has been deleted. What do you wanna do next??"
  	
  end
  
   def updateArticle
	
  	@url = "http://localhost:8087/oneVillageBlog/blog"
	
	@content = params[:content]
    @userid = params[:userid]
    @time = Time.now.to_s
    @blogid = params[:id]
	@articleid = params[:articleid]
  	
	@payload = {"timestamp" => @time,
				"content" => @content,
				"userid"=> @userid,
				"articleid"=>@articleid,
				"blogid"=>@blogid}.to_json
	
  	puts "\n\n---> blog id : #{@blogid}\n\n---> blogentryid: #{@articleid}\n\n---> userid: #{@userid}"
  	
  	response = RestClient.put(@url+"/"+@blogid+"/"+"article/"+@articleid+"/"+@userid, @payload, {"Content-Type" => 'application/json',
												"Accept" => 'application/json'})
  	redirect_to root_url , :notice => "Article Updated Successfully. What do you wanna do next??"
  	
  end
  
   def updateArticleView
	
	@content = params[:content]
    @userid = params[:userid]
    @time = Time.now.to_s
    @blogid = params[:id]
  	
  	puts "\n\n---> blog id : #{@blogid}\n\n---> blogentryid: #{@blogentryid}\n\n---> userid: #{@userid}"
  	
  end

end
