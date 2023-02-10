package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Announcement;

import java.util.List;

public interface AnnouncementService {
    Announcement getAnnouncement(int announcement_id);
    List<Announcement> getAllAnnouncement();
    int addAnnouncement(Announcement announcement);
}
