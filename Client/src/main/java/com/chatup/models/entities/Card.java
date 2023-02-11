package com.chatup.models.entities;

import com.chatup.network.ServerConnection;
import com.chatup.network.interfaces.Server;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.Map;

public class Card {
    private int cardID;
    private String cardName;
    private String cardContent;
    private byte[] cardImg;
    public Card(){}
    public Card(User user){
        this.cardID = user.getId();
        this.cardContent = user.getBio();
        this.cardName = user.getUserName();
        this.cardImg = user.getImg();
    }

    public Card(Chat chat,ChatMessage message){
        this.cardID = chat.getId();
        this.cardContent = message.getContent();
        try {
            User user = ServerConnection.getServer().getUser(message.getSenderId());
            this.cardName = user.getUserName();
            this.cardImg = user.getImg();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public Card(GroupChat group,GroupMessage message){
        this.cardID = group.getGroupChatID();
        this.cardContent = message.getContent();
        this.cardImg = group.getGroupImage();
        this.cardName = group.getGroupTitle();
    }

    public int getCardID() {
        return cardID;
    }

    public void setCardID(int cardID) {
        this.cardID = cardID;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardContent() {
        return cardContent;
    }

    public void setCardContent(String cardContent) {
        this.cardContent = cardContent;
    }

    public byte[] getCardImg() {
        return cardImg;
    }

    public void setCardImg(byte[] cardImg) {
        this.cardImg = cardImg;
    }
    public ObservableList<Card> AlluserChats() throws RemoteException {
        Server server = ServerConnection.getServer();
        Map<Chat, ChatMessage> userChats = server.getUserChats(1);
        ObservableList<Card> cardList = FXCollections.<Card>observableArrayList();
        Card card = null;
        for (Map.Entry<Chat, ChatMessage> set : userChats.entrySet()) {
            card = new Card(set.getKey(), set.getValue());
            cardList.add(card);
        }
        return  cardList;
    }
}
