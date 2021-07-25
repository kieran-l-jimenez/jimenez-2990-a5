package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void addItem1() {
        //create temp Inventory
        Inventory temp = new Inventory();
        //assert Throw when adding new item with wrong sized name
        assertThrows(Exception.class, () -> {
            temp.addItem(19.99, "0123456789", "a");
        });
    }

    @Test
    void addItem2() {
        //create temp Inventory
        Inventory temp = new Inventory();
        //assert Throw when adding new item with improper serial number format
        assertThrows(Exception.class, () -> {
            temp.addItem(19.99, "short", "generic Tech Device");
        });
    }

    @Test
    void addItem3() {
        //create temp Inventory
        Inventory temp = new Inventory();
        //add item
        try {
            temp.addItem(19.99, "0123456789", "generic Tech Device");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //assert Throw when adding new item with same serial number
        assertThrows(Exception.class, () -> {
            temp.addItem(19.99, "0123456789", "similar Tech Device");
        });
    }

    @Test
    void addItem4() {
        //create temp Inventory
        Inventory temp = new Inventory();
        //assert Throw when adding new item with negative value
        assertThrows(Exception.class, () -> {
            temp.addItem(-19.99, "0123456789", "similar Tech Device");
        });
    }

    @Test
    void editItemSerialNumber1() {
        //create temp Inventory
        Inventory temp = new Inventory();
        //add item
        try {
            temp.addItem(19.99, "0123456789", "generic Tech Device");
            //change serial number
            temp.editItemSerialNumber(temp.getItemObject(temp.getItemSerialIndex("0123456789")),
                    "1234567890");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //assert new serial matches item serial
        assertEquals("1234567890", temp.getItemObject(0).getSerialNumber());
    }

    @Test
    void editItemSerialNumber2() {
        //create temp Inventory
        Inventory temp = new Inventory();
        //add 2 items
        try {
            temp.addItem(19.99, "0123456789", "generic Tech Device");
            temp.addItem(19.99, "1234567890", "similar Tech Device");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //assert throw when changing 2nd item to have same serial as 1st
        assertThrows(Exception.class, () -> {
            temp.editItemSerialNumber(temp.getItemObject(temp.getItemNameIndex("similar Tech Device")),
                    "0123456789");
        });
    }

    @Test
    void editItemName() {
        //create temp Inventory
        Inventory temp = new Inventory();
        //add item
        //change item name
        //assert equal new name and item name
    }

    @Test
    void removeItem() {
        //create temp Inventory
        Inventory temp = new Inventory();
        //add 3 items
        //remove 2nd item
        //assert equal 3rd serial and new 2nd item(originally 3rd item)
    }
}