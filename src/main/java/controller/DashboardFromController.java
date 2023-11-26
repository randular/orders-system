package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardFromController {

    public Label lblTime;
    public AnchorPane paneDashboard;

    public void initialize(){
        calculateTime();


    }

    private void calculateTime() {
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.ZERO,
                actionEvent -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm:ss")))
        ),new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void onActionCustomerBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) paneDashboard.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"))));
            stage.setResizable(false);
            stage.setTitle("Customers");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onActionItemBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) paneDashboard.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ItemForm.fxml"))));
            stage.setResizable(false);
            stage.setTitle("Items");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
