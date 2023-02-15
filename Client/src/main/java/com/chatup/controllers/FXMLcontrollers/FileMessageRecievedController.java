package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.FileServicesImpl;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class FileMessageRecievedController {

    @FXML
    private ImageView downloadButton;

    @FXML
    private Text fileName;

    @FXML
    private Text fileSize;

    @FXML
    private Text messageText;

    @FXML
    private Text senderName;

    private String name;
    private int size;
    private String message;
    private int attachmentID;
    private int senderID;

    private String sendName;

    @FXML
    void downloadButtonHandler(MouseEvent event) {
        Thread downloadThread = new Thread(()->{
            boolean downloaded = FileServicesImpl.getFileServices().downloadFile(attachmentID,senderID);
            Platform.runLater(()->{
                if(downloaded){
                    downloadButton.setVisible(false);
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "File download failed");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            });
        });
        downloadThread.start();
    }

    public FileMessageRecievedController(String name, String message, int size, int attachmentID, int senderID, String sendName){
        this.name = name;
        this.message = message;
        this.size = size;
        this.attachmentID = attachmentID;
        this.senderID = senderID;
        this.sendName = sendName;
    }

    public void initialize(){
        messageText.setText(message);
        fileSize.setText((size/1024/1024)+"MB");
        fileName.setText(name);
        senderName.setText(sendName);
        downloadButton.setVisible(!FileServicesImpl.getFileServices().checkIfFileExists(name,senderID));
    }

}
