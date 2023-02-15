package com.chatup;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.UserAuthImp;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.User;
import com.chatup.network.ServerConnection;
import com.chatup.network.implementations.ClientImpl;
import com.chatup.utils.NotificationPopups;
import com.chatup.utils.RememberSetting;
import com.chatup.utils.SwitchScenes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
        User user = null;
        if(RememberSetting.getPhone() != null){
            if(RememberSetting.getPassword() != null){
                user = UserAuthImp.getUserAuth().loginAuth(RememberSetting.getPhone(), RememberSetting.getPassword());
                if ( user != null) {
                    CurrentUserImp.setCurrentUser(user);
                    System.out.println("corrected information");
                    SwitchScenes.getInstance().switchToChatScreen(stage);
                    return;
                }
            }else {
                user = UserServicesImpl.getUserServices().getUser(RememberSetting.getPhone());
                if(user != null){
                    CurrentUserImp.setCurrentUser(user);
                    SwitchScenes.getInstance().switchToSignInSecond(stage);
                    return;
                }
            }
        }
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