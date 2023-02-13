package com.chatup.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class StageManager {
    private static final StageManager instance = new StageManager();
    private final Map<String, Scene> scenes = new HashMap<>();
    private Stage primaryStage;

    public static StageManager getInstance() {
        return instance;
    }

    public void initStage(Stage stage) {
        if (primaryStage != null) {
            throw new IllegalArgumentException("The Stage Already been initialized");
        }
        primaryStage = stage;
    }

    public void switchToOverview() {
        loadView("OverviewDashboard");
    }
    public void switchToStatistics() {
        loadView("StatisticsDashboard");
    }
    public void switchToAnnouncement() {
        loadView("AnnouncementDashboard");
    }

    public void loadView(String name) {
        if (primaryStage == null) {
            throw new RuntimeException("Stage Coordinator should be initialized with a Stage before it could be used");
        }

        if (!scenes.containsKey(name)) {
            try {
                System.out.println("Created New Scene");
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(String.format("/views/%s.fxml", name)));
                Scene dashboardServer = new Scene(fxmlLoader.load());
                scenes.put(name, dashboardServer);
                primaryStage.setScene(dashboardServer);
            } catch (IOException e) {
//                System.out.println(String.format("IO Exception: Couldn't load %s FXML file", viewName));
                e.printStackTrace();
            }
        } else {
            System.out.println("Loaded Existing Scene");
            primaryStage.setScene(scenes.get(name));
        }
        System.out.println("loaded " + name);
    }
}
