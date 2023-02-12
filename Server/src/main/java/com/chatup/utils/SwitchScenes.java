package com.chatup.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public  void switchToDachboard(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(e.getClass().getResource("/views/dashboard.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setX(10);
        stage.setY(10);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
