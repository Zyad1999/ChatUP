package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.enums.Gender;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class EditInfCardController implements Initializable {

    String editBartxt;
    boolean valid = false;
    @FXML
    private MFXButton cancelbbtn;
    @FXML
    private MFXTextField editTextField;
    @FXML
    private MFXLegacyComboBox genderComboBox;
    @FXML
    private MFXLegacyComboBox countryComboBox1;
    @FXML
    private DatePicker dateOfBirthDP;
    @FXML
    private MFXButton nextButton1;
    @FXML
    private MFXPasswordField confirmPasswordPF;
    @FXML
    private MFXPasswordField passwordPF;
    @FXML
    private Label editBar;

    public EditInfCardController(String editbar) {
        this.editBartxt = editbar;
    }

    @FXML
    void phoneNumberEnterPressed(KeyEvent event) {

    }

    @FXML
    void cancelBtn(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveBtn(ActionEvent event) {
        if (editBartxt.equals("name")) {
            if (!editTextField.getText().equals("")) {
                CurrentUserImp.getCurrentUser().setUserName(editTextField.getText());
                EditeProfileController.username.set(editTextField.getText());
                valid = true;
            }
        } else if (editBartxt.equals("email") && validateEmail()) {
            if (!editTextField.getText().equals("")) {
                CurrentUserImp.getCurrentUser().setEmail(editTextField.getText());
                EditeProfileController.Email.set(editTextField.getText());
                valid = true;
            }
        } else if (editBartxt.equals("country")) {
            if (!countryComboBox1.getSelectionModel().isEmpty()) {
                CurrentUserImp.getCurrentUser().setCountry(countryComboBox1.getValue().toString());
                EditeProfileController.Country.set(countryComboBox1.getValue().toString());
                valid = true;
            }
        } else if (editBartxt.equals("gender")) {
            if (!genderComboBox.getSelectionModel().isEmpty()) {
                CurrentUserImp.getCurrentUser().setGender(Gender.valueOf(genderComboBox.getValue().toString()));
                EditeProfileController.gender.set(genderComboBox.getValue().toString());
                valid = true;
            }
        } else if (editBartxt.equals("bd")) {
            if (dateOfBirthDP.getValue().getYear() <= 2012 && dateOfBirthDP.getValue() != null) {
                CurrentUserImp.getCurrentUser().setBirthDate(Date.from((dateOfBirthDP.getValue()).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                EditeProfileController.birthdate.set(((dateOfBirthDP.getValue())).toString());
                valid = true;
            }
        } else if (editBartxt.equals("password") && validatePassword() && validateConfirmPassword() && !passwordPF.getText().equals("")) {
            UserServicesImpl.getUserServices().UpdateUserPassword(CurrentUserImp.getCurrentUser().getId(), passwordPF.getText());
            valid = true;
        }
        if (valid) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    void Gender_Options(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genderComboBox.getItems().addAll("MALE", "FEMALE");
        countryComboBox1.getItems().addAll("Egypt", "Morocco", "Kuwait", "Palestinian", "Qatar", "Other");
        if (editBartxt.equals("name")) {
            editBar.setText("Enter your name");
            editTextField.setPromptText("Name");
            editTextField.setVisible(true);
            genderComboBox.setVisible(false);
            dateOfBirthDP.setVisible(false);
            countryComboBox1.setVisible(false);
            passwordPF.setVisible(false);
            confirmPasswordPF.setVisible(false);
        } else if (editBartxt.equals("email")) {
            editBar.setText("Enter your Email");
            editTextField.setPromptText("Email");

            editTextField.setVisible(true);
            genderComboBox.setVisible(false);
            dateOfBirthDP.setVisible(false);
            countryComboBox1.setVisible(false);
            passwordPF.setVisible(false);
            confirmPasswordPF.setVisible(false);
        } else if (editBartxt.equals("country")) {
            editBar.setText("Select your Country");
            editTextField.setVisible(false);
            genderComboBox.setVisible(false);
            dateOfBirthDP.setVisible(false);
            countryComboBox1.setVisible(true);
            passwordPF.setVisible(false);
            confirmPasswordPF.setVisible(false);
        } else if (editBartxt.equals("gender")) {
            editTextField.setVisible(false);
            genderComboBox.setVisible(true);
            dateOfBirthDP.setVisible(false);
            countryComboBox1.setVisible(false);
            passwordPF.setVisible(false);
            confirmPasswordPF.setVisible(false);
            editBar.setText("Select Gender");
        } else if (editBartxt.equals("bd")) {
            editTextField.setVisible(false);
            genderComboBox.setVisible(false);
            dateOfBirthDP.setVisible(true);
            countryComboBox1.setVisible(false);
            passwordPF.setVisible(false);
            confirmPasswordPF.setVisible(false);
            editBar.setText("Select your BirthDate");

        } else if (editBartxt.equals("password")) {
            editTextField.setVisible(false);
            genderComboBox.setVisible(false);
            dateOfBirthDP.setVisible(false);
            countryComboBox1.setVisible(false);
            passwordPF.setVisible(true);
            confirmPasswordPF.setVisible(true);
            editBar.setText("Enter new password");
        }


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

    private ImageView errorImage(String pathImage) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(pathImage)));
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        return imageView;
    }

    private boolean validateEmail() {
        if (editTextField.getText().length() == 0) {
            editTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            editTextField.setTooltip(hintText("Phone Number is required", errorImage("/images/question-mark.png")));
            valid = false;
        } else if (!editTextField.getText().matches("^\\w+[_]?\\w+@[a-z]+\\.[a-z]{2,3}")) {
            editTextField.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            editTextField.setTooltip(hintText("Invalid Email\nExample: example@example.com", errorImage("/images/error.png")));
            valid = false;
        } else {
            editTextField.setStyle("-fx-border-color: -fx-gray-color;");
            valid = true;
        }
        return valid;
    }

    private boolean validatePassword() {
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
            valid = true;
        }
        return valid;
    }

    private boolean validateConfirmPassword() {
        System.out.println("confirm");
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
            valid = true;
        }
        return valid;
    }
}
