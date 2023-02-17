package com.chatup.network;

import com.chatup.App;
import com.chatup.controllers.services.implementations.CurrentChat;
import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.controllers.services.implementations.PrimaryStage;
import com.chatup.network.implementations.ClientImpl;
import com.chatup.network.interfaces.Client;
import com.chatup.network.interfaces.Server;
import com.chatup.utils.SwitchScenes;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServerConnection {

    private static Server server;
    private final static String serverURI = "rmi://localhost:1900/ChatUPServer";

    private ServerConnection(){}

    public static Server getServer(){
        try {
            if(server == null) {
                server = (Server) Naming.lookup(serverURI);
            }
            server.ping();
            return server;
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Server Disconnected");
                alert.setHeaderText(null);
                alert.showAndWait();
                CurrentChat.setCurrentChat(null);
                ListCoordinatorImpl.getListCoordinator().flushLists();
                try {
                    SwitchScenes.getInstance().switchToSignInSecond(PrimaryStage.getInstance().getPrimaryStage());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });
            return null;
        }
    }

    public static void disconnect(){
        server = null;
    }
}
