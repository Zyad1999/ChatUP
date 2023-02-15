package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.network.ServerConnection;
import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class SignUpSecondSceneController implements Initializable {

    @FXML
    private MFXButton SignUp_Finish;

    @FXML
    private Circle profileImage;

    @FXML
    private TextArea bioTextArea;
    private Image userImage;

    private boolean enterImage = false;

    private Path profileImgPath;

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            userImage = new Image(SignInSecondSceneController.class.getResourceAsStream("/images/default_profile_pic.jpg"));
        }
        profileImage.setFill(new ImagePattern(userImage));
        enterImage = true;
    }

    @FXML
    void SignUp_Finish(ActionEvent event) {
        try {
            byte[] image;
            try {
                if (!enterImage) {
                    profileImgPath = new File(AddGroupController.class.getResource("/images/default_profile_pic.jpg").toURI()).toPath();
                }
                image = Files.readAllBytes(profileImgPath);
                CurrentUserImp.getCurrentUser().setImg(image);

            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
            CurrentUserImp.getCurrentUser().setBio(bioTextArea.getText());
            System.out.println("img is : " + CurrentUserImp.getCurrentUser().getImg());
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

    @FXML
    void minimizeButtonHandler(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
