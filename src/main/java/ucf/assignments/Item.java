/*
 * Item
 *
 * 2021-07-25
 *
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Kieran Jimenez
 */
package ucf.assignments;

import javafx.beans.property.SimpleStringProperty;

public class Item {
    private Double monetaryValue;
    private SimpleStringProperty serialNumber;//XXXXXXXXXX (10 characters) where X can be either a letter or digit
    private SimpleStringProperty name;

    public Item() {
        this.monetaryValue = -1.0;
        this.serialNumber = new SimpleStringProperty();
        this.serialNumber.set("unfilledSN");
        this.name = new SimpleStringProperty();
        this.name.set("unfilledName");
    }

    public Item(Double value, String serial, String nameIn) {
        this.monetaryValue = value;
        this.serialNumber = new SimpleStringProperty();
        this.serialNumber.set(serial);
        this.name = new SimpleStringProperty();
        this.name.set(nameIn);
    }

    public void setMonetaryValue(Double monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public Double getMonetaryValue() {
        return monetaryValue;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber.set(serialNumber);
    }

    public String getSerialNumber() {
        return serialNumber.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }
}
