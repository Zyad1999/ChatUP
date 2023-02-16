package com.chatup.controllers.FXMLcontrollers;

import com.chatup.App;
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
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.util.List;
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
    private static Image friendImage;
    @FXML
    private HBox dragBar;
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
    private HBox closeFrienDetailsbtn;
    @FXML
    private HBox showFriendDetailsbtn;
    @FXML
    private HBox FriendSildeBar;
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
    private MFXButton notification_btn;
    @FXML
    private MFXButton settings_btn;

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

    @FXML
    private AnchorPane anchorPanWithoutmenu;
    @FXML
    private MFXButton chatBot_btn;
    @FXML
    private AnchorPane friendDetailsAnchorPan;
    @FXML
    private AnchorPane chatAnchorpan;
    @FXML
    private AnchorPane headerChat;
    @FXML
    private AnchorPane footerChat;
    @FXML
    private AnchorPane containerAnchorPan;

    @FXML
    private ImageView botImg;

    @FXML
    private VBox defaultVBoxForImage;

    @FXML
    private ScrollPane scrollPane;
    // public static ObservableList<Card> currentList;
    private double lastX = 0.0d;
    private double lastY = 0.0d;
    private double lastWidth = 0.0d;
    private double lastHeight = 0.0d;
    private User friendUser;

    private void prepareListView(ListView cardsListView, ScrollPane scrollPane) {
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
        cardsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                headerChat.setVisible(true);
                footerChat.setVisible(true);
                FXMLLoader loadergroup = new FXMLLoader(getClass().getResource("/views/GroupInfo.fxml"));
                Card selected = (Card) cardsListView.getSelectionModel().getSelectedItem();
                System.out.println(selected.getCardType());
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
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/friendInfo.fxml"));

                        List<User> chatUsers = ChatServicesImpl.getChatService().getSingleChatUsers(selected.getCardID());
                        if (chatUsers.get(0).getId() == CurrentUserImp.getCurrentUser().getId())
                            friendUser = chatUsers.get(1);
                        else {
                            friendUser = chatUsers.get(0);
                        }
                        frieendStatusClose.setVisible(true);
                        frieendStatusOpen.setVisible(true);
                        frieendStatusClose.setText(friendUser.getStatus().toString());
                        frieendStatusOpen.setText(friendUser.getStatus().toString());
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
                        frieendStatusClose.setVisible(false);
                        frieendStatusOpen.setVisible(false);

                        loadergroup.setController(groupInfoController);
                        try {
                            friendDetailsAnchorPan.getChildren().clear();
                            friendDetailsAnchorPan.getChildren().add(loadergroup.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else if (selected.getCardType() == CardType.FRIEND) {
                        int chatID = ChatServicesImpl.getChatService().createChat(new Chat(CurrentUserImp.getCurrentUser().getId(), selected.getCardID()));
                        System.out.println(chatID + "The new Chat ID");
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/friendInfo.fxml"));

                        VBox box = ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(chatID);
                        scrollPane.setContent(box);
                        CurrentChat.setCurrentChatSingle(chatID);
                        friendUser = UserServicesImpl.getUserServices().getUser(selected.getCardID());
                        frieendStatusClose.setVisible(true);
                        frieendStatusOpen.setVisible(true);
                        frieendStatusClose.setText(friendUser.getStatus().toString());
                        frieendStatusOpen.setText(friendUser.getStatus().toString());
                        System.out.println(friendUser.getUserName() + " " + friendUser.getId() + "" + selected.getCardID());
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
    void sendAttachment(MouseEvent event) {
        if (CurrentChat.getCurrentChat() != null) {

            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            int id = CurrentChat.getCurrentChat().getCurrentChatID();
            String msgText = messageText.getText();
            if (CurrentChat.getCurrentChat().getCurrentChatType() == ChatType.SINGLE) {
                Thread sendChatAttachment = new Thread(() -> {
                    ChatMessage message = new ChatMessage(id, CurrentUserImp.getCurrentUser().getId(),
                            msgText, LocalDateTime.now());
                    int attachmentID = FileServicesImpl.getFileServices().sendChatFileToServer(file, message);
                    Platform.runLater(() -> {
                        if (attachmentID == -1) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "File upload failed");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                            return;
                        }
                        message.setAttachment_Id(attachmentID);
                        ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(id).getChildren().add(ChatServicesImpl.getChatService().sendChatFile(message));
                        ChatServicesImpl.getChatService().updateChatList(id, msgText);
                    });
                    Animation animation = new Timeline(
                            new KeyFrame(Duration.seconds(2),
                                    new KeyValue(scrollPane.vvalueProperty(), 1)));
                    animation.play();
                });
                sendChatAttachment.start();
            } else if (CurrentChat.getCurrentChat().getCurrentChatType() == ChatType.GROUP) {
                Thread sendGroupAttach = new Thread(() -> {
                    GroupMessage message = new GroupMessage(CurrentUserImp.getCurrentUser().getId(), msgText, LocalDateTime.now(),
                            id, 0);
                    int attachmentID = FileServicesImpl.getFileServices().sendGroupFileToServer(file, message);
                    Platform.runLater(() -> {
                        if (attachmentID == -1) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "File upload failed");
                            alert.setHeaderText(null);
                            alert.showAndWait();
                            return;
                        }
                        message.setAttachmentID(attachmentID);
                        ListCoordinatorImpl.getListCoordinator().getGroupChatVbox(id).getChildren().add(ChatServicesImpl.getChatService().sendGroupFile(message));
                        ChatServicesImpl.getChatService().updateGroupChatList(id, msgText);
                    });
                    Animation animation = new Timeline(
                            new KeyFrame(Duration.seconds(2),
                                    new KeyValue(scrollPane.vvalueProperty(), 1)));
                    animation.play();
                });
                sendGroupAttach.start();
            }
            messageText.clear();
        }
    }

    @FXML
    void sendMessage(ActionEvent event) {
        if (CurrentChat.getCurrentChat() != null && messageText.getText().length() > 0) {
            int id = CurrentChat.getCurrentChat().getCurrentChatID();
            if (CurrentChat.getCurrentChat().getCurrentChatType() == ChatType.SINGLE) {
                ChatMessage message = new ChatMessage(id, CurrentUserImp.getCurrentUser().getId(),
                        messageText.getText(), LocalDateTime.now());
                ListCoordinatorImpl.getListCoordinator().getSingleChatVbox(id).getChildren().add(ChatServicesImpl.getChatService().sendChatMessage(message));
                ChatServicesImpl.getChatService().updateChatList(id, messageText.getText());
            } else if (CurrentChat.getCurrentChat().getCurrentChatType() == ChatType.GROUP) {
                GroupMessage message = new GroupMessage(CurrentUserImp.getCurrentUser().getId(), messageText.getText(), LocalDateTime.now(),
                        id, 0);
                ListCoordinatorImpl.getListCoordinator().getGroupChatVbox(id).getChildren().add(ChatServicesImpl.getChatService().sendGroupMessage(message));
                ChatServicesImpl.getChatService().updateGroupChatList(id, messageText.getText());
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
        ListCoordinatorImpl.currentList = CardType.CHAT;

        addButton.setVisible(false);
        FriendRequests_id.setVisible(false);
        onlineUsersButton.setVisible(false);
        offlineUsersButton.setVisible(false);
        user_chats_btn.setStyle("-fx-opacity: 1");
        friends_btn.setStyle("-fx-opacity: 0.3");
        group_btn.setStyle("-fx-opacity: 0.3");
        notification_btn.setStyle("-fx-opacity: 0.3");
        settings_btn.setStyle("-fx-opacity: 0.3");
        chatBot_btn.setStyle("-fx-opacity: 0.7");

        ChatterBotService.getChatterBotService().botStatus=false;
        botImg.setImage(new Image(String.valueOf(ChatScreenController.class.getResource("/images/redchatbot.png"))));

        String style = "-fx-background-image:  url(" + ChatScreenController.class.getResource("/images/logo.png") + "); -fx-background-repeat: no-repeat; -fx-background-size: auto; -fx-background-radius: 10; -fx-background-position: center; -fx-opacity: 0.4;";
        defaultVBoxForImage.setStyle(style);

        headerChat.setVisible(false);
        footerChat.setVisible(false);

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

        friendDetailsAnchorPan.setTranslateX(300);
        AnchorPane.setRightAnchor(chatAnchorpan, 0.0);
        AnchorPane.setLeftAnchor(chatAnchorpan, 0.0);
        closeFrienDetailsbtn.setVisible(false);

        prepareListView(cardsListView, scrollPane);

        FilteredList<Card> filteredList = new FilteredList<>(ListCoordinatorImpl.getListCoordinator().getUserChats());

        cardsListView.setItems(filteredList);


        txt_ld_search.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                filteredList.setPredicate(null);
            } else {
                final String searchString = newValue.toUpperCase();
                filteredList.setPredicate(s -> s.getCardName().toUpperCase().contains(searchString));
            }
        });

    }

    @FXML
    void showFriendDetails(MouseEvent event) {
        TranslateTransition slider_tr = new TranslateTransition();
        slider_tr.setDuration(Duration.seconds(0.4));
        slider_tr.setNode(friendDetailsAnchorPan);

        slider_tr.setToX(0);
        slider_tr.play();

        AnchorPane.setRightAnchor(chatAnchorpan, 250.0);
        AnchorPane.setLeftAnchor(chatAnchorpan, 0.0);


        slider_tr.setOnFinished((ActionEvent e) -> {
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

        AnchorPane.setRightAnchor(chatAnchorpan, 0.0);
        AnchorPane.setLeftAnchor(chatAnchorpan, 0.0);


        slider_tr.setOnFinished((ActionEvent e) -> {
            showFriendDetailsbtn.setVisible(true);
            closeFrienDetailsbtn.setVisible(false);

        });

    }

    @FXML
    void setChats(ActionEvent event) {
        user_chats_btn.setStyle("-fx-opacity: 1");
        friends_btn.setStyle("-fx-opacity: 0.3");
        group_btn.setStyle("-fx-opacity: 0.3");
        notification_btn.setStyle("-fx-opacity: 0.3");
        settings_btn.setStyle("-fx-opacity: 0.3");
        chatBot_btn.setStyle("-fx-opacity: 0.7");
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserChats());
        ListCoordinatorImpl.currentList = CardType.CHAT;
        addButton.setVisible(false);
        FriendRequests_id.setVisible(false);
        onlineUsersButton.setVisible(false);
        offlineUsersButton.setVisible(false);
    }

    @FXML
    void setFriends(ActionEvent event) {
        user_chats_btn.setStyle("-fx-opacity: 0.3");
        friends_btn.setStyle("-fx-opacity: 1");
        group_btn.setStyle("-fx-opacity: 0.3");
        notification_btn.setStyle("-fx-opacity: 0.3");
        settings_btn.setStyle("-fx-opacity: 0.3");
        chatBot_btn.setStyle("-fx-opacity: 0.7");
        addButton.setVisible(true);
        FriendRequests_id.setVisible(true);
        onlineUsersButton.setVisible(true);
        offlineUsersButton.setVisible(true);
        onlineUsersButton.setStyle("-fx-opacity: 1; -fx-background-color: transparent;");
        offlineUsersButton.setStyle("-fx-opacity: 0.3;");
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends());
        ListCoordinatorImpl.currentList = CardType.FRIEND;
    }

    @FXML
    void setGroups(ActionEvent event) {
        user_chats_btn.setStyle("-fx-opacity: 0.3");
        friends_btn.setStyle("-fx-opacity: 0.3");
        group_btn.setStyle("-fx-opacity: 1");
        notification_btn.setStyle("-fx-opacity: 0.3");
        settings_btn.setStyle("-fx-opacity: 0.3");
        chatBot_btn.setStyle("-fx-opacity: 0.7");
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserGroups());
        ListCoordinatorImpl.currentList = CardType.GROUP;
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
            RememberSetting.setProperties(CurrentUserImp.getCurrentUser().getPhoneNumber(), "");
            CurrentChat.setCurrentChat(null);
            ListCoordinatorImpl.getListCoordinator().flushLists();
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
        user_chats_btn.setStyle("-fx-opacity: 0.3");
        friends_btn.setStyle("-fx-opacity: 0.3");
        group_btn.setStyle("-fx-opacity: 0.3");
        notification_btn.setStyle("-fx-opacity: 1");
        settings_btn.setStyle("-fx-opacity: 0.3");
        chatBot_btn.setStyle("-fx-opacity: 0.7");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Notifications.fxml"));
        try {
            Scene newScene = new Scene(loader.load());
            Stage newStage = new Stage();
            newScene.setFill(Color.TRANSPARENT);
            newScene.getRoot().setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #0F3D3E; -fx-border-width: 2;");
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.setScene(newScene);
            newStage.show();

            Scene oldScene = ((Node) event.getSource()).getScene();
            oldScene.getRoot().setDisable(true);

            newStage.setOnHidden(event1 -> {
                oldScene.getRoot().setDisable(false);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void userSettings(ActionEvent event) {
        user_chats_btn.setStyle("-fx-opacity: 0.3");
        friends_btn.setStyle("-fx-opacity: 0.3");
        group_btn.setStyle("-fx-opacity: 0.3");
        notification_btn.setStyle("-fx-opacity: 0.3");
        settings_btn.setStyle("-fx-opacity: 1");
        chatBot_btn.setStyle("-fx-opacity: 0.7");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditeProfile.fxml"));
        try {
            Scene newScene = new Scene(loader.load());
            Stage newStage = new Stage();
            newScene.setFill(Color.TRANSPARENT);
            newScene.getRoot().setStyle("-fx-background-radius: 20; -fx-border-radius: 20; -fx-border-color: #0F3D3E; -fx-border-width: 2;");
            newStage.initStyle(StageStyle.TRANSPARENT);
            newStage.setScene(newScene);
            newStage.show();

            Scene oldScene = ((Node) event.getSource()).getScene();
            oldScene.getRoot().setDisable(true);

            newStage.setOnHidden(event1 -> {
                oldScene.getRoot().setDisable(false);
            });

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
            friendRequestFXML = new FXMLLoader(Objects.requireNonNull(ChatScreenController.class.getResource("/views/FriendRequests.fxml")));
            FriendRequestsController friendRequestsController = new FriendRequestsController();
            friendRequestFXML.setController(friendRequestsController);
            Scene scene = new Scene(friendRequestFXML.load());
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
    void sendInvitation(ActionEvent event) {
        FXMLLoader loader;
        Scene scene = null;
        System.out.println("Current List= " + ListCoordinatorImpl.currentList);
        if (ListCoordinatorImpl.currentList == CardType.FRIEND) {
            loader = new FXMLLoader(Objects.requireNonNull(ChatScreenController.class.getResource("/views/AddFriend.fxml")));
            AddFriendRequestController addFriendRequestController = new AddFriendRequestController("addfriend", -1);
            loader.setController(addFriendRequestController);
            try {
                scene = new Scene(loader.load(), 550, 550);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (ListCoordinatorImpl.currentList == CardType.GROUP) {
            loader = new FXMLLoader(Objects.requireNonNull(ChatScreenController.class.getResource("/views/AddGroup.fxml")));
            AddGroupController addGroupController = new AddGroupController();
            loader.setController(addGroupController);
            try {
                scene = new Scene(loader.load(), 550, 550);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
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
    }


    @FXML
    void getAllOnlineUsers(ActionEvent event) {
        onlineUsersButton.setStyle("-fx-opacity: 1; -fx-background-color: transparent");
        offlineUsersButton.setStyle("-fx-opacity: 0.3");
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserOnlineFriends());
    }

    @FXML
    void getAllofflineUsedrs(ActionEvent event) {
        onlineUsersButton.setStyle("-fx-opacity: 0.3");
        offlineUsersButton.setStyle("-fx-opacity: 1; -fx-background-color: transparent ");
        cardsListView.setItems(ListCoordinatorImpl.getListCoordinator().getUserOfflineFriends());
    }

    @FXML
    private void chatBotButtonHandler(ActionEvent event) {
        if (ChatterBotService.getChatterBotService().botStatus == true) {
            ChatterBotService.getChatterBotService().botStatus = false;
            botImg.setImage(new Image(String.valueOf(ChatScreenController.class.getResource("/images/redchatbot.png"))));
            chatBot_btn.setStyle("border-width: 2px; border-color: #ff3300;");
            chatBot_btn.setStyle("-fx-opacity: 1");

        } else {
            ChatterBotService.getChatterBotService().botStatus = true;
            botImg.setImage(new Image(String.valueOf(ChatScreenController.class.getResource("/images/greenChatBot.png"))));
            chatBot_btn.setStyle("border-width: 2px; border-color: #00ff00;");
            chatBot_btn.setStyle("-fx-opacity: 1");
        }
    }

}
