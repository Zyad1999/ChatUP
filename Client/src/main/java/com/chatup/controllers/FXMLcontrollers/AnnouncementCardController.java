package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.ChatServicesImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

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
        FXMLLoader loader;
        Parent parent;
        Scene scene;
        loader = new FXMLLoader(getClass().getResource("/views/announcementDetails.fxml"));
        AnnouncementDetails announcementDetails = new AnnouncementDetails(title, body);
        loader.setController(announcementDetails);
        try {
            parent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {
        announcementTitle.setText(title);
        announcementBody.setText(body);
    }

}

