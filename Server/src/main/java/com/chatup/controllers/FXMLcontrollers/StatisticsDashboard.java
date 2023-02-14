package com.chatup.controllers.FXMLcontrollers;

import com.chatup.controllers.services.implementations.UserServicesImpl;
import com.chatup.utils.StageManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsDashboard implements Initializable {
    private static double xOffset = 0;
    private static double yOffset = 0;
    @FXML
    public HBox dragBar;
    @FXML
    private PieChart userStatePieChart;
    @FXML
    private PieChart genderPieChart;
    @FXML
    private LineChart<String, Number> countryLineChart;
    @FXML
    private Button announcementButton;
    @FXML
    private ImageView closeXButton;
    @FXML
    private Button signoutButton;
    @FXML
    private Button statisticsButton;

    private static StatisticsDashboard statisticsDashboard;

    private StatisticsDashboard(){}

    public static StatisticsDashboard getStatisticsDashboard(){
        if (statisticsDashboard == null)
            statisticsDashboard = new StatisticsDashboard();
        return statisticsDashboard;
    }
    @FXML
    void announcementButtonHandler(ActionEvent event) {
        StageManager.getInstance().switchToAnnouncement();
    }

    @FXML
    void closeButtonHandler(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING, "Server is running!!!", ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @FXML
    void overviewButtonHandler(ActionEvent event) {
        StageManager.getInstance().switchToOverview();
    }

    @FXML
    void signoutButtonHandler(ActionEvent event) {

    }

    @FXML
    void statisticsButtonHandler(ActionEvent event) {

    }

    public void genderPieChartHandler() {
        if (genderPieChart.getData().size() > 0) {
            genderPieChart.getData().clear();
        }
        int numberOfMale = UserServicesImpl.getUserServices().getNumberAllMaleUsers();
        int numberOfFemale = UserServicesImpl.getUserServices().getNumberAllUsers() - numberOfMale;
        pieChartHandler(numberOfMale, numberOfFemale, "Male", "Female", "Gender Statistics", genderPieChart);
    }

    private void pieChartHandler(int firstTotal, int secondTotal, String firstData, String secondData, String title, PieChart chart) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        data.addAll(new PieChart.Data(firstData, firstTotal), new PieChart.Data(secondData, secondTotal));
        chart.setData(data);
        chart.setTitle(title);
        chart.setPrefWidth(550);
        chart.setPrefHeight(350);
        calculateStatisticsPercentages(chart);
    }

    private void calculateStatisticsPercentages(PieChart pieChart) {
        double total = 0;
        for (PieChart.Data d : pieChart.getData()) {
            total += d.getPieValue();
        }
        if (total <= 0) {
            System.out.println("Error: Total data is less than or equal to zero.");
            return;
        }
        for (PieChart.Data d : pieChart.getData()) {
            Node slice = d.getNode();
            double percent = calculatePercentage(d.getPieValue(), total);
            String tip = d.getName() + " = " + String.format("%.2f", percent) + "%";
            Tooltip tooltip = new Tooltip(tip);
            tooltip.setFont(Font.font(20));
            Tooltip.install(slice, tooltip);
        }
    }

    private double calculatePercentage(double value, double total) {
        return (value / total) * 100;
    }

    private void userStatePieChartHandler() {
        if (userStatePieChart.getData().size() > 0) {
            userStatePieChart.getData().clear();
        }
        int numberOfOnline = UserServicesImpl.getUserServices().getNumberAllOnlineUsers();
        int numberOfOffline = UserServicesImpl.getUserServices().getNumberAllUsers() - numberOfOnline;
        pieChartHandler(numberOfOnline, numberOfOffline, "Online", "Offline", "User State Statistics", userStatePieChart);
    }

    private XYChart.Series<String, Number> xyChart(String country) {
        XYChart.Series<String, Number> countryChart = new XYChart.Series<>();
        countryChart.setName(country);
        countryChart.getData().add(new XYChart.Data<>(country, UserServicesImpl.getUserServices().getNumberAllCountryOfUsers(country)));
        return countryChart;
    }

    private void countryBarChartHandler() {
        countryLineChart.getData().clear();
        countryLineChart.getXAxis().setLabel("Country");
        countryLineChart.getYAxis().setLabel("Number of Users");
        countryLineChart.getData().addAll(xyChart("Egypt"), xyChart("Morocco"), xyChart("Kuwait"), xyChart("Palestinian"), xyChart("Qatar"), xyChart("Other"));
    }

    public void refershStatisitic(){
        genderPieChartHandler();
        userStatePieChartHandler();
        countryBarChartHandler();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        signoutButton.setDisable(true);
        genderPieChartHandler();
        userStatePieChartHandler();
        countryBarChartHandler();
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
    }

    @FXML
    public void minimiseButtonHandler(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}