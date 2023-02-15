package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.UserAuthImp;
import com.chatup.utils.RememberSetting;
import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignInSecondSceneController implements Initializable {
    @FXML
    public Hyperlink LoginButton;
    @FXML
    public MFXButton SignInButton;
    @FXML
    public Button Sign_Up;
    @FXML
    public ImageView Exit;
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
        Image userImage = new Image(new ByteArrayInputStream(CurrentUserImp.getCurrentUser().getImg()));
        SignUp_img.setFill(new ImagePattern(userImage));
        Sign_In.setDisable(true);
        phoneNumberTF.setText(CurrentUserImp.getCurrentUser().getPhoneNumber());
    }

    @FXML
    void exitClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void signInClicked(ActionEvent event) {
        signIn(event);
    }

    @FXML
    void signUpClicked(ActionEvent event) {
        try {
            SwitchScenes.getInstance().switchToSignUpFirst(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loginHyperlinkClicked(ActionEvent event) {
        try {
            SwitchScenes.getInstance().switchToSignInFirst(event);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void passwordEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            signIn(event);
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
        } else {
            passwordTF.setStyle("-fx-border-color: -fx-gray-color;");
            valid = true;
        }
    }

    public void setPhoneNumberTF(String phone) {
        phoneNumberTF.setText(phone);
    }

    public void signIn(Event event) {
        validatePassword();
        if (valid) {
            try {
                if (UserAuthImp.getUserAuth().loginAuth(phoneNumberTF.getText(), passwordTF.getText()) != null) {
                    System.out.println("corrected information");
                    RememberSetting.setProperties(phoneNumberTF.getText(), passwordTF.getText());
                    SwitchScenes.getInstance().switchToChatScreen(event);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Password is incorrect!");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                    System.out.println("wrong information");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    void minimizeButtonHandler(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
