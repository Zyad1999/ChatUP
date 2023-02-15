package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.controllers.services.interfaces.CurrentUser;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

public class EditeProfileController {

    public static StringProperty username;
    public static StringProperty Email;
    public static StringProperty DB;
    public static StringProperty Country;
    public static StringProperty gender;
    public static StringProperty birthdate;
    public static StringProperty mode;
    @FXML
    private TextArea changeBio;
    @FXML
    private Circle chnageimage;
    @FXML
    private Label userName;
    @FXML
    private Label useEmail;
    @FXML
    private Label userGender;
    @FXML
    private Label usercountry;
    @FXML
    private Label modeTF;
    @FXML
    private Label userBD;
    @FXML
    private Label userHeaderName;

    public EditeProfileController() {

    }

    private void diableOldScene(MouseEvent event, Stage newStage) {
        Scene oldScene = ((Node) event.getSource()).getScene();
        oldScene.getRoot().setDisable(true);

        newStage.setOnHidden(event1 -> {
            oldScene.getRoot().setDisable(false);
        });
    }

    @FXML
    void ChangePassword(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditInfCard.fxml"));
        EditInfCardController cardController = new EditInfCardController("password");
        loader.setController(cardController);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            diableOldScene(event, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void changeUserStatus(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditInfCard.fxml"));
        EditInfCardController cardController = new EditInfCardController("mode");
        loader.setController(cardController);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            diableOldScene(event, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeCountry(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditInfCard.fxml"));
        EditInfCardController cardController = new EditInfCardController("country");
        loader.setController(cardController);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            diableOldScene(event, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeDateOfBirth(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditInfCard.fxml"));
        EditInfCardController cardController = new EditInfCardController("bd");
        loader.setController(cardController);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            diableOldScene(event, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeEmail(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditInfCard.fxml"));
        EditInfCardController cardController = new EditInfCardController("email");
        loader.setController(cardController);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            diableOldScene(event, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeGender(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditInfCard.fxml"));
        EditInfCardController cardController = new EditInfCardController("gender");
        loader.setController(cardController);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            diableOldScene(event, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void changeUserName(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditInfCard.fxml"));
        EditInfCardController cardController = new EditInfCardController("name");
        loader.setController(cardController);
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            diableOldScene(event, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void closeScene(MouseEvent event) {
        String bio = changeBio.getText();
        if (bio.length() > 0) {
            CurrentUserImp.getCurrentUser().setBio(bio);
        }
        UserServicesImpl.getUserServices().UpdateUser(CurrentUserImp.getCurrentUser());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void initialize() {
        System.out.println(CurrentUserImp.getCurrentUser().getUserName());
        changeBio.setText(CurrentUserImp.getCurrentUser().getBio());
        username = new SimpleStringProperty(CurrentUserImp.getCurrentUser().getUserName());
        Email = new SimpleStringProperty(CurrentUserImp.getCurrentUser().getEmail());
        DB = new SimpleStringProperty(CurrentUserImp.getCurrentUser().getBirthDate().toString());
        Country = new SimpleStringProperty(CurrentUserImp.getCurrentUser().getCountry());
        gender = new SimpleStringProperty(CurrentUserImp.getCurrentUser().getGender().toString());
        birthdate = new SimpleStringProperty(CurrentUserImp.getCurrentUser().getBirthDate().toString());
        mode = new SimpleStringProperty(CurrentUserImp.getCurrentUser().getMode().toString());

        userName.textProperty().bind(username);
        userHeaderName.textProperty().bind(username);
        usercountry.textProperty().bind(Country);
        userBD.textProperty().bind(birthdate);
        useEmail.textProperty().bind(Email);
        userGender.textProperty().bind(gender);
        modeTF.textProperty().bind(mode);
        Image userImage = new Image(new ByteArrayInputStream(CurrentUserImp.getCurrentUser().getImg()));
        chnageimage.setFill(new ImagePattern(userImage));
    }

    @FXML
    void profileImageHandler(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        File file = fileChooser.showOpenDialog(null);
        Image userImage = null;
        if (file != null) {
            try {
                byte[] fileContent = Files.readAllBytes(file.toPath());
                CurrentUserImp.getCurrentUser().setImg(fileContent);
                userImage = new Image(file.toURI().toURL().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            userImage = new Image(new ByteArrayInputStream(CurrentUserImp.getCurrentUser().getImg()));


        }
        chnageimage.setFill(new ImagePattern(userImage));
        System.out.println(CurrentUserImp.getCurrentUser().getId() + " " + CurrentUserImp.getCurrentUser().getPhoneNumber() + " " + CurrentUserImp.getCurrentUser().getImg());
        boolean result = UserServicesImpl.getUserServices().UpdateUserImage(CurrentUserImp.getCurrentUser().getId(), CurrentUserImp.getCurrentUser().getPhoneNumber(), CurrentUserImp.getCurrentUser().getImg());
        System.out.println(result);
    }
}
