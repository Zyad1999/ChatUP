package com.chatup;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.network.ServerConnection;
import com.chatup.network.implementations.ClientImpl;
import com.chatup.utils.NotificationPopups;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.rmi.RemoteException;

public class App extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/SignInFirstScene.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void stop(){
        try {
            ServerConnection.getServer().logout(CurrentUserImp.getCurrentUser().getId(), ClientImpl.getClient());
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("Server Disconnected");
        }
    }
}