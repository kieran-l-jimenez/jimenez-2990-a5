/*
 * InventoryController
 *
 * 2021-07-25
 *
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Kieran Jimenez
 */
package ucf.assignments;

import com.google.gson.Gson;
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
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;

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
        //new stage
        Stage stage = new Stage();
        //open fileChooser
        fileChooser.setTitle("Save Inventory");
        File file = fileChooser.showSaveDialog(stage);
        //call appropriate save function based on file type
        //fileChooser.showSaveDialog().isFile() boolean that returns if path is file
        try {
            switch (Files.probeContentType(file.toPath())) {
                case "text/plain" -> saveTSVFormat(file);
                case "text/html" -> saveHTMLFormat(file);
                case "application/json" -> saveJSONFormat(file);
                default -> saveJSONFormat(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadInventoryPressed(ActionEvent actionEvent) {
        //new stage, set title
        Stage stage = new Stage();
        //open fileChooser
        fileChooser.setTitle("Load Inventory");
        //call loadInventory
        loadInventory(fileChooser.showOpenDialog(stage));
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

    public void saveTSVFormat(File file) {
        //file writer
        try (FileWriter fileWriter = new FileWriter(file.getPath())) {
            //file write Column names(value, name, Serial Number) separated by tabs
            fileWriter.write(String.format("Value\tSerial Number\tName\n"));
            //loop that goes through each item
            for (Item item : currentInventory.getItemsObservable()) {
                //print value name serial separated by tabs
                fileWriter.write(String.format("%.2f\t%s\t%s\n", item.getMonetaryValue(), item.getSerialNumber(),
                        item.getName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveHTMLFormat(File file) {
        //file writer
        try (FileWriter fileWriter = new FileWriter(file.getPath())) {
            //file boiler plate <html>\n <body>\n <table>
            fileWriter.write("<!DOCTYPE html>\n<html>\n<body>\n<table>\n");
            //file print column names <tr><th>value</th><th>name</th><th>serial number</th></tr>
            fileWriter.write("\t<tr>\n\t\t<th>Value</th>\n\t\t<th>Serial Number</th>\n\t\t<th>Name</th>\n\t</tr>\n");

            //loop that goes through each item
            for (Item item : currentInventory.getItemsObservable()) {
                //print <tr><td>item[i].value</td><td>item[i].name</td><td>item[i].serialNumber</td></tr> with appropriate '\n's
                fileWriter.write("\t<tr>\n");
                fileWriter.write(String.format("\t\t<td>%s</td>\n", item.getMonetaryValue().toString()));
                fileWriter.write(String.format("\t\t<td>%s</td>\n", item.getSerialNumber()));
                fileWriter.write(String.format("\t\t<td>%s</td>\n", item.getName()));
                fileWriter.write("\t</tr>\n");
            }
            //after loop print </table></body></html>
            fileWriter.write("</table>\n</body>\n</html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveJSONFormat(File file) {
        //new gson
        Gson gson = new Gson();
        //try file writer filepath
        try (FileWriter fileWriter = new FileWriter(file.getPath())) {
            gson.toJson(currentInventory, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadInventory(File file) {
        //check file format using File.probeContentType, use appropriate code block
        try {
            /*for (Item item : currentInventory.getItemsObservable()) {
                currentInventory.removeItem(item);
            }*/

            inventoryTable.getItems().clear();
            switch (Files.probeContentType(file.toPath())) {
                //file Reader, File.readAllLines file path if not json, stream needs to be closed unless declared in resource specification header
                case "text/plain":
                    //.txt - skip first line, while hasNextLine:scan data in order tokenized by '\t'
                    List<String> textArray = Files.readAllLines(file.toPath());

                    Iterator iterator = textArray.listIterator();
                    iterator.next();

                    while (iterator.hasNext()) {
                        String[] pieces = iterator.next().toString().split("\t");
                        currentInventory.addItem(Double.parseDouble(pieces[0]), pieces[1], pieces[2]);
                    }
                    break;
                case "text/html":
                    //.html - skip to item list (skip first 4 lines), when encountering <tr>, ready for new item, read between <td> for elements in order
                        //call currentInventory addItem, if </tr> stop checking for items
                    List<String> htmlArray = Files.readAllLines(file.toPath());

                    Iterator iteratorHTML = htmlArray.listIterator();


                    for (int i = 0; i < 9; i++) {
                        iteratorHTML.next();
                    }

                    while (!iteratorHTML.next().equals("</table>")) {//<tr>
                        String[] piecesHTML = new String[3];

                        String[] tempTokens = iteratorHTML.next().toString().split("<|>", 6);
                        piecesHTML[0] = tempTokens[2];

                        tempTokens = iteratorHTML.next().toString().split("<|>", 6);
                        piecesHTML[1] = tempTokens[2];

                        tempTokens = iteratorHTML.next().toString().split("<|>", 6);
                        piecesHTML[2] = tempTokens[2];

                        iteratorHTML.next();//<tr>

                        currentInventory.addItem(Double.parseDouble(piecesHTML[0]), piecesHTML[1], piecesHTML[2]);
                    }
                    break;
                case "application/json" :
                    //.json - new Gson, currentInventory = gson.fromJson
                    FileReader fileReader = new FileReader(file.getPath());
                    Gson gson = new Gson();
                    currentInventory = gson.fromJson(fileReader, Inventory.class);
                    break;
            }

            inventoryTable.getSelectionModel().clearSelection();
            inputValue.setText("");
            inputSerial.setText("");
            inputName.setText("");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
