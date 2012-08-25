class ApplicationController < ActionController::Base
  protect_from_forgery
 
 before_filter :cors_preflight_check
 after_filter :cors_set_access_control_headers
	
   def cors_set_access_control_headers
   puts "after <<<" * 10
  headers['Access-Control-Allow-Origin'] = '*'
  headers['Access-Control-Allow-Methods'] = 'POST, GET, OPTIONS'
  headers['Access-Control-Max-Age'] = "1728000"
 end
 
 def cors_preflight_check
  puts "before <<<" * 10
  if request.method == :options
    headers['Access-Control-Allow-Origin'] = '*'
    headers['Access-Control-Allow-Methods'] = 'POST, GET, OPTIONS'
    headers['Access-Control-Allow-Headers'] = 'X-Requested-With, X-Prototype-Version'
    headers['Access-Control-Max-Age'] = '1728000'
    render :text => '', :content_type => 'text/plain'
  end
 end
   
end