package com.chatup.controllers.FXMLcontrollers;

import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInSecondSceneController implements Initializable {
    @FXML
    private Circle SignUp_img;
    @FXML
    private Button Sign_In;

    @FXML
    private MFXPasswordField passwordTF;
    @FXML
    private MFXTextField phoneNumberTF;
    private boolean valid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image userImage = new Image(getClass().getResourceAsStream("/images/avatar1.png"));
        SignUp_img.setFill(new ImagePattern(userImage));
        Sign_In.setDisable(true);
    }

    @FXML
    void Exit_Clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void Login_Clicked(ActionEvent event) {
    }

    @FXML
    void SignIn_Clicked(ActionEvent event) {
        validatePassword();
        if (valid) {
            try {
                SwitchScenes.getInstance().switchToChatScreen(event);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void Sign_In_Clicked(ActionEvent event) {
        try {
            SwitchScenes.getInstance().switchToSignInFirst(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Sign_Up_Clicked(ActionEvent event) {
        try {
            SwitchScenes.getInstance().switchToSignUpFirst(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Login_Clicked(MouseEvent event) {
        ActionEvent e = new ActionEvent(event.getSource(), event.getTarget());
        try {
            SwitchScenes.getInstance().switchToSignInFirst(e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private ImageView errorImage(String pathImage) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(pathImage)));
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        return imageView;
    }

    private Tooltip hintText(String text, ImageView image) {
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-font: normal reqular 11pt 'Times New Roman'; -fx-background-color: rgba(241,241,241,1); -fx-text-fill: black; -fx-background-radius: 4; -fx-border-radius: 4; -fx-opacity: 1.0;");
        tooltip.setAutoHide(false);
        tooltip.setMaxWidth(300);
        tooltip.setWrapText(true);
        tooltip.setText(text);
        tooltip.setGraphic(image);
        return tooltip;
    }

    private void validatePassword() {
        if (passwordTF.getText().length() == 0) {
            passwordTF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            passwordTF.setTooltip(hintText("Password is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else if (passwordTF.getText().length() < 8) {
            passwordTF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            passwordTF.setTooltip(hintText("The phone number or password is incorrect.", errorImage("/images/error.png")));
            valid = false;
        } else {
            passwordTF.setStyle("-fx-border-color: -fx-gray-color;");
            valid = true;
        }
    }
}
