package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import dto.ItemDto;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import dto.tm.ItemTm;
import model.ItemModel;
import model.impl.ItemModelImpl;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.function.Predicate;

public class ItemFormController {

    @FXML
    private BorderPane pane;

    @FXML
    private JFXTextField txtItemCode;

    @FXML
    private JFXTextField txtItemDesc;

    @FXML
    private JFXTextField txtItemPrice;

    @FXML
    private JFXTextField txtItemQTY;

    @FXML
    private JFXTextField txtItemSearch;

    @FXML
    private JFXTreeTableView<ItemTm> tblItem;

    @FXML
    private TreeTableColumn colCode;

    @FXML
    private TreeTableColumn colDesc;

    @FXML
    private TreeTableColumn colPrice;

    @FXML
    private TreeTableColumn colQty;

    @FXML
    private TreeTableColumn colOption;

    private ItemModel itemModel = new ItemModelImpl();

    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("deleteBtn"));
        loadItemTable();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            setItems(newValue);
        });

        txtItemSearch.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String newValue) {
                tblItem.setPredicate(new Predicate<TreeItem<ItemTm>>() {
                    @Override
                    public boolean test(TreeItem<ItemTm> treeItem) {
                        return treeItem.getValue().getCode().toLowerCase().contains(newValue.toLowerCase()) ||
                                treeItem.getValue().getDesc().toLowerCase().contains(newValue.toLowerCase());
                    }
                });
            }
        });

    }

    private void setItems(TreeItem<ItemTm> newValue) {

            if (newValue != null){
                ItemTm selectedObjValue = newValue.getValue();
                txtItemCode.setEditable(false);
                txtItemCode.setText(selectedObjValue.getCode());
                txtItemDesc.setText(selectedObjValue.getDesc());
                txtItemPrice.setText(String.valueOf(selectedObjValue.getUnitPrice()));
                txtItemQTY.setText(String.valueOf(selectedObjValue.getQty()));
            }
    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();

        try {
            List<ItemDto> dtoList = itemModel.allItems();
            for (ItemDto dto:dtoList) {
                JFXButton deleteBtn = new JFXButton("Delete");
                deleteBtn.setStyle("-fx-background-color:#c91114;" +
                        "-fx-font-weight: BOLD"
                );
                ItemTm table = new ItemTm(
                        dto.getCode(),
                        dto.getDesc(),
                        dto.getUnitPrice(),
                        dto.getQty(),
                        deleteBtn
                );

                deleteBtn.setOnAction(actionEvent -> {
                    deleteItem(dto.getCode());
                });
                tmList.add(table);
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        TreeItem<ItemTm> treeItem = new RecursiveTreeItem<> (tmList,
                RecursiveTreeObject::getChildren);
        tblItem.setRoot(treeItem);
        tblItem.setShowRoot(false);
    }

    private void deleteItem(String code) {
        try {
            if (itemModel.deleteItem(code)){
                new Alert(Alert.AlertType.INFORMATION,"Item Deleted!").show();
                loadItemTable();
                clearFields();
            }else{
                new Alert(Alert.AlertType.ERROR,"Something went wrong!").show();
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

    @FXML
    void onActionSaveBtn(ActionEvent event) {
        try {
            boolean isSaved = itemModel.saveItem(new ItemDto(txtItemCode.getText(),
                    txtItemDesc.getText(),
                    Double.parseDouble(txtItemPrice.getText()),
                    Integer.parseInt(txtItemQTY.getText())
            ));
            if (isSaved){
                new Alert(Alert.AlertType.INFORMATION,"Item is Saved!").show();
                loadItemTable();
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
        try {
            boolean isUpdated = itemModel.updateItem(new ItemDto(txtItemCode.getText(),
                    txtItemDesc.getText(),
                    Double.parseDouble(txtItemPrice.getText()),
                    Integer.parseInt(txtItemQTY.getText())
            ));
            if (isUpdated){
                new Alert(Alert.AlertType.INFORMATION,"Item is Updated!").show();
                loadItemTable();
                clearFields();
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearFields() {
//        tblItem.refresh();
        txtItemCode.clear();
        txtItemCode.setEditable(true);
        txtItemDesc.clear();
        txtItemPrice.clear();
        txtItemQTY.clear();
    }

    public void onActionReloadBtn(ActionEvent actionEvent) {
        clearFields();
    }
}
