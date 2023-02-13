package com.chatup.network.implementations;

import com.chatup.controllers.services.implementations.ChatServicesImpl;
import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.GroupMessage;
import com.chatup.network.interfaces.Client;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {

    private static Client client;

    private ClientImpl() throws RemoteException {}

    public static Client getClient(){
        try {
            if(client==null)
                client = new ClientImpl();
            return client;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendGroupMessage(GroupMessage message) throws RemoteException {
        System.out.println("Recived Group Message");
        Platform.runLater(()->{
            ListCoordinatorImpl.getListCoordinator().getGroupChatVbox(message.getGroupChatId()).getChildren()
                    .add(ChatServicesImpl.getChatService().recGroupMessage(message));
            ChatServicesImpl.getChatService().updateGroupChatList(message.getGroupMessageId(),message.getContent());
        });
    }

    @Override
    public void sendChatMessage(ChatMessage message) throws RemoteException {
        System.out.println("Received Chat message");
        Platform.runLater(()->{
            ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(message.getChatId()).getChildren()
                    .add(ChatServicesImpl.getChatService().recChatMessage(message));
            ChatServicesImpl.getChatService().updateChatList(message.getChatId(),message.getContent());
        });
    }
}
