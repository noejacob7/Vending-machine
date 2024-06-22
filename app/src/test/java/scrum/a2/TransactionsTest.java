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

public class TransactionsTest {

    VendingMachine vendingMachine = new VendingMachine();
    DatabaseParsing testDB = new DatabaseParsing();
    private String transactionFile = "src/test/resources/test_transactions.json";

    Map<String, List<Transactions>> transactions = new HashMap<>();
    Transactions t;

    @Before 
    public void setup() {

        Map<Integer, Integer> itemsSold = new HashMap<>();
        Double amountPaid = 3.0;
        Double returnedChange = 1.0;
        String paymentMethod = "Cash";
        t = new Transactions(ZonedDateTime.now(), itemsSold, amountPaid, returnedChange, paymentMethod);

        JSONObject transaction_info = testDB.readFile(transactionFile);
        transactions = testDB.storeTransactions(transaction_info);
    }

    @Test 
    public void testGetDate() {
        ZonedDateTime date = ZonedDateTime.now();
        for (String user: transactions.keySet()) {
            if (user.equals("njac5013")) {
                Transactions tr = transactions.get(user).get(0);
                date = tr.getDate();
                assertEquals(tr.getDate().toString(), "2022-10-24T19:51:31.023421+11:00[Australia/Sydney]");
                break;
            }
        }

        t.setDate(ZonedDateTime.now());
        assertFalse(ZonedDateTime.now() == date);
    }

    @Test 
    public void testGetItemsSold() {
        Map<Integer, Integer> testItemsSold = new HashMap<>();
        assertEquals(t.getItemsSold().size(), 0);
        testItemsSold.put(1001, 3);
        t.setItemsSold(testItemsSold);
        assertEquals(t.getItemsSold().size(), 1);
    }

    @Test
    public void testGetAmountPaid() {
        assertEquals(t.getAmountPaid(), Double.valueOf(3.00));
        t.setAmountPaid(2.0);
        assertEquals(t.getAmountPaid(), Double.valueOf(2.00));
    }

    @Test 
    public void testGetReturnedChange() {
        assertEquals(t.getReturnedChange(), Double.valueOf(1.00));
        t.setReturnedChange(4.0);
        assertEquals(t.getReturnedChange(), Double.valueOf(4.00));
    }

    @Test
    public void testGetPaymentMethod() {
        assertTrue(t.getPaymentMethod().equals("Cash"));
        t.setPaymentMethod("Card");
        assertTrue(t.getPaymentMethod().equals("Card"));
    }
}   


