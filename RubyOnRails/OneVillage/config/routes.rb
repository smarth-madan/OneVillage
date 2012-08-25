#IKAT::Application.routes.draw do

ActionController::Routing::Routes.draw do |map|

  map.connect ':controller/:action/:id'
  map.connect ':controller/:action/:id.:format'
  map.root :controller => "home" 
  
  get "photo/new"
  get "photo/showallblogs"
  get "photo/createBlog"
  get "photo/getBlog"
  get "photo/getAllBlogs"
  get "photo/deleteBlog"
  get "photo/createBlogEntry"
  get "photo/getBlogEntries"
  get "photo/deleteBlogEntry"
  
  get "group/createGroup"
  get "group/deleteGroup"
  get "group/getAllGroups"
  get "group/getGroup"
  get "group/joinGroup"
  get "group/unjoinGroup"
  get "group/showMembers"
  get "group/createEvent"
  get "group/getAllEvents"
  get "group/getAllUserEvents"
  get "group/getEvent"
  get "group/joinEvent"
  get "group/deleteEvent"
  get "group/newEvent"
  get "group/user"
  
  
  get "home/index"
  get "home/home"
  get "home/contact"
  get "home/blog"
  get "home/group"
  get "home/photo"
  get "home/createblog"
  get "home/deleteblog"
  get "home/getblog"
  get "home/getallblogs"
  get "home/createblogentry"
  get "home/getblogentries"
  get "home/deleteblogentries"
  get "home/createevent"
  get "home/creategroup"
  get "home/deleteevent"
  get "home/deletegroup"
  get "home/getallevents"
  get "home/getallgroups"
  get "home/getalluserevents"
  get "home/getevent"
  get "home/getgroup"
  get "home/joinevent"
  get "home/joingroup"
  get "home/showmembers"
  get "home/unjoingroup"
  
  get "blog/new"
  get "blog/showallblogs"
  get "blog/createBlog"
  get "blog/getBlog"
  get "blog/getAllBlogs"
  get "blog/deleteBlog"
  get "blog/createBlogEntry"
  get "blog/getBlogEntries"
  get "blog/deleteBlogEntry"
  get "blog/updateArticleView"
  get "message/index"
  get "blog/error"
  
  get "test/index"

  # The priority is based upon order of creation:
  # first created -> highest priority.

  # Sample of regular route:
  #   match 'products/:id' => 'catalog#view'
  # Keep in mind you can assign values other than :controller and :action

  # Sample of named route:
  #   match 'products/:id/purchase' => 'catalog#purchase', :as => :purchase
  # This route can be invoked with purchase_url(:id => product.id)

  # Sample resource route (maps HTTP verbs to controller actions automatically):
  #   resources :products

  # Sample resource route with options:
  #   resources :products do
  #     member do
  #       get 'short'
  #       post 'toggle'
  #     end
  #
  #     collection do
  #       get 'sold'
  #     end
  #   end

  # Sample resource route with sub-resources:
  #   resources :products do
  #     resources :comments, :sales
  #     resource :seller
  #   end

  # Sample resource route with more complex sub-resources
  #   resources :products do
  #     resources :comments
  #     resources :sales do
  #       get 'recent', :on => :collection
  #     end
  #   end

  # Sample resource route within a namespace:
  #   namespace :admin do
  #     # Directs /admin/products/* to Admin::ProductsController
  #     # (app/controllers/admin/products_controller.rb)
  #     resources :products
  #   end

  # You can have the root of your site routed with "root"
  # just remember to delete public/index.html.
    root :to => "home#index", :as => 'index'

  # See how all your routes lay out with "rake routes"

  # This is a legacy wild controller route that's not recommended for RESTful applications.
  # Note: This route will make all actions in every controller accessible via GET requests.
  # match ':controller(/:action(/:id(.:format)))'
end
