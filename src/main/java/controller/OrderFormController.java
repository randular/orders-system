package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import dto.CustomerDto;
import dto.ItemDto;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.CustomerModel;
import model.ItemModel;
import model.impl.CustomerModelImpl;
import model.impl.ItemModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrderFormController {

    public JFXTextField txtItemDesc;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtItemQty;
    public JFXComboBox cmbItemCode;
    public JFXComboBox cmbCustomerID;
    public JFXTextField txtCustomerName;
    public Label lblTotalAmount;

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTreeTableView<OrderTm> tblOrder;

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

    private List<CustomerDto> customers;
    private List<ItemDto> items;

    private CustomerModel customerModel = new CustomerModelImpl();
    private ItemModel itemModel = new ItemModelImpl();


    public void initialize(){
        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, customerID) -> {
            for (CustomerDto dto:customers){
                if(dto.getId().equals(customerID)){
                    txtCustomerName.setText(dto.getName());
                }
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, itemCode) -> {
            for (ItemDto dto:items) {
                if (dto.getCode().equals(itemCode)){
                    txtItemDesc.setText(dto.getDesc());
                    txtUnitPrice.setText(String.format("%.2f",dto.getUnitPrice()));
                }
            }
        });
        loadCustomerIds();
        loadItemCodes();
    }

    private void loadItemCodes() {
        try {
            items = itemModel.allItems();
            ObservableList list = FXCollections.observableArrayList();
            for (ItemDto dto:items){
                list.add(dto.getCode());
            }
            cmbItemCode.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadCustomerIds() {
        try {
            customers = customerModel.allCustomer();
            ObservableList list = FXCollections.observableArrayList();
            for (CustomerDto dto:customers){
                list.add(dto.getId());
            }
            cmbCustomerID.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
