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

public class DatabaseTest {

   private DatabaseParsing testDB = new DatabaseParsing();

   private String inventoryFile = "src/test/resources/snack_database.json";
   private String transactionFile = "src/test/resources/test_transactions.json";

   @Test
   public void testInvalidFile() {
       // test invalid file
       String test = "src/test/resources/invalidFile.json";
       assertTrue(testDB.readFile(test).equals(new JSONObject()));
       // test valid file
       assertFalse(testDB.readFile(inventoryFile).equals(new JSONObject()));
   }

   @Test
   public void testStoreTransactions() {
    //    Map<String, List<Pair<Integer, Long>>> transactions = new HashMap<>();
    //    JSONObject transactionInfo = testDB.readFile(transactionFile);
    //    transactions = testDB.storeTransactions(transactionInfo);
    //    assertEquals(transactions.size(),5);
   }
}