package com.chatup.utils;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SwitchScenes {
    //create an object of SingleObject
    private static SwitchScenes instance = new SwitchScenes();
    //make the constructor private so that this class cannot be
    //instantiated
    private SwitchScenes(){}

    //Get the only object available
    public static SwitchScenes getInstance(){
        return instance;
    }

    public  void switchToSignUpSecond(Event e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/SignUpSecondScene.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public  void switchToSignUpFirst(Event e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/SignUpFirstScene.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public  void switchToSignInFirst(Event e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/SignInFirstScene.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public  void switchToSignInSecond(Event e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/SignInSecondScene.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public  void switchToSignInSecond(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(SwitchScenes.class.getResource("/views/SignInSecondScene.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public  void switchToChatScreen(Event e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/chatscreen.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setX(20);
        stage.setY(20);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public  void switchToChatScreen(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(SwitchScenes.class.getResource("/views/chatscreen.fxml"));
        stage.setX(20);
        stage.setY(20);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
