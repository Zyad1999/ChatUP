package com.chatup.controllers.FXMLcontrollers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class AddFriendRequestCardController {

    @FXML
    private ImageView friendImage;

    @FXML
    private Label friendNameId;

    @FXML
    private Label friendPhne;
    String friendName;
    String phone ;
    Image image;
    int id ;
    public AddFriendRequestCardController(String name, String phone, Image userImage,int id){
        this.friendName = name;
        this.phone = phone;
        this.image = userImage;
        this.id = id;
    }
    public  void initialize(){
        friendPhne.setText(phone);
        friendImage.setImage(image);
        Circle circle = new Circle(25,25,25);
        friendImage.setClip(circle);

        friendNameId.setText(friendName);
    }
    @FXML
    void deleteInvitation(ActionEvent event) {
        for(int i=0; i<AddFriendRequestController.invitationList.size();i++) {
            if(AddFriendRequestController.invitationList.get(i).getId()== id) {
                int deletedId=i;
               Platform.runLater(() -> AddFriendRequestController.invitationList.remove(deletedId,deletedId+1) );
            }
        }


    }
}
