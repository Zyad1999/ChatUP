package com.chatup.controllers.FXMLcontrollers;
import javafx.fxml.FXML;
import javafx.scene.text.Text;


public class sentMessageController {

    @FXML
    private Text sentMessageContent;

    String messageContent;

    public sentMessageController(String messageContent) {
        this.messageContent = messageContent;
    }

    public void initialize(){
        sentMessageContent.setText(messageContent);
    }
}

