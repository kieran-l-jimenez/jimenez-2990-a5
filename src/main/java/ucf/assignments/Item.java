/*
 * Item
 *
 * 2021-07-24
 *
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Kieran Jimenez
 */
package ucf.assignments;

public class Item {
    private Double monetaryValue;
    private String serialNumber;//XXXXXXXXXX (10 characters) where X can be either a letter or digit
    private String name;

    public Item() {
        this.monetaryValue = -1.0;
        this.serialNumber = "unfilledSN";
        this.name = "unfilledName";
    }

    public void setMonetaryValue(Double monetaryValue) {
        this.monetaryValue = monetaryValue;
    }

    public Double getMonetaryValue() {
        return monetaryValue;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
