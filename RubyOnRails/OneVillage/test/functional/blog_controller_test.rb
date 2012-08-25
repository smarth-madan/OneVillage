require 'test_helper'

class BlogControllerTest < ActionController::TestCase
  test "should get createBlog" do
    get :createBlog
    assert_response :success
  end

  test "should get getBlog" do
    get :getBlog
    assert_response :success
  end

  test "should get getAllBlogs" do
    get :getAllBlogs
    assert_response :success
  end

  test "should get deleteBlog" do
    get :deleteBlog
    assert_response :success
  end

  test "should get createBlogEntry" do
    get :createBlogEntry
    assert_response :success
  end

  test "should get getBlogEntries" do
    get :getBlogEntries
    assert_response :success
  end

  test "should get deleteBlogEntries" do
    get :deleteBlogEntries
    assert_response :success
  end

end
