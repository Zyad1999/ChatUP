package com.chatup.controllers.FXMLcontrollers;

import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpSecondSceneController implements Initializable {

    @FXML
    private MFXButton SignUp_Finish;

    @FXML
    private Circle profileImage;
    private Image userImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userImage = new Image(getClass().getResourceAsStream("/images/avatar1.png"));
        profileImage.setFill(new ImagePattern(userImage));
    }

    @FXML
    void profileImageHandler(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                userImage = new Image(file.toURI().toURL().toString());
                System.out.println("image loaded -> " + file.toPath());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            userImage = new Image("/images/avatar1.png");
        }
        profileImage.setFill(new ImagePattern(userImage));
    }

    @FXML
    void SignUp_Finish(ActionEvent event) {
        try {
            SwitchScenes.getInstance().switchToSignInFirst(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Exit_Clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void Back_Clicked(ActionEvent event) {
        try {
            SwitchScenes.getInstance().switchToSignUpFirst(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
