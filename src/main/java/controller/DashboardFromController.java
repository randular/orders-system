package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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

    public void onActionCustomerBtn(MouseEvent mouseEvent) {

    }

    public void onActionItemBtn(MouseEvent mouseEvent) {

    }
}
