package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.models.entities.Card;
import com.chatup.models.enums.CardType;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatScreenController implements Initializable {

    @FXML
    private MFXButton user_chats_btn;
    @FXML
    private MFXButton friends_btn;
    @FXML
    private MFXButton group_btn;
    @FXML
    private MFXButton settings_btn;
    @FXML
    private MFXButton extract_menu_id;
    @FXML
    private TextField txt_ld_search;
    @FXML
    private Circle user_image_side_bar;
    @FXML
    private ListView cardsListView;

    @FXML
    private  ScrollPane scrollPane;
    private ObservableList<Card> currentList;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareListView(cardsListView,scrollPane);
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserChats());

    }
    @FXML
    void setChats(ActionEvent event) {

        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserChats());
    }

    @FXML
    void setFriends(ActionEvent event) {

        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends());
    }

    @FXML
    void setGroups(ActionEvent event) {


        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserGroups());
    }

    @FXML
    void signOut(ActionEvent event) {

    }

    @FXML
    void userNotifications(ActionEvent event) {

    }

    @FXML
    void userSettings(ActionEvent event) {

    }
    private static void prepareListView(ListView cardsListView, ScrollPane scrollPane){
        cardsListView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            public ListCell<Card> call(ListView<Card> param) {
                final Tooltip tooltip = new Tooltip();
                final ListCell<Card> cell = new ListCell<Card>() {
                    @Override
                    public void updateItem(Card item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty) {
                            if (item != null) {
                                Image userImage = new Image(new ByteArrayInputStream(item.getCardImg()), 30, 30, false, true);
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/card.fxml"));
                                cardController cardController = new cardController(userImage,item.getCardContent(),item.getCardName());
                                loader.setController(cardController);
                                try {
                                    setGraphic(loader.load());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                tooltip.setText(item.getCardContent());
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
        cardsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Card>() {
            public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card newValue) {
                System.out.println(oldValue);
                System.out.println(newValue);
                if (observable != null && observable.getValue() != null) {
                    System.out.println(newValue.getCardID());
                    if(newValue.getCardType()== CardType.CHAT){
                        VBox box = ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(newValue.getCardID());
                        scrollPane.setContent(box);
                    } else if (newValue.getCardType()==CardType.GROUP) {
                        VBox box = ListCoordinatorImpl.getListCoordinator().getGroupChatVbox(newValue.getCardID());
                        scrollPane.setContent(box);
                    }
                }
            }
        });
    }
}
