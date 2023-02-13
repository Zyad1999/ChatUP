package com.chatup.network.implementations;

import com.chatup.controllers.services.implementations.ChatServicesImpl;
import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.Card;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.GroupMessage;
import com.chatup.models.entities.User;
import com.chatup.network.interfaces.Client;
import com.chatup.utils.CardMapper;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClientImpl extends UnicastRemoteObject implements Client {

    private static Client client;

    private ClientImpl() throws RemoteException {}

    public static Client getClient(){
        try {
            if(client==null)
                client = new ClientImpl();
            return client;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendGroupMessage(GroupMessage message) throws RemoteException {
        System.out.println("Recived Group Message");
        Platform.runLater(()->{
            ListCoordinatorImpl.getListCoordinator().getGroupChatVbox(message.getGroupChatId()).getChildren()
                    .add(ChatServicesImpl.getChatService().recGroupMessage(message));
            ChatServicesImpl.getChatService().updateGroupChatList(message.getGroupMessageId(),message.getContent());
        });
    }

    @Override
    public void sendChatMessage(ChatMessage message) throws RemoteException {
        System.out.println("Received Chat message");
        Platform.runLater(()->{
            ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(message.getChatId()).getChildren()
                    .add(ChatServicesImpl.getChatService().recChatMessage(message));
            ChatServicesImpl.getChatService().updateChatList(message.getChatId(),message.getContent());
        });
    }

    @Override
    public void friendLoggedIn(int friendID) throws RemoteException {
        Platform.runLater(()->{
            List<Card> offlineFriends = ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends();
            Card loggedInFriend = null;
            for(Card friend:offlineFriends){
                if(friendID == friend.getCardID()){
                    loggedInFriend = friend;
                }
            }
            if(loggedInFriend != null){
                ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends().remove(loggedInFriend);
                ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends().add(0,loggedInFriend);
            }
        });
    }

    @Override
    public void friendLoggedOut(int friendID) throws RemoteException {
        Platform.runLater(()->{
            List<Card> onlineFriends = ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends();
            Card loggedOutFriend = null;
            for(Card friend:onlineFriends){
                if(friendID == friend.getCardID()){
                    loggedOutFriend = friend;
                }
            }
            if(loggedOutFriend != null){
                ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends().remove(loggedOutFriend);
                ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends().add(0,loggedOutFriend);
            }
        });
    }

    @Override
    public void friendAcceptedRequest(int friendID) throws RemoteException{
        System.out.println("a friend accepted request huraaaaaaahhhhhh");
        Platform.runLater(()->{
            User newFriend = UserServicesImpl.getUserServices().getUser(friendID);
            addFriendToOnlineList(newFriend);
        });
    }

    @Override
    public void receivedFriendRequest(int friendID) throws RemoteException {
        System.out.println("You recived a friend request huraaaaaaahhhhhh");
        Platform.runLater(()->{
            ListCoordinatorImpl.getListCoordinator().updateFriendRequests();
        });
    }

    private static void addFriendToOnlineList(User friend){
        Card friendCard = CardMapper.getCard(friend);
        ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends().add(0,friendCard);
    }
    private static void addFriendToOfflineList(User friend){
        Card friendCard = CardMapper.getCard(friend);
        ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends().add(0,friendCard);
    }
}
