package com.chatup.controllers.services.implementations;

import com.chatup.controllers.reposotories.implementations.AttachmentRepoImp;
import com.chatup.controllers.services.interfaces.AttachmentServices;
import com.chatup.models.entities.Attachment;

public class AttachmentServicesImpl implements AttachmentServices {

    private static AttachmentServices attachmentServices;

    public static AttachmentServices getAttachmentService(){
        if (attachmentServices == null)
            attachmentServices = new AttachmentServicesImpl();
        return attachmentServices;
    }

    @Override
    public Attachment getAttachment(int attachmentID){
        return AttachmentRepoImp.getInstance().getAttachment(attachmentID);
    }

    @Override
    public int addAttachment(Attachment attachment){
        return AttachmentRepoImp.getInstance().addAttachment(attachment);
    }
}
