require 'test_helper'

class GroupControllerTest < ActionController::TestCase
  test "should get createGroup" do
    get :createGroup
    assert_response :success
  end

  test "should get deleteGroup" do
    get :deleteGroup
    assert_response :success
  end

  test "should get getAllGroups" do
    get :getAllGroups
    assert_response :success
  end

  test "should get getGroup" do
    get :getGroup
    assert_response :success
  end

  test "should get joinGroup" do
    get :joinGroup
    assert_response :success
  end

  test "should get unjoinGroup" do
    get :unjoinGroup
    assert_response :success
  end

  test "should get showMembers" do
    get :showMembers
    assert_response :success
  end

  test "should get createEvent" do
    get :createEvent
    assert_response :success
  end

  test "should get getAllEvents" do
    get :getAllEvents
    assert_response :success
  end

  test "should get getAllUserEvents" do
    get :getAllUserEvents
    assert_response :success
  end

  test "should get getEvent" do
    get :getEvent
    assert_response :success
  end

  test "should get joinEvent" do
    get :joinEvent
    assert_response :success
  end

  test "should get deleteEvent" do
    get :deleteEvent
    assert_response :success
  end

end
