package com.chatup.models.entities;

import java.io.Serial;
import java.io.Serializable;

public class Attachment implements Serializable {
    @Serial
    private static final long serialVersionUID = -558553967080513793L;
    private int id;
    private String attachmentName;
    private String extension;
    private  int byteSize;
    public Attachment(int id, String attachmentName, String extension, int byteSize) {
        this.id = id;
        this.attachmentName = attachmentName;
        this.extension = extension;
        this.byteSize = byteSize;
    }
    public Attachment(String attachmentName, String extension, int byteSize) {
        this.attachmentName = attachmentName;
        this.extension = extension;
        this.byteSize = byteSize;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentType(String attachmentName) {
        this.attachmentName = attachmentName;
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

}