package com.chatup.controllers.FXMLcontrollers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.time.LocalTime;

public class recievedMessageController {

    @FXML
    private Text recieverMessage;

    @FXML
    private Text recieverName;
    @FXML
    private Text time;

    private String recieverMessageName;

    private String recieverContent;
    String timeS;


    public recievedMessageController(String recieverMessageName, String recieverContent, String timeS) {
        this.recieverMessageName = recieverMessageName;
        this.recieverContent = recieverContent;
        this.timeS = timeS;
    }

    public void initialize() {
        time.setText(timeS);
        recieverMessage.setText(recieverContent);
        recieverName.setText(recieverMessageName);
    }
}