package scrum.a2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;
import javafx.util.Pair;

public class ItemTest {

    private Item item;

    @Test 
    public void testItemName() {
        item = new Item("Sprite",  2.50, Long.valueOf(7), "Drinks");
        assertEquals(item.getName(), "Sprite");
        item.setName("Pepsi");
        assertEquals(item.getName(), "Pepsi");
    }

    @Test
    public void testItemQuantity() {
        item = new Item("Sprite",  2.50, Long.valueOf(7), "Drinks");
        assertEquals(item.setQuantity(Long.valueOf(8)), true);
        assertEquals(item.getQuantity(), Long.valueOf(8));
        assertEquals(item.setQuantity(Long.valueOf(20)), false);
    }

    @Test 
    public void testItemPrice() {
        item = new Item("Sprite",  2.50, Long.valueOf(7), "Drinks");
        assertEquals(item.getPrice(), Double.valueOf(2.50));
        item.setPrice(3.50);
        assertEquals(item.getPrice(), Double.valueOf(3.50));
    }

    @Test
    public void testItemCategory() {
        item = new Item("Sprite",  2.50, Long.valueOf(7), "Drinks");
        assertEquals(item.getCategory(), "Drinks");
        assertFalse(item.setCategory("InvalidCat"));
        assertTrue(item.setCategory("drINKS")); 
        assertTrue(item.setCategory("Drinks")); 
    }

    @Test 
    public void testQuantitySold() {
        item = new Item("Sprite",  2.50, Long.valueOf(7), "Drinks");
        assertEquals(item.getQuantitySold(), Long.valueOf(0));
        item.setQuantitySold(Long.valueOf(1));
        assertEquals(item.getQuantitySold(), Long.valueOf(1));
    }

}