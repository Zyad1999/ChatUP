package com.chatup.models.entities;

import com.chatup.network.ServerConnection;
import com.chatup.network.interfaces.Server;

import java.rmi.RemoteException;

public class Card {
    private int cardID;
    private String cardName;
    private String cardContent;
    private byte[] cardImg;

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
}
