package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemFormController {

       public BorderPane pane;

    public void onActionBackBtn(ActionEvent actionEvent) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFrom.fxml"))));
            stage.setTitle("Welcome");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onActionSaveBtn(ActionEvent actionEvent) {

    }

    public void onActionUpdateBtn(ActionEvent actionEvent) {

    }
}
