package controller;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.tm.ItemTm;

import java.io.IOException;

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

    }

    @FXML
    void onActionBackBtn(ActionEvent event) {
        Stage stage = (Stage) pane.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashboardFrom.fxml"))));
            stage.setTitle("Welcome");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onActionSaveBtn(ActionEvent event) {

    }

    @FXML
    void onActionUpdateBtn(ActionEvent event) {

    }

}
