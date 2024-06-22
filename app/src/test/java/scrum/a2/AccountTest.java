package scrum.a2;

import java.time.ZonedDateTime;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import javafx.util.Pair;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AccountTest {

    Account a = new Account("1234", true, "Shing", Long.valueOf(6444), "Customer");

    @Test 
    public void testRole() {
        assertEquals(a.getRole(), "Customer");
        a.setRole("Seller");
        assertEquals(a.getRole(), "Seller");
    }


    @Test 
    public void testPassword() {
        assertEquals(a.getPassword(), "1234");
        a.setPassword("4321");
        assertEquals(a.getPassword(), "4321");
    }   

    @Test
    public void testSavedCard() {
        assertEquals(a.isSavedCard(), true);
        a.setSavedCard(false);
        assertEquals(a.isSavedCard(), false);
    }   

    @Test
    public void testName() {
        assertEquals(a.getName(), "Shing");
        a.setName("who");
        assertEquals(a.getName(), "who");
    }

    @Test 
    public void testPin() {
        assertEquals(a.getPin(), Long.valueOf(6444));
        a.setPin(Long.valueOf(4666));
        assertEquals(a.getPin(), Long.valueOf(4666));
    }

}   


