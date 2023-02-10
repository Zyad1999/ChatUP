package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.AnnouncementRepoImpl;
import com.chatup.controllers.services.interfaces.AnnouncementService;
import com.chatup.models.entities.Announcement;

import java.util.List;

public class AnnouncementServiceImp implements AnnouncementService {
    @Override
    public Announcement getAnnouncement(int announcement_id) {
        return AnnouncementRepoImpl.getInstance().getAnnouncement(announcement_id);
    }

    @Override
    public List<Announcement> getAllAnnouncement() {
        return AnnouncementRepoImpl.getInstance().getAllAnnouncement();
    }

    @Override
    public int addAnnouncement(Announcement announcement) {
        return AnnouncementRepoImpl.getInstance().createAnnouncement(announcement);
    }
}
