/*
 * InventoryController
 *
 * 2021-07-24
 *
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Kieran Jimenez
 */
package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    @FXML private TableView inventoryTable;
    @FXML private TextField inputValue;
    @FXML private TextField inputSerial;
    @FXML private TextField inputName;

    Inventory currentInventory;
    FileChooser fileChooser;

    @FXML
    public void newItemPressed(ActionEvent actionEvent) {

    }

    @FXML
    public void deleteItemPressed(ActionEvent actionEvent) {

    }

    @FXML
    public void searchSerialNumberPressed(ActionEvent actionEvent) {

    }

    @FXML
    public void searchNamePressed(ActionEvent actionEvent) {

    }

    @FXML
    public void saveInventoryPressed(ActionEvent actionEvent) {

    }

    @FXML
    public void loadInventoryPressed(ActionEvent actionEvent) {

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TSV", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML", "*.html"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

    }

    public void makeItem() {

    }

    /*public void editItemName() {

    }

    public void editItemValue() {

    }*/

    public Item searchInventoryBySerialNumber() {
        return new Item();
    }

    public Item searchInventoryByName() {
        return new Item();
    }

    public void saveTSVFormat() {

    }

    public void saveHTMLFormat() {

    }

    public void saveJSONFormat() {

    }

    public void loadInventory() {

    }
}
