package com.chatup.controllers.FXMLcontrollers;

import com.chatup.models.enums.ServerState;
import com.chatup.network.ServerConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
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
    void announcementButtonHandler(ActionEvent event) {

    }

    @FXML
    void closeButtonHandler(MouseEvent event) {
        closeApp();
    }

    @FXML
    void overviewButtonHandler(ActionEvent event) {

    }

    @FXML
    void signoutButtonHandler(ActionEvent event) {
        closeApp();
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
            signoutButton.setDisable(true);
            stopServerButton.setDisable(true);
        }
    }

    private void closeApp() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            if (serverState == ServerState.RUNNING) {
                serverConnection.stopConnection();
                System.out.println("logout successfully");
                System.exit(0);
            } else {
                System.exit(0);
            }
        }
    }
}