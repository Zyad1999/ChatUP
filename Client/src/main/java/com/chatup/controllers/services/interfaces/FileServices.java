package com.chatup.controllers.services.interfaces;

import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.GroupMessage;

import java.io.File;

public interface FileServices {
    boolean downloadFile(int attachmentID, int senderID);

    boolean checkIfFileExists(String fileName, int senderID);

    String getFileName(File file);

    String getFileExtension(File file);

    byte[] getFileBytes(File file);

    int sendChatFileToServer(File file, ChatMessage mess);

    int sendGroupFileToServer(File file, GroupMessage mess);
}
