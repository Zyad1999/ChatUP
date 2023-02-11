package com.chatup.models.entities;

import java.io.Serial;
import java.io.Serializable;

public class Attachment implements Serializable {
    @Serial
    private static final long serialVersionUID = -558553967080513793L;
    private int id;
    private String attachmentType;
    private String extension;
    private  int byteSize;
    private String location;

    public Attachment(int id, String attachmentType, String extension, int byteSize, String location) {
        this.id = id;
        this.attachmentType = attachmentType;
        this.extension = extension;
        this.byteSize = byteSize;
        this.location = location;
    }
    public Attachment(String attachmentType, String extension, int byteSize, String location) {
        this.attachmentType = attachmentType;
        this.extension = extension;
        this.byteSize = byteSize;
        this.location = location;
    }

    public String getAttachmentType() {
        return attachmentType;
    }

    public void setAttachmentType(String attachmentType) {
        this.attachmentType = attachmentType;
    }



    public int getId() {
        return id;
    }



    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public int getByteSize() {
        return byteSize;
    }

    public void setByteSize(int byteSize) {
        this.byteSize = byteSize;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
