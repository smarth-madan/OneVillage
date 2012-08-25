require 'rest-client'
require 'json'

class TestController < ApplicationController

def signin
	puts "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Inside signin@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
	@token =  params[:access_token]
	if @token == "undefined" || @token.nil?
		puts "redirect to HOMEEEEE"
	else
		@url = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=#{@token}"
		puts "url = #{@url}"
		@result = RestClient.get(@url,:accept => :json)
		@result=JSON.parse(@result, "object_class" => true);
		puts "\n\n --> result #{@result['email']}\n\n"	
		@user = @result['email']
		cookies["_auth"] = {:value => @user,
							:expires =>20.years.from_now.utc
						}
		session["_auth"] = @user
		puts "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@COOKIES SET@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"
	end
	
	redirect_to "/home/index"
end

def sign_out
	cookies["_auth"]= nil
	session["_auth"] = nil
	redirect_to root_url
end

def signed_in?
	return (cookies["_auth"]!=nil)
end

end
