package com.chatup.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.controlsfx.control.Notifications;

public class NotificationPopups {
    public static void receiveNotification(String title, String body, String pathImage) {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        imageView.setImage(new Image(NotificationPopups.class.getResourceAsStream(pathImage)));
        Notifications.create()
                .title(title)
                .text(body)
                .graphic(imageView)
                .threshold(3, Notifications.create().title("Collapsed Notification"))
                .show();
    }
}
