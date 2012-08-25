module SessionHelper
	   
   def sign_in(user)
	session[:remember_token] = user
	puts "user = "+session[:remember_token]
	cookies.permanent.signed[:remember_token] = user
    #self.current_user = user
	puts "Signed IN!!!!!!!!!!!!!!!!!!!!! as #{cookies[:remember_token]}"
	end
  
  def signed_in?
	!session[:remember_token].nil?
  end
  
  def sign_out
    cookies.delete(:_auth)
	session[:remember_token] = nil
	#self.current_user = nil
  end
  
 end