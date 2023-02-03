package com.chatup.controllers.FXMLcontrollers;

import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SignUpFirstSceneController {

    @FXML
    private MFXPasswordField SignUp_Confirm_PSS;

    @FXML
    private MFXTextField SignUp_Country;

    @FXML
    private DatePicker SignUp_DOB;

    @FXML
    private MFXTextField SignUp_Email;

    @FXML
    private MFXLegacyComboBox<?> SignUp_Gender;

    @FXML
    private MFXTextField SignUp_Name;

    @FXML
    private MFXTextField SignUp_PN;

    @FXML
    private MFXPasswordField SignUp_PSS;

    @FXML
    private MFXButton Sign_Up;

    @FXML
    void SignUp_Gender_Select(ActionEvent event) {

    }

    @FXML
    void Exit_Clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void SignIn_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.switchToSignInFirst(event);
    }

    @FXML
    void SignUp_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.switchToSignUpSecond(event);
    }


    @FXML
    void Sign_Up_Clicked(ActionEvent event) throws IOException {
        SwitchScenes.switchToSignUpSecond(event);

    }

}
