package com.chatup.network.implementations;

import com.chatup.controllers.services.implementations.*;
import com.chatup.models.entities.*;
import com.chatup.models.enums.UserMode;
import com.chatup.network.interfaces.Client;
import com.chatup.utils.CardMapper;
import com.chatup.utils.NotificationPopups;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.util.List;

public class ClientImpl extends UnicastRemoteObject implements Client {

    private static Client client;

    private ClientImpl() throws RemoteException {
    }

    public static Client getClient() {
        try {
            if (client == null)
                client = new ClientImpl();
            return client;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addFriendToOnlineList(User friend) {
        Card friendCard = CardMapper.getCard(friend);
        ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends().add(0, friendCard);
    }

    private static void addFriendToOfflineList(User friend) {
        Card friendCard = CardMapper.getCard(friend);
        ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends().add(0, friendCard);
    }

    @Override
    public void sendGroupMessage(GroupMessage message) throws RemoteException {
        System.out.println("Recived Group Message");
        Platform.runLater(() -> {
            ListCoordinatorImpl.getListCoordinator().getGroupChatVbox(message.getGroupChatId()).getChildren()
                    .add(ChatServicesImpl.getChatService().recGroupMessage(message));
            ChatServicesImpl.getChatService().updateGroupChatList(message.getGroupMessageId(), message.getContent());
            NotificationPopups.receiveNotification("New message ✉️\uD83D\uDC68\u200D\uD83D\uDC69\u200D\uD83D\uDC66\u200D\uD83D\uDC66 from " + UserServicesImpl.getUserServices().getUser(message.getSenderId()).getUserName(), message.getContent(), "/images/newMessage.png");
        });
    }

    @Override
    public void sendChatMessage(ChatMessage message) throws RemoteException {
        System.out.println("Received Chat message");
        Platform.runLater(() -> {
            ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(message.getChatId()).getChildren()
                    .add(ChatServicesImpl.getChatService().recChatMessage(message));
            ChatServicesImpl.getChatService().updateChatList(message.getChatId(), message.getContent());
            NotificationPopups.receiveNotification("New message ✉️\uD83E\uDDD1\u200D\uD83E\uDD1D\u200D\uD83E\uDDD1 from " + UserServicesImpl.getUserServices().getUser(message.getSenderId()).getUserName(), message.getContent(), "/images/newMessage.png");
        });
        // Check If Client Activated ChatBot Service
        if(ChatterBotService.getChatterBotService().botStatus==true) {
            System.out.println(ChatterBotService.getChatterBotService().botStatus);
            Platform.runLater(() -> {
                ChatMessage msg = null;
                try {
                    msg = new ChatMessage(message.getChatId(), CurrentUserImp.getCurrentUser().getId(),
                            ChatterBotService.getChatterBotService().thinkBot(message.getContent()), LocalDateTime.now(), 0);
                    ChatMessage finalMsg = msg;
                    delay(4000, new Runnable() {
                        @Override
                        public void run() {
                            ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(finalMsg.getChatId()).getChildren().add(ChatServicesImpl.getChatService().sendChatMessage(finalMsg));
                            ChatServicesImpl.getChatService().updateChatList(finalMsg.getChatId(), ChatterBotService.getChatterBotService().thinkBot(message.getContent()));
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @Override
    public void addedToGroup(int groupID) throws RemoteException{
        GroupChat group = GroupServicesImpl.getGroupService().getGroupChat(groupID);
        if(group!=null) {
            Platform.runLater(() -> {
                ListCoordinatorImpl.getListCoordinator().getUserGroups().add(CardMapper.getCard(group, ""));
            });
        }
    }

    @Override
    public void friendLoggedIn(int friendID) throws RemoteException {
        Platform.runLater(() -> {
            List<Card> offlineFriends = ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends();
            Card loggedInFriend = null;
            for (Card friend : offlineFriends) {
                if (friendID == friend.getCardID()) {
                    loggedInFriend = friend;
                }
            }
            if (loggedInFriend != null) {
                ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends().remove(loggedInFriend);
                ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends().add(0, loggedInFriend);
            }
        });
    }

    @Override
    public void friendLoggedOut(int friendID) throws RemoteException {
        Platform.runLater(() -> {
            List<Card> onlineFriends = ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends();
            Card loggedOutFriend = null;
            for (Card friend : onlineFriends) {
                if (friendID == friend.getCardID()) {
                    loggedOutFriend = friend;
                }
            }
            if (loggedOutFriend != null) {
                ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends().remove(loggedOutFriend);
                ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends().add(0, loggedOutFriend);
            }
        });
    }

    @Override
    public void friendAcceptedRequest(int friendID) throws RemoteException {
        System.out.println("a friend accepted request huraaaaaaahhhhhh");
        Platform.runLater(() -> {
            User newFriend = UserServicesImpl.getUserServices().getUser(friendID);
            addFriendToOnlineList(newFriend);
        });
    }

    @Override
    public void receivedFriendRequest(int friendID) throws RemoteException {
        System.out.println("You recived a friend request huraaaaaaahhhhhh");
        Platform.runLater(() -> {
            ListCoordinatorImpl.getListCoordinator().updateFriendRequests();
        });
    }

    // Delay function to delay ChatBot Respone to handle bots talk to each other
    public static void delay(long millis, Runnable continuation) {
        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try { Thread.sleep(millis); }
                catch (InterruptedException e) { }
                return null;
            }
        };
        sleeper.setOnSucceeded(event -> continuation.run());
        new Thread(sleeper).start();
    }
}
