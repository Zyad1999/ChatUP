package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.CurrentUserImp;
import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.models.entities.Card;
import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.User;
import com.chatup.models.enums.FriendRequestStatus;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddFriendRequestController implements Initializable {
    public static ObservableList<User> invitationList;
    @FXML
    private TextField friendNumberTxt;
    @FXML
    private ListView listview;
    @FXML
    private HBox dragBar;

    private static void prepareListView(javafx.scene.control.ListView cardsListView) {
        cardsListView.setCellFactory(new Callback<javafx.scene.control.ListView<User>, ListCell<User>>() {
            public ListCell<User> call(javafx.scene.control.ListView<User> param) {
                final Tooltip tooltip = new Tooltip();
                final ListCell<User> cell = new ListCell<User>() {
                    @Override
                    public void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item != null) {
                                Image userImage = new Image(new ByteArrayInputStream(item.getImg()), 30, 30, false, true);
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddFriendCard.fxml"));
//                                AddFriendRequestCardController cardController = new AddFriendRequestCardController(item.getUserName(), item.getPhoneNumber(), userImage, item.getId());
//                                loader.setController(cardController);
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
        cardsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Card>() {
            public void changed(ObservableValue<? extends Card> observable, Card oldValue, Card newValue) {

            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        invitationList = FXCollections.observableArrayList();
        listview.setItems(invitationList);
        prepareListView(listview);
    }

    @FXML
    void addInListView(ActionEvent event) {
        String FriendPhone = friendNumberTxt.getText().trim();
        User friendData = UserServicesImpl.getUserServices().getFiendData(FriendPhone);
        if (friendData != null && !FriendPhone.equals(CurrentUserImp.getCurrentUser().getPhoneNumber()))
            invitationList.add(friendData);
    }

    @FXML
    void sendInvitation(ActionEvent event) {
        List<FriendRequest> friendRequestList = new ArrayList<>();
        for (User user : invitationList) {
            friendRequestList.add(new FriendRequest(CurrentUserImp.getCurrentUser().getId(), user.getId(), FriendRequestStatus.PENDING));
        }
        boolean result = UserServicesImpl.getUserServices().createFriendRequests(friendRequestList);
        System.out.println(result);
        ListCoordinatorImpl.getListCoordinator().updateOnlineFriends();
        ListCoordinatorImpl.getListCoordinator().updateOfflineFriends();
        ListCoordinatorImpl.getListCoordinator().updateFriendRequests();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void closeButton(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
