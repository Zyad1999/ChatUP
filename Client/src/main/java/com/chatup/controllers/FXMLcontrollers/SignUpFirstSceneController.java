package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.User;
import com.chatup.models.enums.Gender;
import com.chatup.models.enums.UserMode;
import com.chatup.models.enums.UserStatus;
import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class SignUpFirstSceneController implements Initializable {

    private boolean valid;
    @FXML
    private MFXLegacyComboBox<String> countryComboBox1;

    @FXML
    private DatePicker dateOfBirthDP;

    @FXML
    private ImageView exitButton;

    @FXML
    private MFXPasswordField confirmPasswordPF;

    @FXML
    private MFXTextField emailTF;

    @FXML
    private MFXLegacyComboBox<String> genderComboBox;

    @FXML
    private MFXButton nextButton;

    @FXML
    private MFXPasswordField passwordPF;

    @FXML
    private MFXTextField phoneNumberTF;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private MFXTextField userNameTF;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderComboBox.getItems().addAll("MALE", "FEMALE");
        countryComboBox1.getItems().addAll("Egypt", "Morocco", "Kuwait", "Palestinian", "Qatar", "Other");
        signUpButton.setDisable(true);
        dateOfBirthDP.setValue(LocalDate.of(LocalDate.now().getYear() - 12, 5, 23));
    }

    @FXML
    void Exit_Clicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void Gender_Options(ActionEvent event) {

    }

    @FXML
    void nextButtonHandler(ActionEvent event) {
        validateSignUp();
        if (valid) {
            try {
                User user = getUserData();
                System.out.println("successfully register: " + user.toString());
                CurrentUserImp.setCurrentUser(user);
                SwitchScenes.getInstance().switchToSignUpSecond(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void signInButtonHandler(ActionEvent event) {
        try {
            SwitchScenes.getInstance().switchToSignInFirst(event);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void signUpButtonHandler(ActionEvent event) {

    }

    @FXML
    public void Cender_Options(ActionEvent actionEvent) {
    }

    private User getUserData() {
        String username = userNameTF.getText();
        String phoneNumber = phoneNumberTF.getText();
        String email = emailTF.getText();
        String gender = genderComboBox.getValue();
        String password = passwordPF.getText();
        Date dateOfBirth = Date.from((dateOfBirthDP.getValue()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String country = countryComboBox1.getValue();
        return new User.Builder(phoneNumber, username, password).email(email).gnder(Gender.valueOf(gender)).birthDate(dateOfBirth).country(country).mode(UserMode.AVAILABLE).status(UserStatus.OFFLINE).build();
    }

    private void validateSignUp() {
        valid = true;
        validatePhoneNumber();
        validateUserName();
        validateEmail();
        validatePassword();
        validateConfirmPassword();
        validateDatePicker();
        validateGenderComboBox();
        validateCountryComboBox();
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

    private void validateUserName() {
        if (userNameTF.getText().length() == 0) {
            userNameTF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            userNameTF.setTooltip(hintText("Username is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else if (!userNameTF.getText().matches("^[a-zA-Z]{3,}(?: [a-zA-Z]+){0,2}$")) {
            userNameTF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            userNameTF.setTooltip(hintText("Invalid Name\nExample: Ahmed Mohamed", errorImage("/images/error.png")));
            valid = false;
        } else {
            userNameTF.setStyle("-fx-border-color: -fx-gray-color;");
//            valid = true;
        }
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
        } else if (UserServicesImpl.getUserServices().getUser(phoneNumberTF.getText()) != null) {
            phoneNumberTF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            phoneNumberTF.setTooltip(hintText("Phone already exist", errorImage("/images/error.png")));
            valid = false;
        } else {
            phoneNumberTF.setStyle("-fx-border-color: -fx-gray-color;");
//            valid = true;
        }
    }

    private void validateEmail() {
        if (emailTF.getText().length() == 0) {
            emailTF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            emailTF.setTooltip(hintText("Phone Number is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else if (!emailTF.getText().matches("^\\w+[_]?\\w+@[a-z]+\\.[a-z]{2,3}")) {
            emailTF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            emailTF.setTooltip(hintText("Invalid Email\nExample: example@example.com", errorImage("/images/error.png")));
            valid = false;
        } else {
            emailTF.setStyle("-fx-border-color: -fx-gray-color;");
//            valid = true;
        }
    }

    private void validatePassword() {
        if (passwordPF.getText().length() == 0) {
            passwordPF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            passwordPF.setTooltip(hintText("Password is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else if (passwordPF.getText().length() < 8) {
            passwordPF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            passwordPF.setTooltip(hintText("Invalid Password - At least 8 characters are required", errorImage("/images/error.png")));
            valid = false;
        } else {
            passwordPF.setStyle("-fx-border-color: -fx-gray-color;");
//            valid = true;
        }
    }

    private void validateConfirmPassword() {
        if (confirmPasswordPF.getText().length() == 0) {
            confirmPasswordPF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            confirmPasswordPF.setTooltip(hintText("Confirmation Password is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else if (!confirmPasswordPF.getText().equals(passwordPF.getText())) {
            confirmPasswordPF.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            confirmPasswordPF.setTooltip(hintText("Passwords don't match", errorImage("/images/error.png")));
            valid = false;
        } else {
            confirmPasswordPF.setStyle("-fx-border-color: -fx-gray-color;");
//            valid = true;
        }
    }

    private void validateDatePicker() {
        if (dateOfBirthDP.getValue() == null) {
            dateOfBirthDP.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            dateOfBirthDP.setTooltip(hintText("Date is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else if (dateOfBirthDP.getValue().getYear() >= 2012) {
            dateOfBirthDP.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            dateOfBirthDP.setTooltip(hintText("You must be at least twelve years old.", errorImage("/images/error.png")));
            valid = false;
        } else {
            dateOfBirthDP.setStyle("-fx-border-color: -fx-gray-color;");
//            valid = true;
        }
    }

    private void validateGenderComboBox() {
        if (genderComboBox.getSelectionModel().isEmpty()) {
            genderComboBox.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            genderComboBox.setTooltip(hintText("Gender is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else {
            genderComboBox.setStyle("-fx-border-color: -fx-gray-color;");
//            valid = true;
        }
    }

    private void validateCountryComboBox() {
        if (countryComboBox1.getSelectionModel().isEmpty()) {
            countryComboBox1.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            countryComboBox1.setTooltip(hintText("Country is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else {
            countryComboBox1.setStyle("-fx-border-color: -fx-gray-color;");
//            valid = true;
        }
    }
}
