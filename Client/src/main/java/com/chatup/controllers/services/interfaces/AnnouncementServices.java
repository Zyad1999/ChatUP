package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Announcement;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public interface AnnouncementServices {
    List<Announcement> getAllAnnouncements();
    VBox getAnnouncementVBox();
    HBox getAnnouncementHBox(Announcement announcement);
}
