package controller;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.ItemDto;
import dto.OrderDetailsDto;
import dto.OrderDto;
import dto.tm.OrderTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.CustomerModel;
import model.ItemModel;
import model.OrderModel;
import model.impl.CustomerModelImpl;
import model.impl.ItemModelImpl;
import model.impl.OrderModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class OrderFormController {

    public JFXTextField txtItemDesc;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtItemQty;
    public JFXComboBox cmbItemCode;
    public JFXComboBox cmbCustomerID;
    public JFXTextField txtCustomerName;
    public Label lblTotalAmount;
    public Label lblOrderId;

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
    private OrderModel orderModel = new OrderModelImpl();

    private ObservableList<OrderTm> tmList = FXCollections.observableArrayList();

    private double tot;


    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colQTY.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("amt"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("deleteBtn"));

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
        txtCustomerName.setEditable(false);
        txtUnitPrice.setEditable(false);
        txtItemDesc.setEditable(false);

        generateId();

        tot = 0;
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
        try {
            int qtyOrdered = 0;
            for (OrderTm order:tmList) {
                if (order.getCode().equals(cmbItemCode.getValue().toString())){
                    qtyOrdered += order.getQty();
                }
            }
            if (txtCustomerName.getText().isEmpty()|| txtItemDesc.getText().isEmpty() || txtItemQty.getText().isEmpty()){
                new Alert(Alert.AlertType.ERROR,"Please enter Customer,Item and quantity details").show();
            }else if (itemModel.getItem(cmbItemCode.getValue().toString()).getQty() < Integer.parseInt(txtItemQty.getText())+qtyOrdered){
                new Alert(Alert.AlertType.ERROR,"Entered quantity is more than on hand quantity").show();
            }else{
                double amount = itemModel.getItem(cmbItemCode.getValue().toString())
                        .getUnitPrice() * Integer.parseInt(txtItemQty.getText());
                JFXButton deleteBtn = new JFXButton("Delete");
                deleteBtn.setStyle("-fx-background-color:#c91114;" +
                        "-fx-font-weight: BOLD"
                );
                OrderTm orderTm = new OrderTm(
                        cmbItemCode.getValue().toString(),
                        txtItemDesc.getText(),
                        Integer.parseInt(txtItemQty.getText()),
                        amount,
                        deleteBtn
                );
                deleteBtn.setOnAction(actionEvent -> {
                    tmList.remove(orderTm);
                    tblOrder.refresh();
                    tot-=orderTm.getAmt();
                    lblTotalAmount.setText(String.format("%.2f",tot));
                });
                boolean isExist = false;

                tot+=amount;
                for (OrderTm order:tmList) {
                    if (order.getCode().equals(orderTm.getCode())){
                        order.setQty(order.getQty() + orderTm.getQty());
                        order.setAmt(order.getAmt() + orderTm.getAmt());
                        isExist = true;
                    }
                }
                if (!isExist){
                    tmList.add(orderTm);
                }

                TreeItem<OrderTm> treeItem = new RecursiveTreeItem<>(tmList,
                        RecursiveTreeObject::getChildren);
                tblOrder.setRoot(treeItem);
                tblOrder.setShowRoot(false);

                lblTotalAmount.setText(String.format("%.2f",tot));
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

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

    public void generateId(){
        try {
            OrderDto orderDto = orderModel.lastOrder();
            if (orderDto != null){
                String id = orderDto.getOrderID();
                int num = Integer.parseInt(id.split("[D]")[1]);
                num++;
                lblOrderId.setText(String.format("D%03d",num));
            }else{
                lblOrderId.setText("D001");
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void onActionPlaceOrder(ActionEvent event) {
        List<OrderDetailsDto> list = new ArrayList<>();
        for (OrderTm orderTm:tmList){
            list.add(new OrderDetailsDto(
                    lblOrderId.getText(),
                    orderTm.getCode(),
                    orderTm.getQty(),
                    orderTm.getAmt()/orderTm.getQty()
            ));
        }
        if (!tmList.isEmpty()){
            boolean isSaved = false;
            try {
                isSaved = orderModel.saveOrder(new OrderDto(
                        lblOrderId.getText(),
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")).toString(),
                        cmbCustomerID.getValue().toString(),
                        list
                ));
                if (isSaved){
                    new Alert(Alert.AlertType.INFORMATION,"Order Saved!").show();
                }else{
                    new Alert(Alert.AlertType.ERROR,"Something went wrong").show();
                }
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }else{
            new Alert(Alert.AlertType.ERROR,"Please input the items for the order").show();
        }

    }

}
