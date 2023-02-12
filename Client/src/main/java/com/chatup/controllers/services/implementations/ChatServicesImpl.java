package com.chatup.controllers.services.implementations;

import com.chatup.controllers.FXMLcontrollers.recievedMessageController;
import com.chatup.controllers.FXMLcontrollers.sentMessageController;
import com.chatup.controllers.services.interfaces.ChatService;
import com.chatup.controllers.services.interfaces.UserLists;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.GroupMessage;
import com.chatup.models.entities.User;
import com.chatup.network.ServerConnection;
import com.chatup.network.interfaces.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Currency;
import java.util.List;

public class ChatServicesImpl implements ChatService {

    private static ChatService chatService;

    private ChatServicesImpl(){}

    public static ChatService getChatService(){
        if (chatService==null)
            chatService = new ChatServicesImpl();
        return chatService;
    }
    @Override
    public  VBox getSingleChatVbox(int chatId) {
        Server server = ServerConnection.getServer();
        try {
            List<ChatMessage> list = server.getChatMessages(chatId);
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

    private static VBox singleMessages(List<ChatMessage> messagesList)  {
        FXMLLoader loader;
        VBox messages = new VBox();
        for(ChatMessage message: messagesList ){
            if(message.getSenderId()== CurrentUserImp.getCurrentUser().getId()){
                loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/sentMessage.fxml"));
                sentMessageController sentController = new sentMessageController(message.getContent());
                loader.setController(sentController);
            }
            else{
                loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/recievedMessage.fxml"));
                User user = UserServicesImpl.getUserServices().getUser(message.getSenderId());
                recievedMessageController recievedController = new recievedMessageController(user.getUserName(),message.getContent());
                loader.setController(recievedController);
            }
            try {
                messages.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        messages.setStyle("-fx-background-color: transparent;-fx-spacing: 25;");
        return messages;
    }

    private static VBox groupMessages(List<GroupMessage> messagesList)  {
        FXMLLoader loader;
        VBox messages = new VBox();
        for(GroupMessage message: messagesList ){
            System.out.println("hi group");
            System.out.println("group message content"+message.getContent());
            // TODO change currentUserID
            if(message.getSenderId()==CurrentUserImp.getCurrentUser().getId()){
                loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/sentMessage.fxml"));
                sentMessageController sentController = new sentMessageController(message.getContent());
                loader.setController(sentController);
            }
            else{
                loader = new FXMLLoader(ChatServicesImpl.class.getResource("/views/recievedMessage.fxml"));
                User user = UserServicesImpl.getUserServices().getUser(message.getSenderId());
                recievedMessageController recievedController = new recievedMessageController(user.getUserName(),message.getContent());
                loader.setController(recievedController);
            }
            try {
                messages.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        messages.setStyle("-fx-background-color: transparent;-fx-spacing: 25;");
        return messages;
    }
}
