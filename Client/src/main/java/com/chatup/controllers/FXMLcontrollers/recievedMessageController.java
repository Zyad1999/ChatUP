package com.chatup.controllers.FXMLcontrollers;

import javafx.fxml.FXML;

import javafx.scene.text.Text;

public class recievedMessageController {

    @FXML
    private Text recieverMessage;

    @FXML
    private Text recieverName;

    private String recieverMessageName;

    private String recieverContent;

    public recievedMessageController(String recieverMessageName, String recieverContent) {
        this.recieverMessageName = recieverMessageName;
        this.recieverContent = recieverContent;
    }

    public void initialize() {

        recieverMessage.setText(recieverContent);
        recieverName.setText(recieverMessageName);
    }
}