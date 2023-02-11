package com.chatup.controllers.FXMLcontrollers;

import com.chatup.models.entities.Card;
import com.chatup.models.entities.Chat;
import com.chatup.models.entities.ChatMessage;
import com.chatup.network.ServerConnection;
import com.chatup.network.interfaces.Server;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Callback;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.Map;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Server server = ServerConnection.getServer();
        try {

            Card card = null;
            ObservableList<Card> cardList = FXCollections.<Card>observableArrayList();
            Map<Chat, ChatMessage> userChats = server.getUserChats(1);
            for (Map.Entry<Chat, ChatMessage> set : userChats.entrySet()) {
                card = new Card(set.getKey(), set.getValue());
                cardList.add(card);
            }
            cardsListView.setItems(cardList);
            cardsListView.setStyle("-fx-background-color :  #F1F1F1;");
            cardsListView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {

                public ListCell<Card> call(ListView<Card> param) {

                    final Tooltip tooltip = new Tooltip();

                    final ListCell<Card> cell = new ListCell<Card>() {

                        @Override
                        public void updateItem(Card item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                HBox hBox = new HBox();
                                VBox box = new VBox();
                                ImageView imageView = new ImageView();
                                imageView.setImage(new Image("D:\\Java-Track\\IntellijProjects\\javaP\\img1.jpg", 30, 30, false, true));
                                Circle circle = new Circle(15, 15, 15);
                                imageView.setStyle("-fx-padding:5;");
                                imageView.setClip(circle);
                                Text text = new Text(item.getCardName());
                                TextFlow textFlow = new TextFlow(new Text(item.getCardContent()));
                                box.getChildren().add(text);
                                box.getChildren().add(textFlow);
                                hBox.getChildren().addAll(imageView, box);
                                hBox.setStyle("-fx-padding: 10;" +
                                        "-fx-background-color :  #ffffff;" +
                                        "-fx-background-radius:20 20 20 20;");
                                setOnMouseEntered(t -> hBox.setStyle("-fx-background-color:#d0d5db;" +
                                        "-fx-padding: 10;" +
                                        "-fx-background-radius:20 20 20 20;" +
                                        "-fx-background-insets: 0 0 -1 0, 0, 1, 2;"));

                                setOnMouseExited(new EventHandler<MouseEvent>() {

                                    @Override
                                    public void handle(MouseEvent t) {
                                        hBox.setStyle("-fx-padding: 10;" +
                                                "-fx-background-color :  #ffffff;" +
                                                "-fx-background-radius:20 20 20 20;" +
                                                "-fx-background-insets: 0 0 -1 0, 0, 1, 2;");
                                    }
                                });
                                setStyle("-fx-background-color :  #F1F1F1;");
                                setGraphic(hBox);
                                tooltip.setText(item.getCardContent());
                                setTooltip(tooltip);
                            }
                        }
                    };
                    return cell;

                }
            });


        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }
}
