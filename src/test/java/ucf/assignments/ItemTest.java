package ucf.assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void getMonetaryValue1() {
        //new item
        Item test = new Item(29.99, "0123456789", "ib");
        //set value
        test.setMonetaryValue(29.99);
        //assert value is expected
        assertEquals(29.99, test.getMonetaryValue());
    }

    @Test
    void getMonetaryValue2() {
        //new item
        Item test = new Item();
        //set value
        test.setMonetaryValue((double) 10);
        //assert value is expected
        assertEquals(10.00, test.getMonetaryValue());
    }

    @Test
    void getSerialNumber() {
        //new item
        Item test = new Item();
        //set serial number
        test.setSerialNumber("0123456789");
        //assert serial number is expected
        assertEquals("0123456789", test.getSerialNumber());
    }

    @Test
    void getName() {
        //new item
        Item test = new Item();
        //set name
        test.setName("Product Example #1");
        //assert name is expected
        assertEquals("Product Example #1", test.getName());
    }
}