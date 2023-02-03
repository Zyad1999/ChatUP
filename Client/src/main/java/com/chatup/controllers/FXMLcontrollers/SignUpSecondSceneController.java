package com.chatup.controllers.FXMLcontrollers;

import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.io.IOException;

public class SignUpSecondSceneController {

    @FXML
    private MFXButton SignUp_Finish;

    @FXML
    private Circle SignUp_img;

    @FXML
    void SignUp_Finish(ActionEvent event) throws IOException {

        SwitchScenes.switchToSignInFirst(event);

    }

    @FXML
    void Exit_Clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void Back_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.switchToSignUpFirst(event);
    }


}
