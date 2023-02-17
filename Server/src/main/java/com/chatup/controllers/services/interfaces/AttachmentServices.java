package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.Attachment;

public interface AttachmentServices {
    Attachment getAttachment(int attachmentID);

    int addAttachment(Attachment attachment);
}
