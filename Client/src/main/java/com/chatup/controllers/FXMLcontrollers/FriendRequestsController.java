package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.ListCoordinatorImpl;
import com.chatup.models.entities.User;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class FriendRequestsController  implements Initializable {

    @FXML
    private ListView listView;

    @FXML
    private ScrollPane scrolpane_id;
    @FXML
    private MFXButton closeFriendRequestsScrene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listView.setItems(ListCoordinatorImpl.getListCoordinator().getAllUserFriendRequests());
        prepareListView(listView);
        closeFriendRequestsScrene.setOnAction(event ->{
            System.out.println("close");
            Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
            ListCoordinatorImpl.getListCoordinator().updateOnlineFriends();
            ListCoordinatorImpl.getListCoordinator().updateFriendRequests();
            stage.close();
        } );
    }
    private static void prepareListView(ListView cardsListView) {
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
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/friendRequestCard.fxml"));
                                friendRequestCardController friendRequestCardController = new friendRequestCardController(item.getUserName(), item.getPhoneNumber(), userImage,item.getId());
                                loader.setController(friendRequestCardController);
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
        cardsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {


            }
        });
    }


    @FXML
    void doneBtn(ActionEvent event) {
        Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();
        ListCoordinatorImpl.getListCoordinator().updateOnlineFriends();
        ListCoordinatorImpl.getListCoordinator().updateOfflineFriends();
        ListCoordinatorImpl.getListCoordinator().updateFriendRequests();
        stage.close();
    }


}
