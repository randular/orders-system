package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import dto.tm.OrderTm;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class OrderFormController {

    public JFXTextField txtItemDesc;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtItemQty;
    public JFXComboBox cmbItemCode;
    public JFXComboBox cmbCustomerID;
    public JFXTextField txtCustomerName;
    @FXML
    private BorderPane pane;

    @FXML
    private JFXTreeTableView<OrderTm> tblCustomer;

    @FXML
    private TreeTableColumn colCode;

    @FXML
    private TreeTableColumn colDescription;

    @FXML
    private TreeTableColumn colQTY;

    @FXML
    private TreeTableColumn colAmount;

    @FXML
    private TreeTableColumn colOption;

    public void initialize(){

    }

    @FXML
    void onActionAddToCart(ActionEvent event) {

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
    void onActionPlaceOrder(ActionEvent event) {

    }

}
