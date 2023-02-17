package com.chatup.controllers.services.implementations;

import com.chatup.controllers.services.interfaces.FileServices;
import com.chatup.models.entities.Attachment;
import com.chatup.models.entities.ChatMessage;
import com.chatup.models.entities.GroupMessage;
import com.chatup.network.ServerConnection;

import java.io.*;
import java.rmi.RemoteException;

public class FileServicesImpl implements FileServices{

    private static FileServices fileServices;

    private FileServicesImpl(){}

    public static FileServices getFileServices(){
        if (fileServices == null)
            fileServices = new FileServicesImpl();
        return fileServices;
    }

    @Override
    public boolean downloadFile(int attachmentID, int senderID){
        try {
            byte [] mydata = ServerConnection.getServer().downloadAttachment(attachmentID, senderID);
            if(mydata == null) {
                System.out.println("file not found");
                return false;
            }
            System.out.println("downloading...");
            Attachment attachment = ChatServicesImpl.getChatService().getAttachment(attachmentID);
            File theDir = new File("./files/"+senderID);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            File clientpathfile = new File("./files/"+senderID+"/"+attachment.getAttachmentName()+"."+attachment.getExtension());
            FileOutputStream out=new FileOutputStream(clientpathfile);
            out.write(mydata);
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkIfFileExists(String fileName, int senderID){
        File file = new File("./files/"+senderID+"/"+fileName);
        if(file.exists()){
            return true;
        }
        return false;
    }

    @Override
    public String getFileName(File file){
        if(file != null && file.exists() && ! file.isDirectory()) {
            String fileString = file.toPath().getFileName().toString();
            return fileString.substring(0, fileString.lastIndexOf("."));
        }
        return null;
    }

    @Override
    public String getFileExtension(File file){
        if(file != null && file.exists() && ! file.isDirectory()) {
            String fileString = file.toPath().getFileName().toString();
            return fileString.substring(fileString.lastIndexOf(".") + 1);
        }
        return null;
    }

    @Override
    public byte[] getFileBytes(File file){
        if(file != null && file.exists() && ! file.isDirectory()) {
            try(FileInputStream in=new FileInputStream(file)) {
                byte [] fileBytes=new byte[(int) file.length()];
                in.read(fileBytes, 0, fileBytes.length);
                return fileBytes;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public int sendChatFileToServer(File file, ChatMessage mess){
        try {
            String fileName = FileServicesImpl.getFileServices().getFileName(file);
            String fileExtension = FileServicesImpl.getFileServices().getFileExtension(file);
            byte[] fileBytes = FileServicesImpl.getFileServices().getFileBytes(file);
            if(fileName != null && fileExtension != null && fileBytes != null) {
                int attachmentID = ServerConnection.getServer().uploadChatFileToServer(fileBytes, mess, new Attachment(fileName, fileExtension, (int) file.length()));
                if(attachmentID != -1)
                    return attachmentID;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int sendGroupFileToServer(File file, GroupMessage mess){
        try {
            String fileName = FileServicesImpl.getFileServices().getFileName(file);
            String fileExtension = FileServicesImpl.getFileServices().getFileExtension(file);
            byte[] fileBytes = FileServicesImpl.getFileServices().getFileBytes(file);
            if(fileName != null && fileExtension != null && fileBytes != null) {
                int attachmentID = ServerConnection.getServer().uploadGroupFileToServer(fileBytes, mess, new Attachment(fileName, fileExtension, (int) file.length()));
                if(attachmentID != -1)
                    return attachmentID;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
