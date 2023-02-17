package com.chatup.models.entities;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Announcement implements Serializable {
    @Serial
    private static final long serialVersionUID = -558553967080513792L;
    private int id;
    private String title;
    private String content ;
    private LocalDateTime date;



    public Announcement( String title, String content, LocalDateTime date) {

        this.title = title;
        this.content = content;
        this.date = date;
    }
    public Announcement(int id, String title, String content, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getData() {
        return date;
    }



    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setData(LocalDateTime date) {
        this.date = date;
    }
}

