package controller;

import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderViewFromController {

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTreeTableView<?> tblOrderView;

    @FXML
    private TreeTableColumn<?, ?> colOrderCode;

    @FXML
    private TreeTableColumn<?, ?> colOrderDate;

    @FXML
    private TreeTableColumn<?, ?> colOrderCustID;

    @FXML
    private TreeTableColumn<?, ?> colOrderCustName;

    @FXML
    private Label lblOrderId;

    @FXML
    private JFXTreeTableView<?> tblItems;

    @FXML
    private TreeTableColumn<?, ?> colItemCode;

    @FXML
    private TreeTableColumn<?, ?> colItemName;

    @FXML
    private TreeTableColumn<?, ?> colItemQty;

    @FXML
    private TreeTableColumn<?, ?> colItemUnitPrice;

    @FXML
    private TreeTableColumn<?, ?> colItemAmount;

    @FXML
    private Label lblOrderTotal;

    @FXML
    void onActionBackBtn(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardFrom.fxml"))));
            stage.setTitle("Welcome");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void onActionRefreshBtn(ActionEvent event) {

    }

}
