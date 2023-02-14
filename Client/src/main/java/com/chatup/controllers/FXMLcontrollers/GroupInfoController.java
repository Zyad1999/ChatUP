package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.*;
import com.chatup.models.entities.Card;
import com.chatup.models.entities.Chat;
import com.chatup.models.entities.User;
import com.chatup.models.enums.CardType;
import com.chatup.network.ServerConnection;
import com.chatup.network.implementations.ClientImpl;
import javafx.collections.ObservableArray;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.rmi.RemoteException;

public class GroupInfoController {

    @FXML
    private HBox FriendSildeBar;

    @FXML
    private ListView groupMembersListView;

    @FXML
    private Circle groupShowDataImage;

    @FXML
    private Text groupShowDataName;
    private Card card;


    public GroupInfoController(Card card){
        this.card = card;
    }
    public  void initialize(){
        groupShowDataName.setText(card.getCardName());
        Image userImage = new Image(new ByteArrayInputStream(card.getCardImg()));
        groupShowDataImage.setFill(new ImagePattern(userImage));
        prepareListView(groupMembersListView);
        groupMembersListView.setItems(ListCoordinatorImpl.getListCoordinator().getGroupMembers(card.getCardID()));

    }
    private  void prepareListView(ListView cardsListView) {
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

                                MemberCardController memberCardController = new MemberCardController(userImage, item.getPhoneNumber());
                                loader.setController(memberCardController);
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
                cell.setStyle("-fx-background-color: #F4F4F4;");
                return cell;
            }
        });

    }

    @FXML
    void closeDecoratedButtonHandler(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to exit?", ButtonType.YES, ButtonType.NO);
        alert.setHeaderText(null);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            try {
                ServerConnection.getServer().logout(CurrentUserImp.getCurrentUser().getId(), ClientImpl.getClient());
                System.out.println("logout successfully");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            System.exit(1);
        }

    }

    @FXML
    void maximizeDecoratedButtonHandler(MouseEvent event) {

    }

    @FXML
    void minimizeDecoratedButtonHandler(MouseEvent event) {

    }

}
