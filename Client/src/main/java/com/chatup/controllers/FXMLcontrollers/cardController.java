package com.chatup.controllers.FXMLcontrollers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class cardController {

    @FXML
    private Text User_name_imenu;

    @FXML
    private Text content_id;

    @FXML
    private ImageView friend_image_menu;

    private String name;
    private String content;
    private Image userImage;
    public cardController (Image userImage, String content, String userName){
                this.name =userName;
                this.content = content;
                this.userImage = userImage;
    }
    public  void initialize(){
        content_id.setText(content);
        friend_image_menu.setImage(userImage);
        Circle circle = new Circle(20, 20, 20);
        friend_image_menu.setClip(circle);

        User_name_imenu.setText(name);
    }
}
