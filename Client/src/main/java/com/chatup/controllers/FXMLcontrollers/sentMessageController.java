package com.chatup.controllers.FXMLcontrollers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;


public class sentMessageController {

    String messageContent;
    String timeS;
    @FXML
    private Text sentMessageContent;
    @FXML
    private Text time;

    public sentMessageController(String messageContent, String timeS) {
        this.messageContent = messageContent;
        this.timeS = timeS;
    }

    public void initialize() {
        sentMessageContent.setText(messageContent);
        time.setText(timeS);
    }
}

