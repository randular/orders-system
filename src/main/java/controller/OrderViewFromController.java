package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.OrderViewDto;
import dto.OrderViewItemsDto;
import dto.tm.OrderItemViewTm;
import dto.tm.OrderViewTm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.OrderViewModel;
import model.impl.OrderViewModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Predicate;

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
    private OrderViewModel orderViewModel = new OrderViewModelImpl();

    public void initialize(){
        colOrderCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderID"));
        colOrderDate.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderDate"));
        colOrderCustID.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderCustID"));
        colOrderCustName.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderCustName"));
        loadOrderViewTable();

        tblOrderView.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            itemTableLoad(newValue);
        });

        txtOrderSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblOrderView.setPredicate(new Predicate<TreeItem<OrderViewTm>>() {
                    @Override
                    public boolean test(TreeItem<OrderViewTm> orderViewTmTreeItem) {
                        return orderViewTmTreeItem.getValue().getOrderID().toLowerCase().contains(newValue.toLowerCase());
                    }
                });
            }
        });
        colItemCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderItemCode"));
        colItemName.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderItemName"));
        colItemQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderItemQty"));
        colItemUnitPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderItemUnitPrice"));
        colItemAmount.setCellValueFactory(new TreeItemPropertyValueFactory<>("orderItemAmount"));
    }

    private void itemTableLoad(TreeItem<OrderViewTm> newValue) {
        if (newValue != null){
            OrderViewTm selectedOrder = newValue.getValue();
            ObservableList<OrderItemViewTm> orderDetailsTmList = FXCollections.observableArrayList();
            double totalAmount = 0;
            try {
                List<OrderViewItemsDto> list = orderViewModel.allItems(selectedOrder.getOrderID());
                for (OrderViewItemsDto dto:list){
                    OrderItemViewTm table = new OrderItemViewTm(
                            dto.getOrderItemCode(),
                            dto.getOrderItemName(),
                            dto.getOrderItemQty(),
                            dto.getOrderItemUnitPrice(),
                            dto.getOrderItemAmount()
                    );
                    orderDetailsTmList.add(table);
                    totalAmount+=dto.getOrderItemAmount();
                }
                TreeItem<OrderItemViewTm> treeItem = new RecursiveTreeItem<>(orderDetailsTmList, RecursiveTreeObject::getChildren);
                tblItems.setRoot(treeItem);
                tblItems.setShowRoot(false);
                lblOrderTotal.setText(String.format("%.2f",totalAmount));

            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void loadOrderViewTable() {
        ObservableList<OrderViewTm> orderTmList = FXCollections.observableArrayList();
        try {
            List<OrderViewDto> orderViewList = orderViewModel.allOrders();
            for (OrderViewDto dto:orderViewList){
                OrderViewTm table = new OrderViewTm(
                        dto.getOrderID(),
                        dto.getDate(),
                        dto.getCustomerID(),
                        dto.getCustomerName()
                );
                orderTmList.add(table);
            }
            TreeItem<OrderViewTm> treeItem = new RecursiveTreeItem<>(orderTmList, RecursiveTreeObject::getChildren);
            tblOrderView.setRoot(treeItem);
            tblOrderView.setShowRoot(false);

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

    @FXML
    void onActionRefreshBtn(ActionEvent event) {
        txtOrderSearch.clear();
        tblOrderView.refresh();
        lblOrderTotal.setText("0.00");
        tblOrderView.getSelectionModel().clearSelection();
        tblItems.setRoot(null);

    }

}
