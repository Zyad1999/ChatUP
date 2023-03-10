package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.GroupServicesImpl;
import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.GroupChat;
import com.chatup.models.entities.User;
import com.chatup.utils.CardMapper;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Stage;
import javafx.util.Callback;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddGroupController implements Initializable {

    public static ObservableList<User> groupUsersList;
    File file;
    @FXML
    private ListView UsersList;
    @FXML
    private MFXButton addButton;
    @FXML
    private Circle groupImage;
    @FXML
    private TextField groupName;
    @FXML
    private TextField userNumber;
    private String groupUserName;
    private String groupUserNumber;
    private Image groupUserImage;
    private Path groupImagePath;
    private boolean enterImage=false;
    private Image groupImg;

    private static void prepareListView(ListView usersListView) {
        usersListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            public ListCell<User> call(ListView<User> param) {
                final Tooltip tooltip = new Tooltip();
                final ListCell<User> cell = new ListCell<User>() {
                    @Override
                    public void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item != null) {
                                Image userImage = new Image(new ByteArrayInputStream(item.getImg()), 30, 30, false, true);
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/addGroupUser.fxml"));
                                AddGroupUserController userController = new AddGroupUserController( item.getUserName(), item.getPhoneNumber(),userImage, item.getId());
                                loader.setController(userController);
                                try {
                                    setGraphic(loader.load());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                tooltip.setText(item.getUserName());
                                setTooltip(tooltip);
                            }
                        } else {
                            setText(null);
                            setGraphic(null);
                        }

                    }
                };
                cell.setStyle("-fx-background-color: #FFFFFF;");
                return cell;
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        groupUsersList = FXCollections.observableArrayList();
        UsersList.setItems(groupUsersList);
        prepareListView(UsersList);
        groupUserImage = new Image(getClass().getResourceAsStream("/images/default_profile_pic.jpg"));
        groupImage.setFill(new ImagePattern(groupUserImage));
    }

    @FXML
    void addButtonClicked(ActionEvent event) {
        String FriendPhone = userNumber.getText().trim();
        User friendData = UserServicesImpl.getUserServices().getFiendData(FriendPhone);
        if (friendData != null && !FriendPhone.equals(CurrentUserImp.getCurrentUser().getPhoneNumber()) && !checkIfInList(FriendPhone, groupUsersList))
            groupUsersList.add(friendData);
    }

    public boolean checkIfInList(String phone, List<User> list) {
        for (User user : list) {
            if (user.getPhoneNumber().equals(phone)) {
                return true;
            }
        }
        return false;
    }

    @FXML
    void profileImageHandler(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
        file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                byte[] fileContent = Files.readAllBytes(file.toPath());
                groupImg = new Image(file.toURI().toURL().toString());
                groupImagePath = file.toPath();
                System.out.println("image loaded -> " + file.toPath());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            groupImg = new Image(Objects.requireNonNull(AddGroupController.class.getResourceAsStream("/images/default_profile_pic.jpg")));

            try {
                groupImagePath =new File(AddGroupController.class.getResource("/images/default_profile_pic.jpg").toURI()).toPath();
                System.out.println(groupImagePath);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        groupImage.setFill(new ImagePattern(groupImg));
        enterImage =true;
    }

    @FXML
    void createGroupClicked(ActionEvent event) {
        byte[] image;
        try {
            if(!enterImage){
                groupImagePath =new File(AddGroupController.class.getResource("/images/default_profile_pic.jpg").toURI()).toPath();
            }
            image = Files.readAllBytes(groupImagePath);

        } catch (IOException|URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
        if(groupName.getText()==""){
            groupName.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            groupName.setTooltip(hintText("Please Provide A Group Title", errorImage("/images/error.png")));
        }
        else {
            GroupChat groupChat = new GroupChat(groupName.getText(), image);
            int id = GroupServicesImpl.getGroupService().createGroupChat(groupChat, observableToList(groupUsersList));
            System.out.println("Created Group Chat With Id: " + id);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    List<User> observableToList(ObservableList<User> list) {
        List<User> usersList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            usersList.add(list.get(i));
        }
        usersList.add(CurrentUserImp.getCurrentUser());
        return usersList;
    }

    @FXML
    public void closeButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
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
}
