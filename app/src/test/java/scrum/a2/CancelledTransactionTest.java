package scrum.a2;

import java.time.ZonedDateTime;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CancelledTransactionTest {

    VendingMachine vendingMachine = new VendingMachine();
    DatabaseParsing testDB = new DatabaseParsing();
    private String transactionFile = "src/test/resources/test_transactions.json";

    Map<String, List<Transactions>> transactions = new HashMap<>();

    List<CancelledTransaction> cancelledTransactions = new ArrayList<>();
    CancelledTransaction t;

    @Before 
    public void setup() {

        String reason = "no money";
        String user = "shng6444";
        t = new CancelledTransaction(user, ZonedDateTime.now(), reason);

        cancelledTransactions = vendingMachine.getCancelledTransactionList();
        JSONObject transaction_info = testDB.readFile(transactionFile);
        transactions = testDB.storeTransactions(transaction_info);
    }

    @Test 
    public void testGetDate() {

        for (String user: transactions.keySet()) {
            if (user.equals("njac5013")) {
                Transactions tr = transactions.get(user).get(0);
                CancelledTransaction ct = new CancelledTransaction(user, tr.getDate(), "no money");
                cancelledTransactions.add(ct);
                break;
            }
        }   

        CancelledTransaction cancelled = cancelledTransactions.get(0);

        assertEquals(cancelled.getDate().toString(), "2022-10-24T19:51:31.023421+11:00[Australia/Sydney]");
        cancelled.setDate(ZonedDateTime.now());
        assertFalse(cancelled.getDate().toString() == "2022-10-24T19:51:31.023421+11:00[Australia/Sydney]");
    }

    @Test 
    public void testGetUser() {
        assertEquals(t.getUser(), "shng6444");
        t.setUser("njac5013");
        assertEquals(t.getUser(), "njac5013");
    }


    @Test 
    public void testGetReason() {
        assertEquals(t.getReason(), "no money");
        t.setReason("forgot money");
        assertEquals(t.getReason(), "forgot money");
    }

}   


