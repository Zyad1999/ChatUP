package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.models.entities.FriendRequest;
import com.chatup.models.enums.FriendRequestStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

public class friendRequestCardController  {

    @FXML
    private ImageView friendImage;

    @FXML
    private Label friendName;
    @FXML
    private MFXButton acceptBtn;
    @FXML
    private MFXButton rejectBtn;
    @FXML
    private Label friendPhoneNumber;
    private final String name;
    private final String phone;
    private final  Image image;
    private final int  friendId;
    public friendRequestCardController (String friendName, String friendPhoneNumber, Image friendImage,int id){
        this.friendId=id;
        this.name = friendName;
        this.phone = friendPhoneNumber;
        this.image = friendImage ;
    }
    public  void initialize(){
        friendName.setText(name);
        friendImage.setImage(image);
        Circle circle = new Circle(25, 25, 25);
        friendImage.setClip(circle);

        friendPhoneNumber.setText(phone);
    }
    @FXML
    void acceptRequest(ActionEvent event) {
        acceptBtn.setDisable(true);
        rejectBtn.setDisable(false);
        FriendRequest friendRequest = new FriendRequest(friendId,CurrentUserImp.getCurrentUser().getId(), FriendRequestStatus.ACCEPTED);
        Boolean result = ListCoordinatorImpl.getListCoordinator().updatesUserFriendRequests(friendRequest);
        ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends();
        ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends();
        System.out.println(result);
    }

    @FXML
    void deleteRequest(ActionEvent event) {
        acceptBtn.setDisable(false);
        rejectBtn.setDisable(true);
        FriendRequest friendRequest = new FriendRequest(friendId,CurrentUserImp.getCurrentUser().getId(), FriendRequestStatus.BLOCKED);
        Boolean result = ListCoordinatorImpl.getListCoordinator().updatesUserFriendRequests(friendRequest);

    }

}
