/*
 * InventoryController
 *
 * 2021-07-25
 *
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Kieran Jimenez
 */
package ucf.assignments;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.converter.DoubleStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    @FXML private TableView<Item> inventoryTable;
    @FXML private TableColumn<Item, Double> valColumn;
    @FXML private TableColumn<Item, String> serColumn;
    @FXML private TableColumn<Item, String> nameColumn;
    @FXML private TextField inputValue;
    @FXML private TextField inputSerial;
    @FXML private TextField inputName;
    @FXML private Label errorLabel;

    private Inventory currentInventory;
    private FileChooser fileChooser;

    @FXML
    public void newItemPressed(ActionEvent actionEvent) {
        //if there are strings in every entry box
        if (!inputValue.getText().isEmpty() && !inputName.getText().isEmpty() && !inputSerial.getText().isEmpty()) {
            //call make item
            makeItem(Double.parseDouble(inputValue.getText()), inputSerial.getText(), inputName.getText());
            //select new item
            inventoryTable.getSelectionModel().select(currentInventory.getItemSerialIndex(inputSerial.getText()));
            inputValue.setText("");
            inputSerial.setText("");
            inputName.setText("");
        } else {
            errorLabel.setText("Fill every box before \nattempting to add a new item.");
        }
    }

    @FXML
    public void deleteItemPressed(ActionEvent actionEvent) {
        //if there is a selected item
        if (inventoryTable.getSelectionModel().getSelectedIndex() != -1) {
            //call remove item
            removeItem(inventoryTable.getSelectionModel().getSelectedItem());
        } else {
            errorLabel.setText("Select an item before \nattempting to delete an item");
        }
        //un-highlight selected item
        inventoryTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void editItemValuePressed(TableColumn.CellEditEvent editedCell) {
        //find selected item
        Item item = inventoryTable.getSelectionModel().getSelectedItem();
        //if value is different, try changing to new value
        if (Double.parseDouble(editedCell.getNewValue().toString()) != Double.parseDouble(editedCell.getOldValue().toString())) {
            try {
                currentInventory.editItemValue(item, Double.parseDouble(editedCell.getNewValue().toString()));
                errorLabel.setText("");
            } catch (Exception e) {
                //inform user of error
                errorLabel.setText("Input Error: New value must be a positive double.");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Change value to a different value \nthan that of the selected item.");
        }
        //clear selection
        inventoryTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void editItemSerialPressed(TableColumn.CellEditEvent editedCell) {
        //find selected item
        Item item = inventoryTable.getSelectionModel().getSelectedItem();
        //if serial is different, try edit item serial
        if (!editedCell.getNewValue().toString().equals(editedCell.getOldValue().toString())) {
            try {
                currentInventory.editItemSerialNumber(item, editedCell.getNewValue().toString());
                errorLabel.setText("");
            } catch (Exception e) {
                //inform user of error
                errorLabel.setText("Input Error: New serial number must be \n10 characters of a-z, A-Z, or 0-9.");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Change serial number to a different \nstring than that of the selected item.");
        }
        //clear selection
        inventoryTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void editItemNamePressed(TableColumn.CellEditEvent editedCell) {
        //find selected item
        Item item = inventoryTable.getSelectionModel().getSelectedItem();
        //if name is different, try change name
        if (!editedCell.getNewValue().toString().equals(editedCell.getOldValue().toString())) {
            try {
                currentInventory.editItemName(item, editedCell.getNewValue().toString());
                errorLabel.setText("");
            } catch (Exception e) {
                //inform user of error
                errorLabel.setText("Input Error: New name must be \n2-256 characters, inclusive.");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Change name to a different string \nthan that of the selected item.");
        }
        //clear selection
        inventoryTable.getSelectionModel().clearSelection();
    }

    @FXML
    public void searchSerialNumberPressed(ActionEvent actionEvent) {
        //call search, pass in serial num
        searchInventoryBySerialNumber(inputSerial.getText());
    }

    @FXML
    public void searchNamePressed(ActionEvent actionEvent) {
        //read inputName, call search
        searchInventoryByName(inputName.getText());
    }

    @FXML
    public void saveInventoryPressed(ActionEvent actionEvent) {
        //new stage, set title
        //open fileChooser
        //call appropriate save function based on file type
    }

    @FXML
    public void loadInventoryPressed(ActionEvent actionEvent) {
        //new stage, set title
        //open fileChooser
        //call loadList
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TSV", "*.txt"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML", "*.html"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        valColumn.setCellValueFactory(new PropertyValueFactory<>("monetaryValue"));
        valColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        serColumn.setCellValueFactory(new PropertyValueFactory<>("serialNumber"));
        serColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        errorLabel.setText("");

        currentInventory = new Inventory();
    }

    public void makeItem(double val, String text, String inputNameText) {
        //try adding item
        try {
            currentInventory.addItem(val, text, inputNameText);
            inventoryTable.setItems(currentInventory.getItemsObservable());
            errorLabel.setText("");
        } catch (Exception e) {
            //new window/text box informing user of error
            errorLabel.setText("Input Error: Double check all values.");
            e.printStackTrace();
        }

    }

    public void removeItem(Item selectedItem) {
        currentInventory.removeItem(selectedItem);
        inventoryTable.setItems(currentInventory.getItemsObservable());
    }

    public void searchInventoryBySerialNumber(String text) {
        //call getItemSerialIndex
        int tempIndex = currentInventory.getItemSerialIndex(text);
        //if not -1, change tableview selected row to returned index
        if (tempIndex != -1) {
            inventoryTable.getSelectionModel().select(currentInventory.getItemObject(tempIndex));
            inventoryTable.scrollTo(currentInventory.getItemObject(tempIndex));
            errorLabel.setText(String.format("Serial Number found in position %d.",
                    inventoryTable.getSelectionModel().getSelectedIndex()+1));
        } else {
            //if -1 returned then say not found
            errorLabel.setText(String.format("Serial Number \"%s\" not found.", text));
            inventoryTable.getSelectionModel().clearSelection();
        }
    }

    public void searchInventoryByName(String text) {
        //call getItemNameIndex
        int tempIndex = currentInventory.getItemNameIndex(text);
        //if not -1, change tableview selected row to returned index and scrollTo index
        if (tempIndex != -1) {
            inventoryTable.getSelectionModel().select(currentInventory.getItemObject(tempIndex));
            inventoryTable.scrollTo(currentInventory.getItemObject(tempIndex));
            errorLabel.setText(String.format("Item Name found in position %d.",
                    inventoryTable.getSelectionModel().getSelectedIndex()+1));
        } else {
            //if -1 returned then say not found
            errorLabel.setText(String.format("Item Name \"%s\" not found.", text));
            inventoryTable.getSelectionModel().clearSelection();
        }
    }

    public void saveTSVFormat() {
        //filewriter
        //new file
        //file print Column names(value, name, Serial Number) separated by tabs
        //loop that goes through each item
            //print value name serial separated by tabs
        //close file
    }

    public void saveHTMLFormat() {
        //filewriter
        //new file
        //file boiler plate <html>\n <body>\n <table>
        //file print column names <tr><th>value</th><th>name</th><th>serial number</th></tr>
        //loop that goes through each item
            //print <tr><td>item[i].value</td><td>item[i].name</td><td>item[i].serialNumber</td></tr> with appropriate '\n's
        //after loop print </table></body></html>
        //close file
    }

    public void saveJSONFormat() {
        //new gson
        //try filewriter filepath
        //gson.toJson(currentInventory, fileWriter)
    }

    public void loadInventory() {
        //check file format, use appropriate code block
        //file Reader
        //.txt - skip first line, while hasNextLine:scan data in order tokenized by '\t'
        //.html - skip to item list, when encountering <tr>, ready for new item, read between <td> for elements in order
            //call currentInventory addItem, if </tr> stop checking for items
        //.json - new Gson, currentInventory = gson.fromJson
        //close file Reader
    }
}
