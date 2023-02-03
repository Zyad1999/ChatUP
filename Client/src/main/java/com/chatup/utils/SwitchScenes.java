package com.chatup.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SwitchScenes {


    public static void switchToSignUpSecond(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/SignUpSecondScene.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSignUpFirst(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/SignUpFirstScene.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSignInFirst(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/SignInFirstScene.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToSignInSecond(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/SignInSecondScene.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public static void switchToChatScreen(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/chatscreen.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
