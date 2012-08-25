class LinkedInConnect
  attr_accessor :oauth_consumer_key, :oauth_consumer_secret, :oauth_consumer_secret, :access_token
  attr_accessor :consumer_options # hash containing the configuration parameters for OAuth with LinkedIn
  
  def initialize(options = {})
     LinkedInConnect.load_config.each do |k, v|
      instance_variable_set("@#{k}", v)
    end
      @consumer_options = {
      :site => @site,
      :request_token_path => @request_token_path,
      :authorize_path => @authorize_path,
      :access_token_path => @access_token_path,
      :oauth_callback => @oauth_callback
    }
  end

  def LinkedInConnect.load_config
    @@stored_config ||= YAML.load(File.open(RAILS_ROOT + "/config/oauth.yml"))[Rails.env]
    return @@stored_config
  end

  # Public api for fetching data from LinkedIn
  def profile(token, secret)
    self.access_token = self.get_access_token(token, secret)
    req = self.access_token.request(:get, PAL_EPNTS['profile_url']+PAL_EPNTS['profile_fields'],{'x-li-format' => 'json','Content-Type'=>'application/json'})
    req.body
  end


  def base_profile(token, secret)
    self.access_token = self.get_access_token(token, secret)
    req = self.access_token.request(:get, PAL_EPNTS['profile_url']+PAL_EPNTS['base_profile_fields'],{'x-li-format' => 'json','Content-Type'=>'application/json'})
    req.body
  end

  def jobs(user, keyword, company_name)
    self.access_token = self.get_access_token(user.access_token_key, user.access_token_secret)
    req = self.access_token.request(:get, PAL_EPNTS['job_search']+keyword + "&company-name="+company_name,{'x-li-format' => 'json','Content-Type'=>'application/json'})
    req.body
  end

  def consumer
    @consumer ||= ::OAuth::Consumer.new(@oauth_consumer_key, @oauth_consumer_secret, @consumer_options)
  end

  def request_token(options={})
    @request_token ||= consumer.get_request_token(options.merge(@consumer_options))
  end

   def get_access_token(access_token,access_secret)
      consumer ||= ::OAuth::Consumer.new(@oauth_consumer_key, @oauth_consumer_secret, @consumer_options)
      @oauth_access_token ||= ::OAuth::AccessToken.new(consumer, access_token, access_secret)
   end

   def get_ac_tokens(request_token, request_secret, verifier)
     consumer ||= ::OAuth::Consumer.new(@oauth_consumer_key, @oauth_consumer_secret, @consumer_options)
     oauth_request_token = ::OAuth::RequestToken.new(consumer, request_token, request_secret)
     access_token = oauth_request_token.get_access_token(:oauth_verifier => verifier)
   end

  def clear_request_token
      @request_token = nil
  end

  def authorize_from_access(atoken, asecret)
    @atoken, @asecret = atoken, asecret
  end
end
