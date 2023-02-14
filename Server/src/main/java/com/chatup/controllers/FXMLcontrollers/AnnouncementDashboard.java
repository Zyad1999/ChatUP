package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.AnnouncementServiceImp;
import com.chatup.models.entities.Announcement;
import com.chatup.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AnnouncementDashboard implements Initializable {
    private static double xOffset = 0;
    private static double yOffset = 0;
    private Announcement announcement;
    @FXML
    private Button announcementButton;
    @FXML
    private TextArea announcementText;
    @FXML
    private ImageView closeXButton;
    @FXML
    private Button signoutButton;
    @FXML
    private Button statisticsButton;
    @FXML
    private HBox dragBar;

    @FXML
    void announcementButtonHandler(ActionEvent event) {

    }

    @FXML
    void closeButtonHandler(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Server is running!!!", ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    void overviewButtonHandler(ActionEvent event) {
        StageManager.getInstance().switchToOverview();
    }

    @FXML
    void sendButtonHandler(ActionEvent event) {
        String announcementText = this.announcementText.getText();
        if (!announcementText.equals("")) {
            // send announcementText
            AnnouncementServiceImp.getAnnouncementService().addAnnouncement(announcement);
            this.announcementText.setText("");
        }
    }

    @FXML
    void signoutButtonHandler(ActionEvent event) {

    }

    @FXML
    void statisticsButtonHandler(ActionEvent event) {
        StageManager.getInstance().switchToStatistics();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signoutButton.setDisable(true);
        dragBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        dragBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    @FXML
    public void minimiseButtonHandler(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
