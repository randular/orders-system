package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.CustomerDto;
import dto.tm.CustomerTm;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.CustomerModel;
import model.impl.CustomerModelImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


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

    private CustomerModel customerModel = new CustomerModelImpl();

    public void initialize(){
        colId.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
        colName.setCellValueFactory(new TreeItemPropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new TreeItemPropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new TreeItemPropertyValueFactory<>("salary"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("deleteBtn"));
        loadCustomersTable();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setItems(newValue);
        });
    }

    private void setItems(TreeItem<CustomerTm> newValue) {
        if (newValue != null){
            CustomerTm selectedObj = newValue.getValue();
            txtCustomerId.setEditable(false);
            txtCustomerId.setText(selectedObj.getId());
            txtCustomerName.setText(selectedObj.getName());
            txtCustomerAddress.setText(selectedObj.getAddress());
            txtCustomerSalary.setText(String.valueOf(selectedObj.getSalary()));
        }
    }

    private void loadCustomersTable() {
        ObservableList<CustomerTm> tmList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> dtoList = customerModel.allCustomer();
            for (CustomerDto dto:dtoList) {
                JFXButton deleteBtn = new JFXButton("Delete");
                deleteBtn.setStyle("-fx-background-color:#c91114;" +
                        "-fx-font-weight: BOLD"
                );
                CustomerTm table = new CustomerTm(dto.getId(),
                        dto.getName(),
                        dto.getAddress(),
                        dto.getSalary(),
                        deleteBtn
                );
                deleteBtn.setOnAction(actionEvent -> {
                    deleteCustomer(dto.getId());
                });

                tmList.add(table);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        TreeItem<CustomerTm> treeItem = new RecursiveTreeItem<>(tmList,
                RecursiveTreeObject::getChildren);
        tblCustomer.setRoot(treeItem);
        tblCustomer.setShowRoot(false);
    }

    private void deleteCustomer(String id) {
        try {
            if (customerModel.deleteCustomer(id)){
                new Alert(Alert.AlertType.INFORMATION,"Customer Deleted!").show();
                loadCustomersTable();
                clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
        txtCustomerId.clear();
        txtCustomerId.setEditable(true);
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerSalary.clear();
        txtCustomerSearch.clear();
    }

    @FXML
    void onActionBackBtn(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/DashboardFrom.fxml"))));
            stage.setResizable(false);
            stage.setTitle("Welcome");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionReloadBtn(ActionEvent event) {
        clearFields();
    }

    @FXML
    void onActionSaveBtn(ActionEvent event) {
        try {
            boolean isSaved = customerModel.saveCustomer(new CustomerDto(
                    txtCustomerId.getText(),
                    txtCustomerName.getText(),
                    txtCustomerAddress.getText(),
                    Double.parseDouble(txtCustomerSalary.getText())
            ));
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Customer Saved!").show();
                loadCustomersTable();
                clearFields();
            }

        }catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Duplicate Entry!").show();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionUpdateBtn(ActionEvent event) {

    }


}
