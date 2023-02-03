package com.chatup.controllers.FXMLcontrollers;

import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SignInFirstSceneController {

    @FXML
    private ImageView Exit;

    @FXML
    private MFXButton Next;

    @FXML
    private MFXTextField SignIN_PN;

    @FXML
    private Button SignIn;

    @FXML
    private Button SignUp;

    @FXML
    void Exit_Clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void Next_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.getInstance().switchToSignInSecond(event);
    }

    @FXML
    void SignIn_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.getInstance().switchToSignInFirst(event);
    }

    @FXML
    void SignUp_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.getInstance().switchToSignUpFirst(event);
    }

}
