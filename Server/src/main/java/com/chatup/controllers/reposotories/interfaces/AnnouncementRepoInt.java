package com.chatup.controllers.reposotories.interfaces;

import com.chatup.models.entities.Announcement;

import java.util.List;

public interface AnnouncementRepoInt {
    public boolean deleteAnnouncement(int id) ;
    public int createAnnouncement(Announcement announcement);
    public Announcement getAnnouncement(int id );
    public boolean updateAnnouncement(Announcement announcement);
    public List<Announcement> getAllAnnouncement();

}
