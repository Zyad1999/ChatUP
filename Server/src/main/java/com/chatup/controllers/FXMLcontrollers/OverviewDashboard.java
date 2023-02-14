package com.chatup.controllers.FXMLcontrollers;

import com.chatup.models.enums.ServerState;
import com.chatup.network.ServerConnection;
import com.chatup.utils.StageManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class OverviewDashboard implements Initializable {
    private static double xOffset = 0;
    private static double yOffset = 0;
    private ServerConnection serverConnection;
    private ServerState serverState = ServerState.STOPPED;
    @FXML
    private Circle startCircle;
    @FXML
    private Button startServerButton;
    @FXML
    private Circle stopCircle;
    @FXML
    private Button stopServerButton;
    @FXML
    private Button statisticsButton;
    @FXML
    private Button announcementButton;
    @FXML
    private Button signoutButton;
    @FXML
    private HBox dragBar;

    @FXML
    void announcementButtonHandler(ActionEvent event) {
        StageManager.getInstance().switchToAnnouncement();
    }

    @FXML
    void closeButtonHandler(MouseEvent event) {
        if (serverState == ServerState.RUNNING) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Server is running!!!", ButtonType.OK);
            alert.setHeaderText(null);
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
            alert.setHeaderText(null);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                System.exit(0);
            }
        }
    }

    @FXML
    void overviewButtonHandler(ActionEvent event) {

    }

    @FXML
    void signoutButtonHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }

    @FXML
    void startServerButtonHandler(ActionEvent event) {
        serverConnection = ServerConnection.getServerConnectionInstance();
        serverConnection.startConnection();
        serverState = ServerState.RUNNING;
        System.out.println("Open Server");
        statisticsButton.setDisable(false);
        announcementButton.setDisable(false);
        signoutButton.setDisable(true);
        startServerButton.setDisable(true);
        stopServerButton.setDisable(false);
        startCircle.setFill(Color.web("#00FF00"));
        stopCircle.setFill(Color.WHITE);
    }

    @FXML
    void statisticsButtonHandler(ActionEvent event) {
        StageManager.getInstance().switchToStatistics();
    }

    @FXML
    void stopServerButtonHandler(ActionEvent event) {
        serverConnection.stopConnection();
        System.out.println("Stop Server");
        serverState = ServerState.STOPPED;
        statisticsButton.setDisable(true);
        announcementButton.setDisable(true);
        signoutButton.setDisable(true);
        stopServerButton.setDisable(true);
        startServerButton.setDisable(false);
        signoutButton.setDisable(false);
        stopCircle.setFill(Color.web("#FF0000"));
        startCircle.setFill(Color.WHITE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (serverState == ServerState.STOPPED) {
            statisticsButton.setDisable(true);
            announcementButton.setDisable(true);
            stopServerButton.setDisable(true);
            stopCircle.setFill(Color.web("#FF0000"));
        }
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