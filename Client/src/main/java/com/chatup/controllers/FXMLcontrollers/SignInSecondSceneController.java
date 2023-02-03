package com.chatup.controllers.FXMLcontrollers;

import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class SignInSecondSceneController {

    @FXML
    private ImageView Exit;

    @FXML
    private Label Login;

    @FXML
    private MFXTextField SignIN_PN;

    @FXML
    private MFXPasswordField SignIN_PSS;

    @FXML
    private MFXButton SignIn;

    @FXML
    private Circle SignUp_img;

    @FXML
    private Button Sign_In;

    @FXML
    private Button Sign_Up;

    @FXML
    void Exit_Clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void Login_Clicked(MouseEvent event) throws IOException {
        ActionEvent e = new ActionEvent(event.getSource(), event.getTarget());
        SwitchScenes.switchToSignInFirst(e);
    }

    @FXML
    void SignIn_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.switchToChatScreen(event);
    }

    @FXML
    void Sign_In_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.switchToSignInFirst(event);
    }

    @FXML
    void Sign_Up_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.switchToSignUpFirst(event);
    }

}
