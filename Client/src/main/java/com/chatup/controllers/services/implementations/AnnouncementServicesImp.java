package com.chatup.controllers.services.implementations;

import com.chatup.controllers.FXMLcontrollers.AnnouncementCardController;
import com.chatup.controllers.services.interfaces.AnnouncementServices;
import com.chatup.models.entities.Announcement;
import com.chatup.network.ServerConnection;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class AnnouncementServicesImp implements AnnouncementServices {
    private static AnnouncementServices announcementServicesService;

    private AnnouncementServicesImp() {
    }

    public static AnnouncementServices getAnnouncementService() {
        if (announcementServicesService == null)
            announcementServicesService = new AnnouncementServicesImp();
        return announcementServicesService;
    }

    @Override
    public List<Announcement> getAllAnnouncements() {
        try {
            return ServerConnection.getServer().getAllAnnouncememts();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public VBox getAnnouncementVBox() {
        List<Announcement> announcements = AnnouncementServicesImp.getAnnouncementService().getAllAnnouncements();
        FXMLLoader loader;
        VBox notification = new VBox();
        System.out.println("outside -----------------");
        for (Announcement announcement : announcements) {
            System.out.println(announcement.getTitle());
            loader = new FXMLLoader(getClass().getResource("/views/NotificationCard.fxml"));
            AnnouncementCardController announcementCardController = new AnnouncementCardController(announcement.getTitle(), announcement.getContent());
            loader.setController(announcementCardController);
            try {
                notification.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return notification;
    }

    @Override
    public HBox getAnnouncementHBox(Announcement announcement) {
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("/views/NotificationCard.fxml"));
        AnnouncementCardController announcementCardController = new AnnouncementCardController(announcement.getTitle(), announcement.getContent());
        loader.setController(announcementCardController);
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
