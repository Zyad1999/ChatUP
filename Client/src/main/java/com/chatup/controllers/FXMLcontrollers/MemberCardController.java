package com.chatup.controllers.FXMLcontrollers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;


public class MemberCardController {
    private String phone;
    private Image image;
    @FXML
    private Circle memberImage;

    @FXML
    private Text memberPhone;
    public MemberCardController(Image userImage,String phone){
        this.image=userImage;
        this.phone= phone;
    }
    public  void initialize(){
        memberPhone.setText(phone);
        memberImage.setFill(new ImagePattern(image));

    }
}
