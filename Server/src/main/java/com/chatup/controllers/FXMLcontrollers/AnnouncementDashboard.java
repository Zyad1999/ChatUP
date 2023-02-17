package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.AnnouncementServiceImp;
import com.chatup.models.entities.Announcement;
import com.chatup.network.implementations.ServerImpl;
import com.chatup.network.interfaces.Client;
import com.chatup.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class AnnouncementDashboard implements Initializable {
    private static double xOffset = 0;
    private static double yOffset = 0;
    @FXML
    private TextField title;
    @FXML
    private Button announcementButton;
    @FXML
    private TextArea body;
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

        if (!title.getText().equals("") && !body.getText().equals("")) {
            // send announcementText
            Announcement announcement = new Announcement(title.getText(), body.getText(), LocalDateTime.now());
            AnnouncementServiceImp.getAnnouncementService().addAnnouncement(announcement);
            System.out.println(title.getText() + " - " + body.getText() + " - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("E hh:mm a")));
            title.setText("");
            body.setText("");
            ConcurrentHashMap<Integer, Client> clients = ServerImpl.getOnlineClients();
            for (Client client : clients.values()) {
                try {
                    client.receiveAnnouncement(announcement);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Title and body is required", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
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
