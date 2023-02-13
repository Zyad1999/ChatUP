package com.chatup.network.implementations;

import com.chatup.controllers.services.implementations.*;
import com.chatup.models.entities.*;
import com.chatup.network.interfaces.Client;
import com.chatup.network.interfaces.Server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements Server {
    private static Server server;
    public static ConcurrentHashMap<Integer, Client> clients = new ConcurrentHashMap<>();
    private ServerImpl() throws RemoteException {}

    public static Server getServer(){
        try {
            if(server==null)
                server = new ServerImpl();
            return server;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int signup(User user) throws RemoteException {
        return UserAuthImpl.getUserAuth().sign_Up(user);
    }

    @Override
    public User login(String phone, String password, Client client) throws RemoteException{
        User user = UserAuthImpl.getUserAuth().sign_In(phone, password);
        if(user != null){
            clients.put(user.getId(),client);
            //TODO notify friends
            return user;
        }else{
            return null;
        }
    }

    @Override
    public void logout(int id, Client client) throws RemoteException {
        List<User> friends = FriendsServicesImpl.getFriendsServices().getUserFriends(id);
        clients.remove(id, client);
        UserAuthImpl.getUserAuth().logout(id);
        //TODO notify friends
    }

    @Override
    public Map<Chat,ChatMessage> getUserChats(int userID) throws RemoteException {
        return UserServicesImpl.getUserServices().getUserchats(userID);
    }

    @Override
    public Map<GroupChat, GroupMessage> getUserGroups(int userID) throws RemoteException {
        return UserGroupsServiceImp.getUserGroupsService().getAllUserGroups(userID);
    }

    @Override
    public List<ChatMessage> getChatMessages(int chatID) throws RemoteException {
        return UserServicesImpl.getUserServices().getChatMsg(chatID);
    }

    @Override
    public List<GroupMessage> getGroupMessages(int groupID) throws RemoteException {
        return UserGroupsServiceImp.getUserGroupsService().getAllGroupMessages(groupID);
    }

    @Override
    public List<User> getUserFriends(int userID) throws RemoteException {
        return FriendsServicesImpl.getFriendsServices().getUserFriends(userID);
    }

    @Override
    public List<User> getUserFriendRequests(int userID) throws RemoteException {
        return FriendsServicesImpl.getFriendsServices().getUserFriendRequests(userID);
    }


    @Override
    public User getUser(int userID) throws RemoteException {
        return UserServicesImpl.getUserServices().getUserInfo(userID);
    }

    @Override
    public User getUser(String phoneNumber) throws RemoteException {
        return UserServicesImpl.getUserServices().getUserInfo(phoneNumber);
    }
    

    @Override
    public Boolean sendFriendRequest(List<FriendRequest> addRequests) throws RemoteException {
        return FriendsServicesImpl.getFriendsServices().sendFriendRequest(addRequests);
    }

    @Override
    public Boolean updateFriendsRequestStatus (FriendRequest friendRequests) throws RemoteException {
       return FriendsServicesImpl.getFriendsServices().updateFriendsRequestStatus(friendRequests);
    }
    @Override
    public int sendChatMessage(ChatMessage message) throws RemoteException{
        Chat chat = UserChatServicesImpl.getUserChatServices().getChat(message.getChatId());
        User receiver = UserServicesImpl.getUserServices().getUserInfo((chat.getFirstUserId() == message.getSenderId())?
                chat.getSecondUserId():chat.getFirstUserId());
        if(clients.containsKey(receiver.getId())){
            try{
                clients.get(receiver.getId()).sendChatMessage(message);
            } catch (RemoteException e) {
                System.out.println("Client Disconnected");
                clients.remove(receiver.getId());
            }
        }
        return UserChatServicesImpl.getUserChatServices().sendChatMessage(message);
    }

    @Override
    public int sendGroupChatMessage(GroupMessage message) throws RemoteException{
        List<User> groupMembers = UserGroupsServiceImp.getUserGroupsService().getGroupMembers(message.getGroupChatId());
        for(User member : groupMembers){
            if(clients.containsKey(member.getId()) && member.getId() != message.getSenderId()){
                try {
                    clients.get(member.getId()).sendGroupMessage(message);
                } catch (RemoteException e) {
                    System.out.println("Client Disconnected");
                    clients.remove(member.getId());
                }
            }
        }
        return UserGroupsServiceImp.getUserGroupsService().sendGroupMessage(message);
    }
    public int createGroupChat(GroupChat groupChat,List<User> userList) throws RemoteException {
        return UserGroupsServiceImp.getUserGroupsService().createGroupChat(groupChat,userList);
    }

    @Override
    public void addUsersToGroup(int groupChatId, List<User> userList) throws RemoteException {
         UserGroupsServiceImp.getUserGroupsService().addUsersToGroup(groupChatId,userList);
    }

    @Override
    public int createChat(Chat chat) throws RemoteException{
        return UserChatServicesImpl.getUserChatServices().createChat(chat);
    }
}