package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentChat;
import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.GroupServicesImpl;
import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.models.entities.Card;
import com.chatup.models.entities.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

public class GroupInfoController {

    @FXML
    private HBox FriendSildeBar;
    @FXML
    private MFXButton addButton;
    @FXML
    private MFXButton leaveBtn;
    @FXML
    private ListView groupMembersListView;

    @FXML
    private Circle groupShowDataImage;

    @FXML
    private Text groupShowDataName;
    private Card card;


    public GroupInfoController(Card card) {
        this.card = card;
    }

    public void initialize() {
        groupShowDataName.setText(card.getCardName());
        Image userImage = new Image(new ByteArrayInputStream(card.getCardImg()));
        groupShowDataImage.setFill(new ImagePattern(userImage));
        prepareListView(groupMembersListView);
        groupMembersListView.setItems(ListCoordinatorImpl.getListCoordinator().getGroupMembers(card.getCardID()));

    }

    private void prepareListView(ListView cardsListView) {
        cardsListView.setCellFactory(new Callback<ListView<User>, ListCell<User>>() {
            public ListCell<User> call(ListView<User> param) {
                final Tooltip tooltip = new Tooltip();
                final ListCell<User> cell = new ListCell<User>() {
                    @Override
                    public void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item != null) {
                                Image userImage = new Image(new ByteArrayInputStream(item.getImg()), 30, 30, false, true);
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MemberCard.fxml"));

                                MemberCardController memberCardController = new MemberCardController(userImage, item.getUserName());
                                loader.setController(memberCardController);
                                try {
                                    setGraphic(loader.load());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                tooltip.setText(item.getPhoneNumber());
                                setTooltip(tooltip);
                            }
                        } else {
                            setText(null);
                            setGraphic(null);
                        }

                    }
                };
                cell.setStyle("-fx-background-color: #F4F4F4;");
                return cell;
            }
        });

    }


    @FXML
    void Addmember(ActionEvent event) {
        FXMLLoader loader;
        Scene scene = null;
        try {
            System.out.println("Current List= " + ListCoordinatorImpl.currentList);

            loader = new FXMLLoader(Objects.requireNonNull(ChatScreenController.class.getResource("/views/AddFriend.fxml")));
            AddFriendRequestController addFriendRequestController = new AddFriendRequestController("addmember", CurrentChat.getCurrentChat().getCurrentChatID());
            loader.setController(addFriendRequestController);
            scene = new Scene(loader.load(), 550, 550);
            Stage stage = new Stage(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            scene.getRoot().setStyle("-fx-background-radius: 20;");
            stage.setScene(scene);
            stage.show();
            Scene oldScene = ((Node) event.getSource()).getScene();
            oldScene.getRoot().setDisable(true);

            stage.setOnHidden(event1 -> {
                oldScene.getRoot().setDisable(false);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void leaveGroup(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to leave this group?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            boolean res = GroupServicesImpl.getGroupService().deleteMemberfromGroup(CurrentUserImp.getCurrentUser().getId(), CurrentChat.getCurrentChat().getCurrentChatID());
            ListCoordinatorImpl.getListCoordinator().getGroupMembers(CurrentChat.getCurrentChat().getCurrentChatID());
            ListCoordinatorImpl.getListCoordinator().updatesUserGroups();
            System.out.println("leave from group" + res);

        }
    }

}
