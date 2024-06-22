
package scrum.a2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;
import javafx.util.Pair;

public class VendingMachineTest {

    private VendingMachine vendingMachine = new VendingMachine();
    private DatabaseParsing testDB = new DatabaseParsing();

    private String inventoryFile = "src/test/resources/snack_database.json";
    private String accountFile = "src/test/resources/account_data.json";
    private String transactionFile = "src/test/resources/test_transactions.json";
    private String cash_reserve_file = "src/test/resources/cash_reserve.json";
    private String card_reserve_file = "src/test/resources/credit_cards.json";

    Map<Integer, Item> items = new HashMap<>();
    Map<String, Account> accounts = new HashMap<>();
    Map<Double, Long> cashReserve = new TreeMap<Double, Long>();
    Map<String, List<Transactions>> transactions = new HashMap<>();
    Map<String, Integer> cardReserve;

    List<CancelledTransaction> cancelledTransaction = new ArrayList<>();

    @Before
    public void setup() {
        
        JSONObject inventory = testDB.readFile(inventoryFile);
        items = testDB.storeInventory(inventory);

        JSONObject accountInfo = testDB.readFile(accountFile);
        accounts = testDB.storeLoginDetails(accountInfo);

        JSONObject transactionInfo = testDB.readFile(transactionFile);
        transactions = testDB.storeTransactions(transactionInfo);
        vendingMachine.setTransactions(transactions);

        JSONObject cash_reserve = testDB.readFile(cash_reserve_file);
        cashReserve = testDB.storeCashReserve(cash_reserve);
        vendingMachine.setCashReserve(cashReserve);

        cardReserve = testDB.storeCardReserve(card_reserve_file);
        
    }

    @Test
    public void testGetDrinks() {
        Map<Integer, Item> drinks = vendingMachine.getDrinks();

        Map<Integer, Item> testDrinks = new HashMap<>();

        Item item1001 = new Item("Mineral Water", 1.00, Long.valueOf(7), "Drinks");
        Item item1002 = new Item("Sprite", 2.50, Long.valueOf(7), "Drinks");
        Item item1003 = new Item("Coca cola", 1.00, Long.valueOf(7), "Drinks");
        Item item1004 = new Item("Pepsi", 2.50, Long.valueOf(7), "Drinks");
        Item item1005 = new Item("Juice", 2.50, Long.valueOf(7), "Drinks");
        testDrinks.put(1001, item1001);
        testDrinks.put(1002, item1002);
        testDrinks.put(1003, item1003);
        testDrinks.put(1004, item1004);
        testDrinks.put(1005, item1005);

        assertTrue(testDrinks.keySet().equals(drinks.keySet()));
    }

    @Test
    public void testGetChocolates() {
        Map<Integer, Item> choco = vendingMachine.getChocolates();

        Map<Integer, Item> testChocolates = new HashMap<>();

        Item item1006 = new Item("Mars", 2.00, Long.valueOf(7), "Chocolates");
        Item item1007 = new Item("M&M", 2.00, Long.valueOf(7), "Chocolates");
        Item item1008 = new Item("Bounty", 3.00, Long.valueOf(7), "Chocolates");
        Item item1009 = new Item("Snickers", 2.50, Long.valueOf(7), "Chocolates");
        testChocolates.put(1006, item1006);
        testChocolates.put(1007, item1007);
        testChocolates.put(1008, item1008);
        testChocolates.put(1009, item1009);

        assertTrue(testChocolates.keySet().equals(choco.keySet()));

    }

    @Test
    public void testGetChips() {
        Map<Integer, Item> chips = vendingMachine.getChips();

        Map<Integer, Item> testChips = new HashMap<>();

        Item item1010 = new Item("Smiths", 3.00, Long.valueOf(7), "Chips");
        Item item1011 = new Item("Pringles", 4.50, Long.valueOf(7), "Chips");
        Item item1012 = new Item("Kettle", 3.50, Long.valueOf(7), "Chips");
        Item item1013 = new Item("Thins", 2.50, Long.valueOf(7), "Chips");

        testChips.put(1010, item1010);
        testChips.put(1011, item1011);
        testChips.put(1012, item1012);
        testChips.put(1013, item1013);

        assertTrue(testChips.keySet().equals(chips.keySet()));
    }

    @Test
    public void testGetCandies() {
        Map<Integer, Item> candies = vendingMachine.getCandies();

        Map<Integer, Item> testCandies = new HashMap<>();

        Item item1014 = new Item("Mentos", 1.50, Long.valueOf(7), "Candies");
        Item item1015 = new Item("Sour Patch", 1.50, Long.valueOf(7), "Candies");
        Item item1016 = new Item("Skittles", 1.00, Long.valueOf(7), "Candies");

        testCandies.put(1014, item1014);
        testCandies.put(1015, item1015);
        testCandies.put(1016, item1016);

        assertTrue(testCandies.keySet().equals(candies.keySet()));
    }

    @Test
    public void testCheckLogin() {

        Pair<Boolean, String> message = vendingMachine.checkLogin("shng6444", "1234");
        Pair<Boolean, String> testMessage = new Pair<>(true,"CUSTOMER");
        assertTrue(testMessage.equals(message));

        Pair<Boolean, String> messageInvalid = vendingMachine.checkLogin("invaliduser", "invalidpassword");
        Pair<Boolean, String> testMessageInvalid = new Pair<>(false, "Username does not exist!");
        assertTrue(messageInvalid.equals(testMessageInvalid));

        Pair<Boolean, String> messageInvalidPW = vendingMachine.checkLogin("njac5013", "000");
        Pair<Boolean, String> testMessageInvalidPW = new Pair<>(false, "Invalid Password!");
        assertTrue(testMessageInvalidPW.equals(messageInvalidPW));

    }

    @Test
    public void testAddLogin() {

        Pair<Boolean, String> message = vendingMachine.addLogin("newUser", "pwpwpw", "pwpwpw");
        Pair<Boolean, String> testMessage = new Pair<>(true, "Customer");
        assertTrue(testMessage.equals(message));

        Pair<Boolean, String> messagePWdontMatch = vendingMachine.addLogin("newuser", "510207807", "invalidpassword");
        // Pair<Boolean, String> testMessagePWdontMatch = new Pair<>(false, "Cannot use \"anonymous\" as a username");
        Pair<Boolean, String> testMessagePWdontMatch = new Pair<>(false, "The passwords do not match");
        assertTrue(messagePWdontMatch.equals(testMessagePWdontMatch));

        Pair<Boolean, String> messageExisting = vendingMachine.addLogin("njac5013", "510207807", "510207807");
        Pair<Boolean, String> testMessageExisting = new Pair<>(false, "Username already exists");
        assertTrue(messageExisting.equals(testMessageExisting));

        Pair<Boolean, String> messageAnon = vendingMachine.addLogin("anonymous", "pwwww", "pwwww");
        Pair<Boolean, String> testMessageAnon= new Pair<>(false, "Cannot use \"anonymous\" as a username");
        assertTrue(messageAnon.equals(testMessageAnon));

        Pair<Boolean, String> message1Charac = vendingMachine.addLogin("user", "", "");
        Pair<Boolean, String> testMessage1Charac = new Pair<>(false, "Need to have at least one character in the password");
        assertTrue(message1Charac.equals(testMessage1Charac));

    }


    @Test
    public void testSetUsernameAnon() {
        vendingMachine.setUser("anonymous");
        assertEquals(vendingMachine.getUser(), "anonymous");
    }

    @Test
    public void testGetTransactions() {
        vendingMachine.setUser(null);
        assertNull(vendingMachine.getTransactions());

        // no transactions 
        // vendingMachine.setUser("dummy6444");
        // assertNull(vendingMachine.getTransactions());

        vendingMachine.setUser("anonymous");
        List<Pair<Item,Long>> transactionsTestAnon = vendingMachine.getTransactions();
        assertEquals(transactionsTestAnon.size(), 4); // check if the getTransactions returns only last 5 items

        vendingMachine.setUser("njac5013");
        List<Pair<Item, Long>> transctionUser1 = vendingMachine.getTransactions();
        assertEquals(transctionUser1.size(), 5);
    }

    @Test
    public void testCheckoutWithCash() {
        Map<Double, Long> cash =  new HashMap<>();

        Pair<Boolean, String> insuffMoneyMessage = new Pair<>(false, "Insufficient money");
        assertEquals(Double.valueOf(0), vendingMachine.getTotal());
        Double v = vendingMachine.calculateMoney(cash);
        assertEquals(v, Double.valueOf(0));

        vendingMachine.addItemToCart(1001);
        assertEquals(Double.valueOf(1.00), vendingMachine.getTotal()); // price to pay is $1.00
        assertEquals(insuffMoneyMessage, vendingMachine.checkoutWithCash(cash)); // user currently put 0.00

        Map<Double, Long> cash2 =  new HashMap<>();
        vendingMachine.returnItem(1001);
        vendingMachine.addItemToCart(1001);
        vendingMachine.addItemToCart(1001);
        vendingMachine.addItemToCart(1001);

        cash2.put(Double.valueOf(1.00), Long.valueOf(5)); // user put $5
        cash2.put(Double.valueOf(2.00), Long.valueOf(1)); // user put $2
        assertEquals(Double.valueOf(3.00), vendingMachine.getTotal()); // need to pay $3.00

        Map<Double, Long> changeTest = new HashMap<>();
        assertEquals(null, vendingMachine.getChange());
        Double checkMoney = vendingMachine.calculateMoney(cash2);
        assertEquals(checkMoney, Double.valueOf(7));
        assertEquals(true, vendingMachine.calculateChange(checkMoney, cash2));
        assertNotNull(vendingMachine.getChange());

        // changeTest.put(Double.valueOf(2.00), Long.valueOf(2));
        // assertEquals(changeTest, vendingMachine.getChange());
        // assertEquals(Double.valueOf(4.00), vendingMachine.calculateMoney(changeTest));

        Pair<Boolean, String> successMsg = new Pair<>(true, "Successful! Collect your change and items from the Vending Machine");
        assertEquals(successMsg, vendingMachine.checkoutWithCash(cash2));
        // check that the amount entered is 7 after running checkout with cash method
        assertEquals(vendingMachine.getAmountEntered(), Double.valueOf(7));

        // Map<Double, Long> changeTest = new HashMap<>();
        // changeTest.put(Double.valueOf(2.00), Long.valueOf(2));
        // assertEquals(Double.valueOf(4.00), vendingMachine.calculateMoney(changeTest));

        vendingMachine.returnItem(1001);
        vendingMachine.returnItem(1001);
        vendingMachine.returnItem(1001);

        vendingMachine.addItemToCart(1001); // $1

        Pair<Boolean, String> unsuccessFul = new Pair<>(false, "The machine doesn't have enough change. Please insert other notes/coins");
        Map<Double, Long> cashNew = new HashMap<>();
        cashNew.put(Double.valueOf(20.00), Long.valueOf(1)); // user put $20.00
        assertEquals(unsuccessFul, vendingMachine.checkoutWithCash(cashNew));

        vendingMachine.returnItem(1001);

        // vendingMachine.
        // Map<Double, Long> changeTest = new HashMap<>();
        // changeTest.put(Double.valueOf(5.00), Long.valueOf(1));
        // assertEquals(changeTest, vendingMachine.getChange());



    }


    @Test
    public void testCalculateMoney() {
        Map<Double, Long> cash =  new HashMap<>();
        cash.put(Double.valueOf(0.01), Long.valueOf(0)); // 0
        cash.put(Double.valueOf(0.05), Long.valueOf(6)); // 0.30
        cash.put(Double.valueOf(0.10), Long.valueOf(2)); // 0.20
        cash.put(Double.valueOf(0.50), Long.valueOf(4)); // 2.00
        cash.put(Double.valueOf(1.00), Long.valueOf(1)); // 1.00
        cash.put(Double.valueOf(2.00), Long.valueOf(1)); // 2.00
        // notes
        cash.put(Double.valueOf(10.00), Long.valueOf(2)); // 20.00
        cash.put(Double.valueOf(100.00), Long.valueOf(1)); // 100.00
        cash.put(Double.valueOf(50.00), Long.valueOf(1)); // 50.00
        Double v = vendingMachine.calculateMoney(cash);
        assertEquals(v, Double.valueOf(175.50));
    }




    @Test
    public void testAddItemToCart() {
        assertEquals(vendingMachine.getCart().size(), 0);
        Pair<Boolean, String> msg = vendingMachine.addItemToCart(1001);
        assertEquals(vendingMachine.getCart().size(), 1);
        Pair<Boolean, String> testmsg = new Pair<>(true, "Successfully added to cart!!");
        assertEquals(msg, testmsg);

        // add existing item again (if cart containsKey(code))
        Pair<Boolean, String> msgTwo = vendingMachine.addItemToCart(1001);
        assertEquals(vendingMachine.getCart().size(), 1);
        Pair<Boolean, String> testmsgTwo = new Pair<>(true, "Successfully added to cart!!");
        assertEquals(msgTwo, testmsgTwo);

        Pair<Boolean, String> msgInvalid = vendingMachine.addItemToCart(999);

        Pair<Boolean, String> testmsgInvalidItem = new Pair<>(false, "No such item :(");
        Pair<Boolean, String> testmsgNotInStock = new Pair<>(false, "Item not in stock :(");

        // add item 1001 until vending machine doesnt have anymore item 1001-> quanity is 0
        vendingMachine.addItemToCart(1001);
        vendingMachine.addItemToCart(1001);
        vendingMachine.addItemToCart(1001);
        vendingMachine.addItemToCart(1001);
        vendingMachine.addItemToCart(1001);
        Pair<Boolean, String> msg0Items = vendingMachine.addItemToCart(1001);

        assertEquals(msgInvalid, testmsgInvalidItem);
        assertEquals(msg0Items, testmsgNotInStock);
    }

    @Test
    public void testShowCart() {
        vendingMachine.addItemToCart(1001);
        vendingMachine.addItemToCart(1016);
        Map<Item, Integer> showCartTestMap = new HashMap<>();
        showCartTestMap =  vendingMachine.showCart();
        assertEquals(showCartTestMap.size(), 2);
        assertEquals(vendingMachine.getTotal(), Double.valueOf(2.00));
    }

    // 
    @Test
    public void testReturnItem() {
        vendingMachine.addItemToCart(1002);
        Pair<Boolean, String> valid = vendingMachine.returnItem(1002);
        assertTrue(vendingMachine.getCart().size() == 0);

        Pair<Boolean, String> testValid = new Pair<>(true, "Successfully removed from cart!!");
        assertEquals(valid, testValid);

        Pair<Boolean, String> invalid = vendingMachine.returnItem(1002);
        Pair<Boolean, String> testInvalid = new Pair<>(false, "Item not in cart yet");
        assertEquals(invalid, testInvalid);
    }


    @Test
    public void testGetTotalItems() {
        // get user cart items
        vendingMachine.addItemToCart(1001);
        vendingMachine.addItemToCart(1001);
        assertEquals(2, vendingMachine.getTotalItems());
    }

    @Test 
    public void testChangeRole() {
        Pair<Boolean, String> testSuccess = new Pair<>(true, String.format("Successful changed role of %s to %s", "njac5013", "Seller"));
        assertEquals(testSuccess, vendingMachine.changeRole("njac5013", "Seller"));
        Pair<Boolean, String> testSameRole = new Pair<>(false, "Role remains the same. User already has this role.");
        assertEquals(testSameRole, vendingMachine.changeRole("njac5013", "Seller"));
        
        // Pair<Boolean, String> testunSuccess = new Pair<>(false, String.format("User %s not found", "xxx"));
        Pair<Boolean, String> testunSuccess = new Pair<>(false, "User not found.");
        assertEquals(testunSuccess, vendingMachine.changeRole("xxx", "Seller"));

        Pair<Boolean, String> testunSuccess2 = new Pair<>(false, "You can't change your own role.");
        vendingMachine.setUser("njac5013");
        assertEquals(testunSuccess2, vendingMachine.changeRole("njac5013", "njac5013"));
    }   

    @Test 
    public void testCancelTransaction() {
        vendingMachine.setUser("shng6444");
        vendingMachine.cancelTransaction("random reason");
        assertEquals(vendingMachine.getCancelledTransactionList().size(), 1);
    }


    @Test 
    public void testGetCashReserve() {
        // size 10 after removing the $100.00 currency in cash reserve
        int checkSizeVendingMachine = vendingMachine.getCashReserve().size();
        assertEquals(10,checkSizeVendingMachine);
    }   


    // test cashier modify currency quantity function
    @Test 
    public void testModifyCurrencyQuantity() {

        Double currency = 0.05;
        long newQuantity = 5;
        Pair<Boolean,String> unsuccessmsg = new Pair<>(false, String.format("Quantity for currency $%f remains the same.", currency));
        assertEquals(vendingMachine.modifyCurrencyQuantity(currency, newQuantity), unsuccessmsg);
        
        currency = 0.10;
        newQuantity = 1;
        Pair<Boolean,String> successmsg = new Pair<>(true, String.format("Successful. For currency $%f, changed quantity to %d.", currency, newQuantity));
        assertEquals(vendingMachine.modifyCurrencyQuantity(currency, newQuantity), successmsg);

        // test contains key
        Pair<Boolean,String> unsuccessmsg2 = new Pair<>(false, "");
        currency = 200.00;
        assertEquals(vendingMachine.modifyCurrencyQuantity(currency, newQuantity), unsuccessmsg2);

    }

    /**
    Test Seller methods: DONT DELETE, change s to vendingMachine
     */

    @Test
    public void testModifyProduct() {
        Integer oldItemCode = 1001;
        String newItemCode = "1112";
        String newProductName = "TestNewDrink";
        String newPrice = "7.00";
        Long newQuantity = Long.valueOf(2);
        Pair<Boolean,String> successmsg = new Pair<>(true, "Successfully modified item product");
        assertEquals(vendingMachine.modifyProduct(oldItemCode, newItemCode, newProductName, newPrice, newQuantity), successmsg);

        Pair<Boolean,String> unsuccessmsg1 = new Pair<>(false, "Item code must be an integer");
        // test new item code not an integer 
        assertEquals(vendingMachine.modifyProduct(oldItemCode, "", newProductName, newPrice, newQuantity), unsuccessmsg1);
        assertEquals(vendingMachine.modifyProduct(oldItemCode, "1.90", newProductName, newPrice, newQuantity), unsuccessmsg1);

        // test new price is not a double data type
        Pair<Boolean,String> unsuccessmsg2 = new Pair<>(false, "Product price must be a double");
        assertEquals(vendingMachine.modifyProduct(oldItemCode, newItemCode, newProductName, "", newQuantity), unsuccessmsg2);
        
        // test existing item code 
        Pair<Boolean, String> unsuccessfulmsg3 = new Pair<>(false, "The new item code already exists in the system , choose other item code");
        assertEquals(vendingMachine.modifyProduct(oldItemCode, "1002", newProductName, newPrice, newQuantity), unsuccessfulmsg3);
    }

    @Test
    public void testCheckoutWithCard() {
        // test card number invalid format
        Pair<Boolean, String> unsuccessfulmsg = new Pair<>(false, "Card Number must be in integer. Try Again.");
        assertEquals(vendingMachine.checkoutWithCard("Charles", "", true), unsuccessfulmsg);
        
        // test invalid name detail
        // Pair<Boolean, String> unsuccessfulmsg2 = new Pair<>(false, "There are no cards with the given name in the system");
        Pair<Boolean, String> unsuccessfulmsg2 = new Pair<>(false, "Invalid card.");
        assertEquals(vendingMachine.checkoutWithCard("invalid", "1234", true), unsuccessfulmsg2);
        
        // test invalid credit card number
        Pair<Boolean, String> unsuccessfulmsg3 = new Pair<>(false, "The credit card number is incorrect");
        assertEquals(vendingMachine.checkoutWithCard("Charles", "333", true), unsuccessfulmsg3);

        // test save
        Pair<Boolean, String> successfulmsg = new Pair<>(true, "Successful! Collect your items from the Vending Machine");
        // assertEquals(vendingMachine.checkoutWithCard("Shing", "6444", true), successfulmsg);
    
    }   


    /**
    Testing getting the CSV reports for seller, cashier and owner
     */
    @Test 
    public void testCreateUsernameReport() {
        Pair<Boolean, String> testSuccess =  new Pair<>(true, "Successfully downloaded: Username Report");
        // Pair<Boolean, String> testUNSuccess = new Pair<>(false, "Download Failed");
        assertEquals(vendingMachine.createUsernameReport(), testSuccess);
    }

    @Test 
    public void testCreateCancelledTransactionReport() {
        Pair<Boolean, String> testSuccess =  new Pair<>(true, "Successfully downloaded: Cancelled Transactions Report");
        // Pair<Boolean, String> testUNSuccess = new Pair<>(false, "Download Failed");
        assertEquals(vendingMachine.createCancelledTransactionReport(), testSuccess);
    }

    @Test 
    public void testCreateSummaryOfTransactionsReport() {
        Pair<Boolean, String> testSuccess =  new Pair<>(true, "Successfully downloaded: Summary of Transactions Report");
        // Pair<Boolean, String> testUNSuccess = new Pair<>(false, "Download Failed");
        assertEquals(vendingMachine.createSummaryOfTransactionsReport(), testSuccess);
    }   

    @Test 
    public void testCreateAvailableChangeReport() {
        Pair<Boolean, String> testSuccess =  new Pair<>(true, "Successfully downloaded: List of Available Change Report");
        // Pair<Boolean, String> testUNSuccess = new Pair<>(false, "Download Failed");
        assertEquals(vendingMachine.createAvailableChangeReport(), testSuccess);
    }

    @Test 
    public void testGenerateAvailableProductsReport() {
        Pair<Boolean, String> testSuccess =  new Pair<>(true, "Successfully downloaded: List of Available Products Report");
        // Pair<Boolean, String> testUNSuccess = new Pair<>(false, "Download Failed");
        assertEquals(vendingMachine.generateAvailableProductsReport(), testSuccess);
    }

    @Test 
    public void testGenerateProductsSoldReport() {
        Pair<Boolean, String> testSuccess =  new Pair<>(true,  "Successfully downloaded: List of Products Sold Report");
        // Pair<Boolean, String> testUNSuccess = new Pair<>(false, "Download Failed");
        assertEquals(vendingMachine.generateProductsSoldReport(), testSuccess);
    }

    


}

