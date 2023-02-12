package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.FriendRequestRepoImpl;
import com.chatup.controllers.reposotories.implementations.UserRepoImpl;
import com.chatup.controllers.services.interfaces.FriendsServices;
import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.User;
import com.chatup.models.enums.FriendRequestStatus;

import java.util.ArrayList;
import java.util.List;

public class FriendsServicesImpl implements FriendsServices {

    private static FriendsServices friendsServices;

    private FriendsServicesImpl(){}

    public static FriendsServices getFriendsServices(){
        if(friendsServices == null)
            friendsServices = new FriendsServicesImpl();
        return friendsServices;
    }

    @Override
    public List<User> getUserFriends(int userID) {
        List<FriendRequest> friendRequests = FriendRequestRepoImpl.getFriendRequestRepo().getAllUserFriendRequests(userID, FriendRequestStatus.ACCEPTED);
        List<User> friends = new ArrayList<>();
        for (FriendRequest friendRequest: friendRequests) {
            int friendID = (friendRequest.getSenderID() == userID) ? friendRequest.getReceiverID() : friendRequest.getSenderID();
            friends.add(UserRepoImpl.getUserRepo().getUser(friendID));
        }
        return friends;
    }

    @Override
    public List<User> getUserFriendRequests(int userID) {
        List<FriendRequest> requests =  FriendRequestRepoImpl.getFriendRequestRepo().getUserFriendRequests(userID,FriendRequestStatus.PENDING);
        List<User> requesters = new ArrayList<>();
        for (FriendRequest request : requests) {
            requesters.add(UserRepoImpl.getUserRepo().getUser(request.getSenderID()));

        }
        return requesters;
    }

    @Override
    public Boolean updateFriendsRequestStatus(FriendRequest friendRequest) {
         return FriendRequestRepoImpl.getFriendRequestRepo().updateFriendRequestStatus(friendRequest.getSenderID(),friendRequest.getReceiverID(),friendRequest.getRequestStatus());

    }
    public Boolean sendFriendRequest( List<FriendRequest> addRequests){
        for ( FriendRequest friendRequest:addRequests) {
            FriendRequestRepoImpl.getFriendRequestRepo().createFriendRequest(friendRequest);
        }
        return true;
    }

}
