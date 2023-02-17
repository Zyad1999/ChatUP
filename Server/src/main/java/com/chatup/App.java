package com.chatup;

import com.chatup.utils.StageManager;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        StageManager stageManager = StageManager.getInstance();
        stageManager.initStage(stage);
        stageManager.switchToOverview();
        stage.show();
    }
}