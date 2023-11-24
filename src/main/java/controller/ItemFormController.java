package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import db.DBConnection;
import dto.ItemDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import dto.tm.ItemTm;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;

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

    public void initialize(){
        colCode.setCellValueFactory(new TreeItemPropertyValueFactory<>("code"));
        colDesc.setCellValueFactory(new TreeItemPropertyValueFactory<>("desc"));
        colPrice.setCellValueFactory(new TreeItemPropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new TreeItemPropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new TreeItemPropertyValueFactory<>("deleteBtn"));
        loadItemTable();

    }

    private void loadItemTable() {
        ObservableList<ItemTm> tmList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM Item";

        try {
            Statement stm = DBConnection.getInstance().getConnection().createStatement();
            ResultSet resultSet = stm.executeQuery(sql);
            while (resultSet.next()){
                JFXButton deleteBtn = new JFXButton("Delete");
                deleteBtn.setStyle("-fx-background-color:#cf0f12;" +
                        "-fx-font-weight: BOLD"
                );

                ItemTm tm = new ItemTm(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4),
                        deleteBtn
                );
                tmList.add(tm);
            }
            TreeItem<ItemTm> treeItem = new RecursiveTreeItem<> (tmList,
                    RecursiveTreeObject::getChildren);
            tblItem.setRoot(treeItem);
            tblItem.setShowRoot(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
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
        ItemDto dto = new ItemDto(txtItemCode.getText(),
                txtItemDesc.getText(),
                Double.parseDouble(txtItemPrice.getText()),
                Integer.parseInt(txtItemQTY.getText())
        );
        String sql = "INSERT INTO Item VALUES(?,?,?,?)";
        try {
            PreparedStatement pstm = DBConnection.getInstance().getConnection().prepareStatement(sql);
            pstm.setString(1,
                    dto.getCode());
            pstm.setString(2,
                    dto.getDesc());
            pstm.setDouble(3,
                    dto.getUnitPrice());
            pstm.setInt(4,
                    dto.getQty());
            int result = pstm.executeUpdate();
            if (result>0){
                new Alert(Alert.AlertType.INFORMATION,"Item Saved!").show();
            }

        }catch (SQLIntegrityConstraintViolationException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Duplicate Entry!").show();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionUpdateBtn(ActionEvent event) {

    }

}
