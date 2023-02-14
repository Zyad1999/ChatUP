package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.*;

import com.chatup.models.entities.*;
import com.chatup.models.enums.CardType;
import com.chatup.models.enums.ChatType;
import com.chatup.network.ServerConnection;
import com.chatup.network.implementations.ClientImpl;
import com.chatup.utils.RememberSetting;
import com.chatup.utils.SwitchScenes;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatScreenController implements Initializable {
    private static StringProperty friendName;
    private static StringProperty friendStatus;
    private static StringProperty friendEmail;
    private static StringProperty friendPhone;
    private static StringProperty friendCountry;
    private static StringProperty friendBio;
    private static double xOffset = 0;
    private static double yOffset = 0;
    @FXML
    private HBox dragBar;
    private static Image friendImage;
    @FXML
    private Text friendShowDataCountry;

    @FXML
    private Text friendShowDataEmail;



    @FXML
    private Text friendShowDataName;

    @FXML
    private Text friendShowDataPhone;
    @FXML
    private Text friendShowDatabio;

    @FXML
    private Circle friendImageClose;

    @FXML
    private Circle friendImageOpen;

    @FXML
    private Text friendNameClose;

    @FXML
    private Text friendNameOpen;
    @FXML
    private Text frieendStatusClose;

    @FXML
    private Text frieendStatusOpen;

    @FXML
    private MFXButton closeExtraction;
    @FXML
    private HBox closeFrienDetailsbtn;
    @FXML
    private HBox showFriendDetailsbtn;
    @FXML
    private  HBox FriendSildeBar;
    @FXML
    private VBox slider;
    @FXML
    private AnchorPane anchorPanSlider;
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
    private MFXButton addButton;
    @FXML
    private Button sendButton;

    @FXML
    private TextField messageText;

    @FXML
    private HBox listBox;

    @FXML
    private MFXButton FriendRequests_id;

    @FXML
    private MFXButton offlineUsersButton;

    @FXML
    private MFXButton onlineUsersButton;


    private AnchorPane anchorPanWithoutmenu;
    @FXML
    private AnchorPane friendDetailsAnchorPan;
    @FXML
    private AnchorPane chatAnchorpan;
    @FXML
    private AnchorPane containerAnchorPan;

    @FXML
    private ScrollPane scrollPane;
   // public static ObservableList<Card> currentList;
    private double lastX = 0.0d;
    private double lastY = 0.0d;
    private double lastWidth = 0.0d;
    private double lastHeight = 0.0d;
    private User friendUser;
    private  void prepareListView(ListView cardsListView, ScrollPane scrollPane) {
        cardsListView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            public ListCell<Card> call(ListView<Card> param) {
                final Tooltip tooltip = new Tooltip();
                final ListCell<Card> cell = new ListCell<Card>() {
                    @Override
                    public void updateItem(Card item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            if (item != null) {
                                Image userImage = new Image(new ByteArrayInputStream(item.getCardImg()), 30, 30, false, true);
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/card.fxml"));
                                cardController cardController = new cardController(userImage, item.getCardContent(), item.getCardName());
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
        cardsListView.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/friendInfo.fxml"));
                FXMLLoader loadergroup = new FXMLLoader(getClass().getResource("/views/GroupInfo.fxml"));
                Card selected = (Card)cardsListView.getSelectionModel().getSelectedItem();
                if (cardsListView.getSelectionModel().getSelectedItem() != null) {
                    System.out.println(selected.getCardID());
                    friendName.set(selected.getCardName());
                    friendImage = new Image(new ByteArrayInputStream((selected.getCardImg())));
                    friendImageOpen.setFill(new ImagePattern(friendImage));
                    friendImageClose.setFill(new ImagePattern(friendImage));
                    if (selected.getCardType() == CardType.CHAT) {
                        VBox box = ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(selected.getCardID());
                        scrollPane.setContent(box);
                        CurrentChat.setCurrentChatSingle(selected.getCardID());

                        friendUser =  UserServicesImpl.getUserServices().getUser(selected.getCardID());
                        friendInfoController friendInfoController = new friendInfoController(friendUser);
                        loader.setController(friendInfoController);
                        try {
                            friendDetailsAnchorPan.getChildren().clear();
                            friendDetailsAnchorPan.getChildren().add(loader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    } else if (selected.getCardType() == CardType.GROUP) {
                        VBox box = ListCoordinatorImpl.getListCoordinator().getGroupChatVbox(selected.getCardID());
                        scrollPane.setContent(box);
                        CurrentChat.setCurrentChatGroup(selected.getCardID());

                        GroupInfoController groupInfoController = new GroupInfoController(selected);
                        loadergroup.setController(groupInfoController);
                        try {
                            friendDetailsAnchorPan.getChildren().clear();
                            friendDetailsAnchorPan.getChildren().add(loadergroup.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }else if(selected.getCardType() == CardType.FRIEND){
                        int chatID = ChatServicesImpl.getChatService().createChat(new Chat(CurrentUserImp.getCurrentUser().getId(), selected.getCardID()));
                        System.out.println(chatID+"The new Chat ID");
                        VBox box = ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(chatID);
                        scrollPane.setContent(box);
                        CurrentChat.setCurrentChatSingle(chatID);
                        friendUser =  UserServicesImpl.getUserServices().getUser(selected.getCardID());
                        System.out.println(friendUser.getUserName()+ " "+friendUser.getId()+ ""+selected.getCardID() );
                        friendInfoController friendInfoController = new friendInfoController(friendUser);
                        loader.setController(friendInfoController);
                        try {
                            friendDetailsAnchorPan.getChildren().clear();
                            friendDetailsAnchorPan.getChildren().add(loader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    Animation animation = new Timeline(
                            new KeyFrame(Duration.seconds(2),
                                    new KeyValue(scrollPane.vvalueProperty(), 1)));
                    animation.play();
                    friendName.set(selected.getCardName());
                    friendImage = new Image(new ByteArrayInputStream((selected.getCardImg())));
                    friendImageOpen.setFill(new ImagePattern(friendImage));
                    friendImageClose.setFill(new ImagePattern(friendImage));

                }
            }

        });
    }

    @FXML
    void sendMessage(ActionEvent event) {
        if(CurrentChat.getCurrentChat() != null && messageText.getText().length() > 0){
            int id = CurrentChat.getCurrentChat().getCurrentChatID();
            if(CurrentChat.getCurrentChat().getCurrentChatType() == ChatType.SINGLE){
                ChatMessage message = new ChatMessage(id,CurrentUserImp.getCurrentUser().getId(),
                        messageText.getText(), LocalDateTime.now(),0);
                ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(id).getChildren().add(ChatServicesImpl.getChatService().sendChatMessage(message));
                ChatServicesImpl.getChatService().updateChatList(id,messageText.getText());
            }else if(CurrentChat.getCurrentChat().getCurrentChatType() == ChatType.GROUP){
                GroupMessage message = new GroupMessage(CurrentUserImp.getCurrentUser().getId(),messageText.getText(),LocalDateTime.now(),
                        id,0);
                ListCoordinatorImpl.getListCoordinator().getGroupChatVbox(id).getChildren().add(ChatServicesImpl.getChatService().sendGroupMessage(message));
                ChatServicesImpl.getChatService().updateGroupChatList(id,messageText.getText());
            }
            Animation animation = new Timeline(
                    new KeyFrame(Duration.seconds(2),
                            new KeyValue(scrollPane.vvalueProperty(), 1)));
            animation.play();
            messageText.clear();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepareListView(cardsListView, scrollPane);
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserChats());
        ListCoordinatorImpl.currentList=CardType.CHAT;
        listBox.setVisible(false);
    
        dragBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        dragBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
            friendName = new SimpleStringProperty("");

            friendNameClose.textProperty().bind(friendName);
            friendNameOpen.textProperty().bind(friendName);


            Image UserImage = new Image(new ByteArrayInputStream(CurrentUserImp.getCurrentUser().getImg()));
            user_image_side_bar.setFill(new ImagePattern(UserImage));
            // sliders
            anchorPanSlider.setTranslateX(-80);
            friendDetailsAnchorPan.setTranslateX(300);
            AnchorPane.setRightAnchor(chatAnchorpan,0.0);
            AnchorPane.setLeftAnchor(chatAnchorpan,0.0);
            closeFrienDetailsbtn.setVisible(false);
            closeExtraction.setVisible(false);

            extract_menu_id.setOnAction(event -> {
                TranslateTransition slider_tr = new TranslateTransition();
                slider_tr.setDuration(Duration.seconds(0.4));
                slider_tr.setNode(anchorPanSlider);

                slider_tr.setToX(0);
                slider_tr.play();
                //anchorPanSlider.setTranslateX(-80);
                TranslateTransition VBoxslider = new TranslateTransition();
                VBoxslider.setDuration(Duration.seconds(0.4));
                VBoxslider.setNode(anchorPanWithoutmenu);

                VBoxslider.setToX(66);
                VBoxslider.play();
                TranslateTransition cardListSLider = new TranslateTransition();
                cardListSLider.setDuration(Duration.seconds(0.4));
                cardListSLider.setNode(cardsListView);

                cardListSLider.setToX(0);
                cardListSLider.play();
                slider_tr.setOnFinished((ActionEvent e)->{
                    extract_menu_id.setVisible(false);
                    closeExtraction.setVisible(true);

                });

            });
            closeExtraction.setOnAction(event -> {
                TranslateTransition slider_tr = new TranslateTransition();
                slider_tr.setDuration(Duration.seconds(0.4));
                slider_tr.setNode(anchorPanSlider);

                slider_tr.setToX(-80);
                slider_tr.play();
                //anchorPanSlider.setTranslateX(-80);
                TranslateTransition VBoxslider = new TranslateTransition();
                VBoxslider.setDuration(Duration.seconds(0.4));
                VBoxslider.setNode(anchorPanWithoutmenu);

                VBoxslider.setToX(0);
                VBoxslider.play();
                TranslateTransition cardListSLider = new TranslateTransition();
                cardListSLider.setDuration(Duration.seconds(0.4));
                cardListSLider.setNode(cardsListView);

                cardListSLider.setToX(30);
                cardListSLider.play();


                slider_tr.setOnFinished((ActionEvent e)->{
                    extract_menu_id.setVisible(true);
                    closeExtraction.setVisible(false);

                });

            });
            /////////////////
            prepareListView(cardsListView, scrollPane);
            cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserChats());
    }
    @FXML
    void showFriendDetails(MouseEvent event) {
        TranslateTransition slider_tr = new TranslateTransition();
        slider_tr.setDuration(Duration.seconds(0.4));
        slider_tr.setNode(friendDetailsAnchorPan);

        slider_tr.setToX(0);
        slider_tr.play();

        AnchorPane.setRightAnchor(chatAnchorpan,250.0);
        AnchorPane.setLeftAnchor(chatAnchorpan,0.0);


        slider_tr.setOnFinished((ActionEvent e)->{
            showFriendDetailsbtn.setVisible(false);
            closeFrienDetailsbtn.setVisible(true);

        });

    }
    @FXML
    void closeFrienDetails(MouseEvent event) {
        TranslateTransition slider_tr = new TranslateTransition();
        slider_tr.setDuration(Duration.seconds(0.4));
        slider_tr.setNode(friendDetailsAnchorPan);

        slider_tr.setToX(250);
        slider_tr.play();

        AnchorPane.setRightAnchor(chatAnchorpan,0.0);
        AnchorPane.setLeftAnchor(chatAnchorpan,0.0);


        slider_tr.setOnFinished((ActionEvent e)->{
            showFriendDetailsbtn.setVisible(true);
            closeFrienDetailsbtn.setVisible(false);

        });

    }
    @FXML
    void setChats(ActionEvent event) {
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserChats());
        ListCoordinatorImpl.currentList=CardType.CHAT;
        listBox.setVisible(false);
    }

    @FXML
    void setFriends(ActionEvent event) {
        addButton.setVisible(true);
        FriendRequests_id.setVisible(true);
        onlineUsersButton.setVisible(true);
        offlineUsersButton.setVisible(true);
        listBox.setVisible(true);
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends());
        ListCoordinatorImpl.currentList=CardType.FRIEND;
    }

    @FXML
    void setGroups(ActionEvent event) {

        //currentList.clear();
        //currentList.addAll(ListCoordinatorImpl.getListCoordinator().getUserGroups());
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserGroups());
        ListCoordinatorImpl.currentList=CardType.GROUP;
        listBox.setVisible(true);
        addButton.setVisible(true);
        FriendRequests_id.setVisible(false);
        onlineUsersButton.setVisible(false);
        offlineUsersButton.setVisible(false);
    }

    @FXML
    void signOut(ActionEvent event) {
        try {
            ServerConnection.getServer().logout(CurrentUserImp.getCurrentUser().getId(), ClientImpl.getClient());
            System.out.println("logout successfully");
            System.out.println(RememberSetting.getPhone());
            System.out.println(RememberSetting.getPassword());
            RememberSetting.setProperties(CurrentUserImp.getCurrentUser().getPhoneNumber(),"");
            SwitchScenes.getInstance().switchToSignInSecond(event);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.out.println("couldn't logout");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("couldn't switch");
        }
    }

    @FXML
    void userNotifications(ActionEvent event) {

    }

    @FXML
    void userSettings(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditeProfile.fxml"));
        EditeProfileController editeProfileController = new EditeProfileController();
        loader.setController(editeProfileController);
        try {
            Scene scene =new Scene(loader.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

           // ((Node)(event.getSource())).getScene().getRoot().setDisable(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            System.exit(0);
        }

    }

    @FXML
    void maximizeDecoratedButtonHandler(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (stage.isMaximized()) {
            stage.setMaximized(false);
        } else {
            stage.setMaximized(true);
        }
    }

    @FXML
    void minimizeDecoratedButtonHandler(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void getAllFriendRequests(ActionEvent event) {
        FXMLLoader friendRequestFXML;
        try {
            friendRequestFXML =new FXMLLoader(Objects.requireNonNull(ChatScreenController.class.getResource("/views/FriendRequests.fxml")));
            FriendRequestsController friendRequestsController = new FriendRequestsController();
            friendRequestFXML.setController(friendRequestsController);
            Scene scene =new Scene(friendRequestFXML.load());
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();

            //((Node)(event.getSource())).getScene().getRoot().setDisable(true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void sendInvitation(ActionEvent event) {
        FXMLLoader loader ;
        Scene scene = null;
        try {
            System.out.println("Current List= "+ListCoordinatorImpl.currentList);
            if(ListCoordinatorImpl.currentList==CardType.FRIEND) {
                loader = new FXMLLoader(Objects.requireNonNull(ChatScreenController.class.getResource("/views/AddFriend.fxml")));
                AddFriendRequestController addFriendRequestController = new AddFriendRequestController();
                loader.setController(addFriendRequestController);
                scene = new Scene(loader.load(), 550, 550);
            }
            else if(ListCoordinatorImpl.currentList==CardType.GROUP){
                loader = new FXMLLoader(Objects.requireNonNull(ChatScreenController.class.getResource("/views/AddGroup.fxml")));
                AddGroupController addGroupController = new AddGroupController();
                loader.setController(addGroupController);
                scene = new Scene(loader.load(), 550, 550);
            }
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            //((Node)(event.getSource())).getScene().getRoot().setDisable(true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void getAllOnlineUsers(ActionEvent event) {
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends());
    }

    @FXML
    void getAllofflineUsedrs(ActionEvent event) {
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends());
    }

}
