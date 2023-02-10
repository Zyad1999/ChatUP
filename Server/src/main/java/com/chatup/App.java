package com.chatup;

import com.chatup.network.ServerConnection;
import com.chatup.models.enums.ServerState;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    ServerConnection serverConnection;
    ServerState serverState = ServerState.STOPPED;
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button serverStartStop = new Button("Start");
        serverStartStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(serverState==ServerState.STOPPED){
                    serverConnection = ServerConnection.getServerConnectionInstance();
                    serverConnection.startConnection();
                    serverState = ServerState.RUNNING;
                    serverStartStop.setText("Stop");
                }else{
                    serverConnection.stopConnection();
                    serverState = ServerState.STOPPED;
                    serverStartStop.setText("Start");
                }
            }
        });
        StackPane root = new StackPane(serverStartStop);
        Scene scene = new Scene(root, 600, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}