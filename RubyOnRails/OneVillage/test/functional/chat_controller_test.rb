require 'test_helper'

class ChatControllerTest < ActionController::TestCase
  test "should get login" do
    get :login
    assert_response :success
  end

  test "should get signout" do
    get :signout
    assert_response :success
  end

  test "should get getAllUsers" do
    get :getAllUsers
    assert_response :success
  end

  test "should get startSession" do
    get :startSession
    assert_response :success
  end

  test "should get addUserToSession" do
    get :addUserToSession
    assert_response :success
  end

  test "should get removeUserFromSession" do
    get :removeUserFromSession
    assert_response :success
  end

  test "should get sendMessage" do
    get :sendMessage
    assert_response :success
  end

  test "should get getMessage" do
    get :getMessage
    assert_response :success
  end

end
