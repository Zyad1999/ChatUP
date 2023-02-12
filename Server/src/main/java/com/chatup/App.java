package com.chatup;

import com.chatup.network.ServerConnection;
import com.chatup.models.enums.ServerState;
import com.chatup.utils.SwitchScenes;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {
    ServerConnection serverConnection;
    ServerState serverState = ServerState.STOPPED;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
//        Button serverStartStop = new Button("Start");
//        Button openDashboard = new Button("Open Dashboard");
//        serverStartStop.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                if(serverState==ServerState.STOPPED){
//                    serverConnection = ServerConnection.getServerConnectionInstance();
//                    serverConnection.startConnection();
//                    serverState = ServerState.RUNNING;
//                    serverStartStop.setText("Stop");
//                }else{
//                    serverConnection.stopConnection();
//                    serverState = ServerState.STOPPED;
//                    serverStartStop.setText("Start");
//                }
//            }
//        });
//        StackPane root = new StackPane(serverStartStop, openDashboard);
//        Scene scene = new Scene(root, 600, 300);
//        primaryStage.setScene(scene);
//        primaryStage.show();

        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/views/dashboard.fxml"));
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
}