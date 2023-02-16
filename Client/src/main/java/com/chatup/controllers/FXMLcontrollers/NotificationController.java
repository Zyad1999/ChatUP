package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NotificationController {

    @FXML
    private ScrollPane scrollPane;

    public void initialize() {
        scrollPane.setContent(ListCoordinatorImpl.getListCoordinator().getAnnouncement());
    }

    @FXML
    public void closeButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
