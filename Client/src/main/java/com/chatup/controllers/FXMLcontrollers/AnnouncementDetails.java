package com.chatup.controllers.FXMLcontrollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AnnouncementDetails implements Initializable {
    @FXML
    private Label TitleAnnouncement;

    @FXML
    private Text bodyAnnouncement;
    String title, body;
    public AnnouncementDetails(String title, String body) {
        this.title = title;
        this.body = body;
    }
    public void initialize(URL location, ResourceBundle resources) {
        TitleAnnouncement.setText(title);
        bodyAnnouncement.setText(body);
    }
    @FXML
    public void closeButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
