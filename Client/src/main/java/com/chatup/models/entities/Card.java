package com.chatup.models.entities;

import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.enums.CardType;

public class Card {
    private int cardID;
    private String cardName;
    private String cardContent;

    private CardType cardType;
    private byte[] cardImg;
    public Card(){}

    public Card(int cardID, String cardName, String cardContent, CardType cardType, byte[] cardImg) {
        this.cardID = cardID;
        this.cardName = cardName;
        this.cardContent = cardContent;
        this.cardType = cardType;
        this.cardImg = cardImg;
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

    public CardType getCardType() { return cardType; }

    public void setCardType(CardType cardType) { this.cardType = cardType; }
}
