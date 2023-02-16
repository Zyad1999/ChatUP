package com.chatup.controllers.FXMLcontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class AnnouncementCardController {

    String title, body;
    @FXML
    private Text announcementBody;
    @FXML
    private Text announcementTitle;

    public AnnouncementCardController(String title, String body) {
        this.title = title;
        this.body = body;
    }

    @FXML
    void openAnnouncement(ActionEvent event) {
        if (!announcementTitle.getText().equals("") && !announcementBody.getText().equals("")) {

        }
    }

    public void initialize() {
        announcementTitle.setText(title);
        announcementBody.setText(body);
    }
}

