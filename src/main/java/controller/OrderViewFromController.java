package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import dto.tm.OrderItemViewTm;
import dto.tm.OrderViewTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class OrderViewFromController {

    public JFXTextField txtOrderSearch;
    @FXML
    private BorderPane pane;

    @FXML
    private JFXTreeTableView<OrderViewTm> tblOrderView;

    @FXML
    private TreeTableColumn colOrderCode;

    @FXML
    private TreeTableColumn colOrderDate;

    @FXML
    private TreeTableColumn colOrderCustID;

    @FXML
    private TreeTableColumn colOrderCustName;

    @FXML
    private JFXTreeTableView<OrderItemViewTm> tblItems;

    @FXML
    private TreeTableColumn colItemCode;

    @FXML
    private TreeTableColumn colItemName;

    @FXML
    private TreeTableColumn colItemQty;

    @FXML
    private TreeTableColumn colItemUnitPrice;

    @FXML
    private TreeTableColumn colItemAmount;

    @FXML
    private Label lblOrderTotal;

    public void initialize(){
        colOrderCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("colOrderCode"));
        colOrderDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("Date"));
        colOrderCustID.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerID"));
        colOrderCustName.setCellValueFactory(new TreeItemPropertyValueFactory<>("customerName"));
        loadOrderViewTable();
    }

    private void loadOrderViewTable() {
        ObservableList<OrderViewTm> orderTmList = FXCollections.observableArrayList();



    }

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
