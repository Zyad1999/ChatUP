package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.network.ServerConnection;
import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ResourceBundle;

public class SignUpSecondSceneController implements Initializable {

    @FXML
    private MFXButton SignUp_Finish;

    @FXML
    private Circle profileImage;

    @FXML
    private TextArea bioTextArea;
    private Image userImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userImage = new Image(getClass().getResourceAsStream("/images/default_profile_pic.jpg"));
        profileImage.setFill(new ImagePattern(userImage));
    }

    @FXML
    void profileImageHandler(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                byte[] fileContent = Files.readAllBytes(file.toPath());
                CurrentUserImp.getCurrentUser().setImg(fileContent);
                userImage = new Image(file.toURI().toURL().toString());
                System.out.println("image loaded -> " + file.toPath());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            userImage = new Image(SignInSecondSceneController.class.getResourceAsStream("/images/default_profile_pic.jpg"));
        }
        profileImage.setFill(new ImagePattern(userImage));
    }

    @FXML
    void SignUp_Finish(ActionEvent event) {
        try {
            CurrentUserImp.getCurrentUser().setBio(bioTextArea.getText());
            ServerConnection.getServer().signup(CurrentUserImp.getCurrentUser());
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
