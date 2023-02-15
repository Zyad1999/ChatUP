package com.chatup.utils;

import com.chatup.controllers.FXMLcontrollers.ChatScreenController;
import com.chatup.controllers.FXMLcontrollers.recievedMessageController;
import com.chatup.controllers.FXMLcontrollers.sentMessageController;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SingleChatMessage {

    public static HBox sendMessages = new HBox();
    public static TextFlow sendMessagesContent ;

    public static VBox recieveMessagesBox = new VBox();
    public static HBox recieveMessages = new HBox();
    public static TextFlow recieveMessageName;
    public static TextFlow recieveMessageContent;


    public static VBox singleMessages(List<ChatMessage> messagesList)  {
        FXMLLoader loader;
        VBox messages = new VBox();
        for(ChatMessage message: messagesList ){
            if(message.getSenderId()==1){
                loader = new FXMLLoader(SingleChatMessage.class.getResource("/views/sentMessage.fxml"));
                sentMessageController sentController = new sentMessageController(message.getContent(), message.getMessageDateTime().format(DateTimeFormatter.ofPattern("E hh:mm a")));
                loader.setController(sentController);
            }
            else{
                loader = new FXMLLoader(SingleChatMessage.class.getResource("/views/recievedMessage.fxml"));
                User user = UserServicesImpl.getUserServices().getUser(message.getSenderId());
                recievedMessageController recievedController = new recievedMessageController(user.getUserName(),message.getContent(), message.getMessageDateTime().format(DateTimeFormatter.ofPattern("E hh:mm a")));
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
