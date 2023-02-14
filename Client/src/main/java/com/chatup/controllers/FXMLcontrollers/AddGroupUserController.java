package com.chatup.controllers.FXMLcontrollers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.net.URL;
import java.util.ResourceBundle;

public class AddGroupUserController implements Initializable {

    @FXML
    private ImageView deleteImg;

    @FXML
    private ImageView userImg;

    @FXML
    private Label userName;

    @FXML
    private Label userNumber;


    private String groupUserName;

    private String groupUserNumber;

    private Image groupUserImg;

    private int userId;

    public AddGroupUserController(String groupUserName, String groupUserNumber, Image groupUserImg,int id) {
        this.groupUserName = groupUserName;
        this.groupUserNumber = groupUserNumber;
        this.groupUserImg = groupUserImg;
        this.userId=id;
    }

    @FXML
    public void deleteImgClicked(ActionEvent event) {
        for(int i=0; i<AddGroupController.groupUsersList.size();i++) {
            if(AddGroupController.groupUsersList.get(i).getId()== userId) {
                int deletedId=i;
                Platform.runLater(() -> AddGroupController.groupUsersList.remove(deletedId,deletedId+1) );
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userImg.setImage(this.groupUserImg);
        userName.setText(this.groupUserName);
        userNumber.setText(this.groupUserNumber);
    }


}
