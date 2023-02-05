package com.chatup.controllers.reposotories.interfaces;


import com.chatup.models.entities.Attachment;

import java.util.List;

public interface AttachmentRepoInl {
    public boolean deleteAttachment(int id) ;
    public int addAttachment(Attachment attachment);
    public Attachment getAttachment(int id );
    public boolean updateAttachmentt(Attachment attachment) ;
    public List<Attachment> getAllAttachment();
}
