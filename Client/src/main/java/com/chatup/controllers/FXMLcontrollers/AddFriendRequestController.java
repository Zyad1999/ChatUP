package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.*;
import com.chatup.models.entities.Card;
import com.chatup.models.entities.FriendRequest;
import com.chatup.models.entities.GroupMembership;
import com.chatup.models.entities.User;
import com.chatup.models.enums.FriendRequestStatus;
import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
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
    String action;
    int chatId;

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

                                AddFriendRequestCardController cardController = new AddFriendRequestCardController(item.getUserName(), item.getPhoneNumber(), userImage, item.getId());
                                loader.setController(cardController);
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

    public AddFriendRequestController (String action, int chatId){
        this.action = action;
        this.chatId=chatId;
    }
    @FXML
    void addInListView(ActionEvent event) {
        String FriendPhone = friendNumberTxt.getText().trim();
        User friendData = UserServicesImpl.getUserServices().getFiendData(FriendPhone);
        System.out.println(friendData);

        if (friendData != null && !FriendPhone.equals(CurrentUserImp.getCurrentUser().getPhoneNumber()) && checkIfInCurrentList(friendData))
        {
            if(action.equals("addmember")&&!checkIfInList(friendData)){
                friendNumberTxt.setStyle("-fx-border-color: red; -fx-border-width: 1px");
                friendNumberTxt.setTooltip(hintText("This Phone Number isn't one of your Friends ,You need to add him first", errorImage("/images/error.png")));

            }else if(action.equals("addmember")&&!checkIfInCurrentGroupList(friendData)){
                friendNumberTxt.setStyle("-fx-border-color: red; -fx-border-width: 1px");
                friendNumberTxt.setTooltip(hintText("This Phone Number isn already existing in your group", errorImage("/images/error.png")));

            }
            else {
                invitationList.add(friendData);
                friendNumberTxt.setStyle("-fx-border-color: -fx-gray-color;");
            }
        }else if(FriendPhone.equals(CurrentUserImp.getCurrentUser().getPhoneNumber())){
            friendNumberTxt.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            friendNumberTxt.setTooltip(hintText("You can't enter your phone number", errorImage("/images/error.png")));

        }
        else if(!checkIfInCurrentList(friendData)){
            friendNumberTxt.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            friendNumberTxt.setTooltip(hintText("this Phone number is already existing below List ", errorImage("/images/error.png")));

        }else if(friendData==null){
            friendNumberTxt.setStyle("-fx-border-color: red; -fx-border-width: 1px");
            friendNumberTxt.setTooltip(hintText("this Phone number isn't a member with us in our application ", errorImage("/images/error.png")));

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
    public boolean checkIfInCurrentList( User member){
        for(User user: invitationList){
            if (user.getPhoneNumber().equals(member.getPhoneNumber())){
                return false;
            }
        }
        return true;
    }
    public boolean checkIfInCurrentGroupList( User member){
        for(User user: ListCoordinatorImpl.getListCoordinator().getGroupMembers(CurrentChat.getCurrentChat().getCurrentChatID())){
            if (user.getPhoneNumber().equals(member.getPhoneNumber())){
                return false;
            }
        }
        return true;
    }
    @FXML
    void sendInvitation(ActionEvent event) {
        if (action.equals("addfriend")){
            List<FriendRequest> friendRequestList = new ArrayList<>();
            for (User user : invitationList) {
                friendRequestList.add(new FriendRequest(CurrentUserImp.getCurrentUser().getId(), user.getId(), FriendRequestStatus.PENDING));
            }

            boolean result = UserServicesImpl.getUserServices().createFriendRequests(friendRequestList);
            System.out.println(result);
            ListCoordinatorImpl.getListCoordinator().updateOnlineFriends();
            ListCoordinatorImpl.getListCoordinator().updateOfflineFriends();
            ListCoordinatorImpl.getListCoordinator().updateFriendRequests();
        }else if(action.equals("addmember")){
            List<User> groupMembershipList = new ArrayList<>();
            for (User user : invitationList) {
                groupMembershipList.add(user);
            }
            GroupServicesImpl.getGroupService().addUsersToGroup(chatId,groupMembershipList);
            ListCoordinatorImpl.getListCoordinator().getGroupMembers(CurrentChat.getCurrentChat().getCurrentChatID());
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
    public boolean checkIfInList(User user){
        boolean inOnline = false;
        boolean isOffLine = false;
        for(Card card: ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends()){
            if (card.getCardID()== user.getId()){
                inOnline = true;
                System.out.println(inOnline +" online" );
               break;
            }
        }
        for(Card card: ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends()){
            if (card.getCardID()== user.getId()){
                isOffLine = true;
                System.out.println(isOffLine +" offline" );

                break;
            }
        }
      //  System.out.println(isOffLine || inOnline);
        return isOffLine || inOnline;
    }
    @FXML
    void closebtn(ActionEvent event) {
        Stage stage =(Stage) ((Node)event.getSource()).getScene().getWindow();

        stage.close();
    }

}
