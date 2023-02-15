package com.chatup.controllers.services.implementations;

import com.chatup.controllers.FXMLcontrollers.recievedMessageController;
import com.chatup.controllers.FXMLcontrollers.sentMessageController;
import com.chatup.controllers.services.interfaces.ChatService;
import com.chatup.models.entities.*;
import com.chatup.network.ServerConnection;
import com.chatup.network.interfaces.Server;
import com.chatup.utils.CardMapper;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.rmi.RemoteException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ChatServicesImpl implements ChatService {

    private static ChatService chatService;

    private ChatServicesImpl() {
    }

    public static ChatService getChatService() {
        if (chatService == null)
            chatService = new ChatServicesImpl();
        return chatService;
    }

    private static VBox singleMessages(List<ChatMessage> messagesList) {
        FXMLLoader loader;
        VBox messages = new VBox();
        for (ChatMessage message : messagesList) {
            if (message.getSenderId() == CurrentUserImp.getCurrentUser().getId()) {
                loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/sentMessage.fxml"));
                sentMessageController sentController = new sentMessageController(message.getContent(), message.getMessageDateTime().format(DateTimeFormatter.ofPattern("E hh:mm a")));
                loader.setController(sentController);
            } else {
                loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/recievedMessage.fxml"));
                User user = UserServicesImpl.getUserServices().getUser(message.getSenderId());
                recievedMessageController recievedController = new recievedMessageController(user.getUserName(), message.getContent(), message.getMessageDateTime().format(DateTimeFormatter.ofPattern("E hh:mm a")));
                loader.setController(recievedController);
            }
            try {
                messages.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        String style = "-fx-background-image: url(" + ChatServicesImpl.class.getResource("/images/tile1.png").toString() + "); -fx-spacing: 25;";
        messages.setStyle(style);
        return messages;
    }

    private static VBox groupMessages(List<GroupMessage> messagesList) {
        FXMLLoader loader;
        VBox messages = new VBox();
        for (GroupMessage message : messagesList) {
            System.out.println("hi group");
            System.out.println("group message content" + message.getContent());
            if (message.getSenderId() == CurrentUserImp.getCurrentUser().getId()) {
                loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/sentMessage.fxml"));
                sentMessageController sentController = new sentMessageController(message.getContent(), message.getMessageDate().format(DateTimeFormatter.ofPattern("E hh:mm a")));
                loader.setController(sentController);
            } else {
                loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/recievedMessage.fxml"));
                User user = UserServicesImpl.getUserServices().getUser(message.getSenderId());
                recievedMessageController recievedController = new recievedMessageController(user.getUserName(), message.getContent(), message.getMessageDate().format(DateTimeFormatter.ofPattern("E hh:mm a")));
                loader.setController(recievedController);
            }
            try {
                messages.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        String style = "-fx-background-image: url(" + ChatServicesImpl.class.getResource("/images/tile1.png").toString() + "); -fx-spacing: 25;";
        messages.setStyle(style);
        return messages;
    }

    private static HBox groupMessage(GroupMessage message) {
        FXMLLoader loader;
        if (message.getSenderId() == CurrentUserImp.getCurrentUser().getId()) {
            loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/sentMessage.fxml"));
            sentMessageController sentController = new sentMessageController(message.getContent(), message.getMessageDate().format(DateTimeFormatter.ofPattern("E hh:mm a")));
            loader.setController(sentController);
        } else {
            loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/recievedMessage.fxml"));
            User user = UserServicesImpl.getUserServices().getUser(message.getSenderId());
            recievedMessageController recievedController = new recievedMessageController(user.getUserName(), message.getContent(), message.getMessageDate().format(DateTimeFormatter.ofPattern("E hh:mm a")));
            loader.setController(recievedController);
        }
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static HBox chatMessage(ChatMessage message) {
        FXMLLoader loader;
        if (message.getSenderId() == CurrentUserImp.getCurrentUser().getId()) {
            loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/sentMessage.fxml"));
            sentMessageController sentController = new sentMessageController(message.getContent(), message.getMessageDateTime().format(DateTimeFormatter.ofPattern("E hh:mm a")));
            loader.setController(sentController);
        } else {
            loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/recievedMessage.fxml"));
            User user = UserServicesImpl.getUserServices().getUser(message.getSenderId());
            recievedMessageController recievedController = new recievedMessageController(user.getUserName(), message.getContent(), message.getMessageDateTime().format(DateTimeFormatter.ofPattern("E hh:mm a")));
            loader.setController(recievedController);
        }
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public VBox getSingleChatVbox(int chatId) {
        Server server = ServerConnection.getServer();
        try {
            List<ChatMessage> list = server.getChatMessages(chatId);
            System.out.println("The lenght of the list is " + list.size());
            return singleMessages(list);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public VBox getGroupChatVbox(int chatId) {
        Server server = ServerConnection.getServer();
        try {
            List<GroupMessage> list = server.getGroupMessages(chatId);
            return groupMessages(list);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public HBox sendGroupMessage(GroupMessage message) {
        try {
            ServerConnection.getServer().sendGroupChatMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return groupMessage(message);
    }

    @Override
    public HBox recGroupMessage(GroupMessage message) {
        return groupMessage(message);
    }

    @Override
    public HBox sendChatMessage(ChatMessage message) {
        try {
            ServerConnection.getServer().sendChatMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return chatMessage(message);
    }

    @Override
    public HBox recChatMessage(ChatMessage message) {
        return chatMessage(message);
    }

    @Override
    public void updateChatList(int chatID, String content) {
        ObservableList<Card> chats = ListCoordinatorImpl.getListCoordinator().getUserChats();
        Card curChat = null;
        for (Card chat : chats) {
            if (chat.getCardID() == chatID) {
                curChat = chat;
            }
        }
        if (curChat != null) {
            curChat.setCardContent(content);
            chats.remove(curChat);
            chats.add(0, curChat);
        } else {
            Chat chat = ChatServicesImpl.getChatService().getChat(chatID);
            chats.add(0, CardMapper.getCard(chat, content));
        }
    }

    @Override
    public void updateGroupChatList(int groupChatID, String content) {
        ObservableList<Card> chats = ListCoordinatorImpl.getListCoordinator().getUserGroups();
        Card curChat = null;
        for (Card chat : chats) {
            if (chat.getCardID() == groupChatID) {
                curChat = chat;
                System.out.println("Found the chat");
            }
        }
        if (curChat != null) {
            curChat.setCardContent(content);
            chats.remove(curChat);
            chats.add(0, curChat);
        }
    }

    @Override
    public int createChat(Chat chat) {
        try {
            return ServerConnection.getServer().createChat(chat);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Chat getChat(int chatID) {
        try {
            return ServerConnection.getServer().getChat(chatID);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<User> getSingleChatUsers(int singleChatId) {
        try {
            return ServerConnection.getServer().getSingleChatUsers(singleChatId);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
