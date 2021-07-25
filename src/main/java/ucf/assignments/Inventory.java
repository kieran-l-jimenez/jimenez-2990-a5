/*
 * Inventory
 *
 * 2021-07-25
 *
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Kieran Jimenez
 */
package ucf.assignments;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> itemsArrayList;
    private ObservableList<Item> itemsObservable;

    public Inventory() {
        itemsArrayList = new ArrayList<>();
        itemsObservable = FXCollections.observableArrayList();
    }

    public void addItem(Double val, String serNum, String name) throws Exception {
        //check that all arguments fit parameters
        if (this.checkParameters(val, serNum, name) || this.checkConflict(serNum)) {
            throw new Exception("Improper Item Input");
        }
        //declare temp Item variable with arguments
        Item temp = new Item(val, serNum, name);
        //add temp to Inventory array list
        itemsArrayList.add(temp);
    }

    public int getItemSerialIndex(String serNum) {
        //loop through every array list element, if serial matches return index
        for (Item currentItem : itemsArrayList) {
            if (currentItem.getSerialNumber().equals(serNum)) {
                return itemsArrayList.indexOf(currentItem);
            }
        }
        //if the element was not found, return -1
        return -1;
    }

    public int getItemNameIndex(String name) {
        //loop through every array list element, if name matches return index
        for (Item currentItem : itemsArrayList) {
            if (currentItem.getName().equals(name)) {
                return itemsArrayList.indexOf(currentItem);
            }
        }
        //if the element was not found, return -1
        return -1;
    }

    public Item getItemObject(int index) {
        return this.itemsArrayList.get(index);
    }

    public void editItemValue(Item pendingItem, Double newVal) throws Exception {
        if (this.checkParameters(newVal, pendingItem.getSerialNumber(), pendingItem.getName())) {
            throw new Exception("Improper Item Input Value");
        }
        pendingItem.setMonetaryValue(newVal);
    }

    public ObservableList<Item> getItemsObservable() {
        itemsObservable.clear();
        itemsObservable.addAll(itemsArrayList);
        return itemsObservable;
    }

    public void editItemSerialNumber(Item pendingItem, String newSerNum) throws Exception {
        if (this.checkParameters(pendingItem.getMonetaryValue(), newSerNum, pendingItem.getName())
                || this.checkConflict(newSerNum)) {
            throw new Exception("Improper Item Input Serial");
        }
        pendingItem.setSerialNumber(newSerNum);
    }

    public void editItemName(Item pendingItem, String newName) throws Exception {
        if (this.checkParameters(pendingItem.getMonetaryValue(), pendingItem.getSerialNumber(), newName)) {
            throw new Exception("Improper Item Input Name");
        }
        pendingItem.setName(newName);
    }

    public void removeItem(Item pendingItem) {
        itemsArrayList.remove(pendingItem);
    }

    private boolean checkConflict(String serNum) {
        //loop through every item
        //if it is found, return true, there is a conflict, serial num already used
        for (Item currentItem : itemsArrayList) {
            if (currentItem.getSerialNumber().equals(serNum)) {
                return true;
            }
        }
        //else false, no conflict
        return false;
    }

    private boolean checkParameters(Double val, String serNum, String name) {
        //return true if there is a problem
        return val < 0 || !serNum.matches("\\w\\w\\w\\w\\w\\w\\w\\w\\w\\w") || name.length() < 2
                || name.length() > 256;
    }
}
