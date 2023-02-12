package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.User;
import com.chatup.network.ServerConnection;
import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInFirstSceneController implements Initializable {
    @FXML
    public MFXTextField phoneNumberTF;
    @FXML
    public MFXButton NextButton;
    @FXML
    public ImageView Exit;
    @FXML
    public Button SignIn;
    @FXML
    public Button SignUp;
    private boolean valid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SignIn.setDisable(true);
    }

    @FXML
    void Exit_Clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void Next_Clicked(ActionEvent event) {
        next(event);
    }

    @FXML
    void SignIn_Clicked(ActionEvent event) {
        try {
            SwitchScenes.getInstance().switchToSignInFirst(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SignUp_Clicked(ActionEvent event) {
        try {
            SwitchScenes.getInstance().switchToSignUpFirst(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void phoneNumberEnterPressed(KeyEvent event) {
        if(event.getCode()== KeyCode.ENTER){
            next(event);
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

    private void validatePhoneNumber() {
        if (phoneNumberTF.getText().length() == 0) {
            phoneNumberTF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            phoneNumberTF.setTooltip(hintText("Phone Number is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else if (!phoneNumberTF.getText().matches("^01[0125][0-9]{8}$")) {
            phoneNumberTF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            phoneNumberTF.setTooltip(hintText("Invalid Phone Number\nExample: 01xxxxxxxxx", errorImage("/images/error.png")));
            valid = false;
        } else {
            phoneNumberTF.setStyle("-fx-border-color: -fx-gray-color;");
            valid = true;
        }
    }

    private void next(Event event){
        validatePhoneNumber();
        if (valid) {
            try {
                User user = ServerConnection.getServer().getUser(phoneNumberTF.getText());
                if (user != null) {
                    System.out.println("phone number is correct");
                    CurrentUserImp.setCurrentUser(UserServicesImpl.getUserServices().getUser(phoneNumberTF.getText()));
                    SwitchScenes.getInstance().switchToSignInSecond(event);
                } else {
                    System.out.println("not found phone number");
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Phone number is incorrect!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            } catch (IOException e) {
                System.out.println("problem ya karim");
                e.printStackTrace();
            }
        }
    }
}
