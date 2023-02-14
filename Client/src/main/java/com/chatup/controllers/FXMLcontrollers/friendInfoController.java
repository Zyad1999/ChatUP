package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.models.entities.Card;
import com.chatup.models.entities.User;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;

public class friendInfoController {

    @FXML
    private HBox FriendSildeBar;

    @FXML
    private Text friendShowDataCountry;

    @FXML
    private Text friendShowDataEmail;

    @FXML
    private Circle friendShowDataImage;

    @FXML
    private Text friendShowDataName;

    @FXML
    private Text friendShowDataPhone;

    @FXML
    private Text friendShowDatabio;


    private User userInfo;

    public friendInfoController(User user){
        this.userInfo = user;
        System.out.println(user.getUserName()+ " "  +user.getId() );
    }
    public  void initialize(){
        friendShowDatabio.setText(userInfo.getBio());
        friendShowDataCountry.setText(userInfo.getCountry());
        friendShowDataName.setText(userInfo.getUserName());
        friendShowDataEmail.setText(userInfo.getEmail());
        Image userImage = new Image(new ByteArrayInputStream(userInfo.getImg()));
        friendShowDataImage.setFill(new ImagePattern(userImage));

    }
    @FXML
    void closeDecoratedButtonHandler(MouseEvent event) {

    }

    @FXML
    void maximizeDecoratedButtonHandler(MouseEvent event) {

    }

    @FXML
    void minimizeDecoratedButtonHandler(MouseEvent event) {

    }
}
