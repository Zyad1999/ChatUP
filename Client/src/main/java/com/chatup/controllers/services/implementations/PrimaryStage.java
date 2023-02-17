package com.chatup.controllers.services.implementations;

import javafx.stage.Stage;

public class PrimaryStage {

    private Stage stage;

    private static PrimaryStage primaryStage;

    private PrimaryStage(){}

    public static PrimaryStage getInstance(){
        if (primaryStage == null)
            primaryStage = new PrimaryStage();
        return primaryStage;
    }

    public Stage getPrimaryStage(){
        if(stage == null)
            throw new RuntimeException("There is no primary stage");
        return stage;
    }

    public void setPrimaryStage(Stage stage){
        this.stage = stage;
    }
}
