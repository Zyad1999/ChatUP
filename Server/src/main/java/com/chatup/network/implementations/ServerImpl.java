package com.chatup.network.implementations;

import com.chatup.controllers.reposotories.implementations.GroupMembershipRepoImpl;
import com.chatup.controllers.FXMLcontrollers.StatisticsDashboard;
import com.chatup.controllers.reposotories.implementations.UserRepoImpl;
import com.chatup.controllers.services.implementations.*;
import com.chatup.controllers.services.interfaces.AttachmentServices;
import com.chatup.controllers.services.interfaces.UserChatServices;
import com.chatup.models.entities.*;
import com.chatup.models.enums.ChatType;
import com.chatup.network.interfaces.Client;
import com.chatup.network.interfaces.Server;
import javafx.application.Platform;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
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
        int res = UserAuthImpl.getUserAuth().sign_Up(user);
        Platform.runLater(()->{
            StatisticsDashboard.getStatisticsDashboard().refershStatisitic();
        });
        return res;
    }

    @Override
    public User login(String phone, String password, Client client) throws RemoteException{
        User user = UserAuthImpl.getUserAuth().sign_In(phone, password);
        if(user != null){
            List<User> friends = FriendsServicesImpl.getFriendsServices().getUserFriends(user.getId());
            clients.put(user.getId(),client);
            for (User friend: friends){
                if(clients.containsKey(friend.getId())){
                    try {
                        clients.get(friend.getId()).friendLoggedIn(user.getId());
                    } catch (RemoteException e) {
                        System.out.println("Client Disconnected");
                        clients.remove(friend.getId());
                    }
                }
            }
            Platform.runLater(()->{
                StatisticsDashboard.getStatisticsDashboard().refershStatisitic();
            });
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
        for (User friend: friends){
            if(clients.containsKey(friend.getId())){
                try {
                    clients.get(friend.getId()).friendLoggedOut(id);
                } catch (RemoteException e) {
                    System.out.println("Client Disconnected");
                    clients.remove(friend.getId());
                }
            }
        }
        Platform.runLater(()->{
            StatisticsDashboard.getStatisticsDashboard().refershStatisitic();
        });
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
        System.out.println("int the accepted function");
        if(clients.containsKey(friendRequests.getSenderID())){
            System.out.println("Sending accepted to the sender");
            clients.get(friendRequests.getSenderID()).friendAcceptedRequest(friendRequests.getReceiverID());
        }
       return FriendsServicesImpl.getFriendsServices().updateFriendsRequestStatus(friendRequests);
    }
    @Override
    public int sendChatMessage(ChatMessage message) throws RemoteException{
        Chat chat = UserChatServicesImpl.getUserChatServices().getChat(message.getChatId());
        if(chat != null){
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
        return -1;
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
         for (User user:userList){
             if(clients.containsKey(user.getId())){
                 clients.get(user.getId()).addedToGroup(groupChatId);
             }
         }
    }

    @Override
    public GroupChat getGroupChat(int groupID) throws RemoteException{
        return UserGroupsServiceImp.getUserGroupsService().getGroupChat(groupID);
    }

    @Override
    public int createChat(Chat chat) throws RemoteException{
        return UserChatServicesImpl.getUserChatServices().createChat(chat);
    }

    @Override
    public Chat getChat(int chatID) throws RemoteException {
        return UserChatServicesImpl.getUserChatServices().getChat(chatID);
    }
    @Override
    public List<User> getGroupMembers(int groupId) throws RemoteException {
       List <GroupMembership>groupMembershipList = GroupMembershipRepoImpl.getInstance().getContactsGroupMembership(groupId);
       List<User> userList =new ArrayList<>();
        for (GroupMembership groupMembership:groupMembershipList  ) {
            userList.add(UserServicesImpl.getUserServices().getUserInfo(groupMembership.getUserId()));
        }
        return userList;
    }

    @Override
    public boolean updateUserInfo(User user) throws RemoteException {
        return UserServicesImpl.getUserServices().updateUserInfo(user);
    }
    @Override
    public boolean updateUserImage(int userID, String phone, byte[] img)throws RemoteException {
        return UserRepoImpl.getUserRepo().updateUserImg(userID,phone,img);
    }

    @Override
    public boolean updateUserPassword(int userID, String password)throws RemoteException {
        return UserRepoImpl.getUserRepo().updateUserPassword(userID,password);
    }

    @Override
    public int uploadChatFileToServer(byte[] file, ChatMessage message, Attachment attach) throws RemoteException {
        try {
            File theDir = new File("./files/"+message.getSenderId());
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            File serverpathfile = new File("./files/"+message.getSenderId()+"/"+attach.getAttachmentName()+"."+attach.getExtension());
            FileOutputStream out=new FileOutputStream(serverpathfile);
            byte [] data=file;
            out.write(data);
            out.flush();
            out.close();
            int attachmentID = AttachmentServicesImpl.getAttachmentService().addAttachment(attach);
            if(attachmentID==-1)
                return -1;
            message.setAttachment_Id(attachmentID);
            if(UserChatServicesImpl.getUserChatServices().sendChatMessage(message) == -1)
                return -1;
            Chat chat = UserChatServicesImpl.getUserChatServices().getChat(message.getChatId());
            if(chat != null){
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
            }
            return attachmentID;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int uploadGroupFileToServer(byte[] file, GroupMessage message, Attachment attach) throws RemoteException {
        try {
            File theDir = new File("./files/"+message.getGroupChatId());
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            File serverpathfile = new File("./files/"+message.getGroupChatId()+"/"+attach.getAttachmentName()+"."+attach.getExtension());
            FileOutputStream out=new FileOutputStream(serverpathfile);
            byte [] data=file;
            out.write(data);
            out.flush();
            out.close();
            int attachmentID = AttachmentServicesImpl.getAttachmentService().addAttachment(attach);
            if(attachmentID==-1)
                return -1;
            message.setAttachmentID(attachmentID);
            if( UserGroupsServiceImp.getUserGroupsService().sendGroupMessage(message) == -1)
                return -1;
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
            return attachmentID;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public byte[] downloadAttachment(int attachmentID, int senderID) throws RemoteException {
        Attachment attach = AttachmentServicesImpl.getAttachmentService().getAttachment(attachmentID);
        byte [] file;
        File serverpathfile = new File("./files/"+senderID+"/"+attach.getAttachmentName()+"."+attach.getExtension());
        file = new byte[(int) serverpathfile.length()];
        if(!serverpathfile.exists() || serverpathfile.isDirectory())
            return null;
        FileInputStream in;
        try {
            in = new FileInputStream(serverpathfile);
            try {
                in.read(file, 0, file.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public Attachment getAttachment(int attachmentID) throws RemoteException {
        return AttachmentServicesImpl.getAttachmentService().getAttachment(attachmentID);
    }
}