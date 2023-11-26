package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import dto.tm.CustomerTm;
import javafx.fxml.FXML;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;



public class CustomerFormController {

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTextField txtCustomerId;

    @FXML
    private JFXTextField txtCustomerName;

    @FXML
    private JFXTextField txtCustomerAddress;

    @FXML
    private JFXTextField txtCustomerSalary;

    @FXML
    private JFXTextField txtCustomerSearch;

    @FXML
    private JFXTreeTableView<CustomerTm> tblCustomer;

    @FXML
    private TreeTableColumn colId;

    @FXML
    private TreeTableColumn colName;

    @FXML
    private TreeTableColumn colAddress;

    @FXML
    private TreeTableColumn colSalary;

    @FXML
    private TreeTableColumn colOption;

    @FXML
    void onActionBackBtn(ActionEvent event) {

    }

    @FXML
    void onActionReloadBtn(ActionEvent event) {

    }

    @FXML
    void onActionSaveBtn(ActionEvent event) {

    }

    @FXML
    void onActionUpdateBtn(ActionEvent event) {

    }


}
