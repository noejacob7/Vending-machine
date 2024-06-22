package scrum.a2;
import javafx.util.Pair;
import org.json.simple.JSONObject;

import java.time.ZonedDateTime;
import java.util.*;

public class VendingMachine {

    DatabaseParsing db = new DatabaseParsing();
    String inventory_file = "src/main/resources/snack_database.json";
    String account_file = "src/main/resources/account_data.json";
    String transaction_file = "src/main/resources/transactions.json";
    String cash_reserve_file = "src/main/resources/cash_reserve.json";
    String card_reserve_file = "src/main/resources/credit_cards.json";
    Map<String, Account> accounts = new HashMap<>();
    Map<Integer, Item> items = new HashMap<>();
    Map<String, List<Transactions>> transactions = new HashMap<>();

    List<CancelledTransaction> cancelledTransaction = new ArrayList<>();
    Map<Double, Long> cashReserve;
    Map<String, Integer> cardReserve;
    Double totalPriceToPay;
    Map<Integer, Integer> cart; // key:item code, value:quantity
    String user;
    Map<Double, Long> change;
    private Double amount = 0.0;
    Double amountPaid;
    Double changeAmount;

    public VendingMachine() {
        setup();
    }

    public void setup() {

        JSONObject inventory = db.readFile(inventory_file);
        items = db.storeInventory(inventory);

        JSONObject account_info = db.readFile(account_file);
        accounts = db.storeLoginDetails(account_info);

        JSONObject transaction_info = db.readFile(transaction_file);
        transactions = db.storeTransactions(transaction_info);

        JSONObject cash_reserve = db.readFile(cash_reserve_file);
        cashReserve = db.storeCashReserve(cash_reserve);

        cardReserve = db.storeCardReserve(card_reserve_file);

        setUserAnon();
      
        cart = new HashMap<>();

        totalPriceToPay = 0.0;
    }

    public void setUserAnon(){
        this.user = "anonymous";
    }

    public void setTransactions(Map<String, List<Transactions>> newTransactions) {
        transactions = newTransactions;
    }

    public void setCashReserve(Map<Double, Long> newCashR) {
        cashReserve = newCashR;
    }

    public Map<Integer, Item> getDrinks() {
        Map<Integer, Item> drinks = new HashMap<>();
        for (Integer code: items.keySet()){
            if (items.get(code).getCategory().equals("Drinks")){
                drinks.put(code, items.get(code));
            }
        }
        return drinks;
    }

    public Map<Integer, Item> getChocolates() {
        Map<Integer, Item> chocolates = new HashMap<>();
            for (Integer code: items.keySet()){
                if (items.get(code).getCategory().equals("Chocolates")){
                    chocolates.put(code, items.get(code));
                }
            }
        return chocolates;
    }

    public Map<Integer, Item> getChips() {
        Map<Integer, Item> chips = new HashMap<>();
            for (Integer code: items.keySet()){
                if (items.get(code).getCategory().equals("Chips")){
                    chips.put(code, items.get(code));
                }
            }
        return chips;
    }

    public Map<Integer, Item> getCandies() {
        Map<Integer, Item> candies = new HashMap<>();
            for (Integer code: items.keySet()){
                if (items.get(code).getCategory().equals("Candies")){
                    candies.put(code, items.get(code));
                }
            }
        return candies;
    }

    public Pair<Boolean, String> checkLogin(String username, String password){
        Pair<Boolean, String> message;
        if (accounts.containsKey(username)){
            if (accounts.get(username).getPassword().equals(password)){
                this.user = username;
                message = new Pair<>(true, accounts.get(user).getRole());
            }
            else{
                message = new Pair<>(false, "Invalid Password!");
            }
        } else{
            message = new Pair<>(false, "Username does not exist!");
        }
        return message;
    }
   
    public Pair<Boolean, String> addLogin(String username, String password, String verifyPassword){
        Pair<Boolean, String> message;
        if (username.equals("anonymous")){
            message = new Pair<>(false, "Cannot use \"anonymous\" as a username");
        }
        else if (accounts.containsKey(username)){
            message = new Pair<>(false, "Username already exists");
        }
        else if (password.strip().equals("")) {
            message = new Pair<>(false, "Need to have at least one character in the password");
        }
        else if (!password.equals(verifyPassword)) {
            message = new Pair<>(false, "The passwords do not match");
        }
        else {
            accounts.put(username,new Account(password, false, "", 0L, "Customer"));
            user = username;
            transactions.put(username, new ArrayList<>());
            message = new Pair<>(true, accounts.get(user).getRole());
        }
        return message;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return this.user;
    }

    public Double getAmountEntered() {
        return this.amount;
    }

    // get last 5 transactions list 
    public List<Pair<Item, Long>> getTransactions() {
        List<Pair<Item, Long>> lastFiveTransactions = new ArrayList<>();
        if (transactions.get(user) == null || transactions.get(user).size() == 0) {
            return null;
        }
        boolean bool = false;
        for (int i= transactions.get(user).size()-1; i>=0; i--){
            if (bool){
                break;
            }
            for (Integer code: transactions.get(user).get(i).getItemsSold().keySet() ){
                if (lastFiveTransactions.size() >=5){
                    bool = true;
                    break;
                }
                lastFiveTransactions.add(new Pair<>(items.get(code), Long.valueOf(transactions.get(user).get(i).getItemsSold().get(code))));
            }
        }
        return lastFiveTransactions;
    }
    

    public Map<Integer, Integer> getCart() {
        return cart;
    }

    public Pair<Boolean, String> addItemToCart(int code) {
        Pair<Boolean, String> message;
        // check if code is valid aka, exist in database
        if (items.get(code) == null) {
            message = new Pair<>(false, "No such item :(");
        }
        else if (items.get(code).addToCart()) {
            if (cart.containsKey(code)) {
                cart.put(code, cart.get(code) + 1);
            } else{
                cart.put(code, 1);
            }
            totalPriceToPay += items.get(code).getPrice();
            message = new Pair<>(true, "Successfully added to cart!!");
        } else {
            message = new Pair<>(false, "Item not in stock :(");
        }
        return message;
    }


    public Pair<Boolean, String> returnItem(int code) {
        Pair<Boolean, String> message;
        if (cart.containsKey(code)) {
            items.get(code).returnToCart();
            totalPriceToPay -= items.get(code).getPrice();
            cart.put(code, cart.get(code) - 1);
            if (cart.get(code) == 0) {
                cart.remove(code);
            } 
            message = new Pair<>(true, "Successfully removed from cart!!");
        } else {
            message = new Pair<>(false, "Item not in cart yet");
        }
        return message;
    }

    public Double getTotal() {
        return this.totalPriceToPay;
    }


    public int getTotalItems() {
        int count = 0;
        for (Integer eachCount: cart.values()) {
            count += eachCount;
        }
        return count;
    }

    public Map<Item, Integer> showCart() {
        Map<Item, Integer> newCart = new HashMap<>();
        System.out.println(cart);
        for(Integer code: cart.keySet()) {
            newCart.put(items.get(code), cart.get(code));
        }
        return newCart;
    }


    public Double calculateMoney(Map<Double, Long> cash) {
        double totalCash = 0;
        for (Double currency: cash.keySet()) {
            totalCash += currency * cash.get(currency);
        }
        return totalCash;
    }

    public Pair<Boolean, String> checkoutWithCash(Map<Double, Long> cash) {
        Pair<Boolean, String> message;
        Double amount = calculateMoney(cash); // amount= user input 
        if (amount < this.totalPriceToPay) {
            message = new Pair<>(false, "Insufficient money");
        } else {
            if (calculateChange(amount, cash)) {
                this.amountPaid = amount;
                this.changeAmount = calculateMoney(this.change);
                this.amount = amount; // amount entered 
                addTransactionsToCart("Cash");
                message = new Pair<>(true, "Successful! Collect your change and items from the Vending Machine");
            } else {
                message = new Pair<>(false, "The machine doesn't have enough change. Please insert other notes/coins");
            }
        }
        return message;
    }

    public Pair<String, Long> getCardDetails(){
        if (this.user.equals("anonymous"))
            return new Pair<>(null, null);

        else if (this.accounts.get(user).isSavedCard()){
            return new Pair<>(this.accounts.get(user).getName(), this.accounts.get(user).getPin());
        }
        return new Pair<>(null, null);
    }

    public Pair<Boolean, String> checkoutWithCard(String name, String number, boolean save) {
        Pair<Boolean, String> message;
        Integer cardNumber;
      
        //catch card number
        if (!cardReserve.containsKey(name)){
            // return new Pair<>(false, "There are no cards with the given name in the system");
            return new Pair<>(false, "Invalid card.");
        } 
        try{
            cardNumber = Integer.valueOf(number);
        }
        catch(NumberFormatException e){
            return new Pair<>(false, "Card Number must be in integer. Try Again.");
        }
        if (cardReserve.get(name).equals(cardNumber)){
            if (save){
                accounts.get(user).setSavedCard(true);
                accounts.get(user).setName(name);
                accounts.get(user).setPin(Long.valueOf(cardNumber));
            }
            this.amountPaid = amount;
            this.changeAmount = 0.0;
            addTransactionsToCart("Card");
            message = new Pair<>(true, "Successful! Collect your items from the Vending Machine");
        } else{
            message = new Pair<>(false, "The credit card number is incorrect");
        }
        return message;
    }

    public void logout() {
        this.cart = new HashMap<>();
        System.out.println("log out cart");
        System.out.println(this.cart);
        setUserAnon();
        totalPriceToPay = 0.0;
        this.amountPaid = 0.0;
        this.changeAmount = 0.0;
    }

    public void addTransactionsToCart(String paymentType) {
        Transactions newTransaction = new Transactions(ZonedDateTime.now(), cart, this.amountPaid, this.changeAmount, paymentType);
        transactions.get(user).add(newTransaction);
    }

    public boolean calculateChange(double moneyEntered, Map<Double, Long> cash) { 
        double dupTotal = moneyEntered - this.totalPriceToPay;
        change = new HashMap<>();
        addToReserve(cash);
        for (Double eachCash: cashReserve.keySet()) {
            if (dupTotal == 0){
                return true;
            }
            while (cashReserve.get(eachCash) > 0) {
                if (eachCash > dupTotal) {
                    break;
                }
                dupTotal -= eachCash;
                cashReserve.put(eachCash, cashReserve.get(eachCash) -1);
                if (change.containsKey(eachCash)) {
                    change.put(eachCash, change.get(eachCash)+1);
                } else {

                    change.put(eachCash, 1L);
                }
            }
        }
        if (dupTotal == 0) {
            return true;
        }
        addToReserve(change);
        removeFromReserve(cash);
        change = new HashMap<>();
        return false;
    }

    public void addToReserve(Map<Double, Long> cash) {
        for (Double eachCash: cash.keySet()){
            cashReserve.put(eachCash, cashReserve.get(eachCash)+ cash.get(eachCash));
        }
    }

    public void removeFromReserve(Map<Double, Long> cash) {
        for (Double eachCash: cash.keySet()) {
            cashReserve.put(eachCash, cashReserve.get(eachCash)- cash.get(eachCash));
        }
    }

    public Map<Double, Long> getChange() {
        return change;
    }

    public Map<Double, Long> getCashReserve() {
        return cashReserve;
    }

    public void cancelTransaction(String reason) {
        cancelledTransaction.add(new CancelledTransaction(user, ZonedDateTime.now(), reason));
        for (Integer code: cart.keySet()){
            for (int i = 0; i<cart.get(code); i++)
                items.get(code).returnToCart();
        }
        logout();
    }

    public List<CancelledTransaction> getCancelledTransactionList() {
        return cancelledTransaction;
    }

    public Pair<Boolean, String> changeRole(String username, String role) {
        Pair<Boolean, String> message;
        if (accounts.containsKey(username)) {
            if (accounts.get(username).getRole().equals(role)) {
                message = new Pair<>(false, "Role remains the same. User already has this role.");
            } else if (username.equals(user)) {
                message = new Pair<>(false, "You can't change your own role.");
            } else {
                accounts.get(username).setRole(role);
                message = new Pair<>(true, String.format("Successful changed role of %s to %s", username, role));
            }
        } else {
            // message = new Pair<>(false, String.format("User %s not found", username));
            message = new Pair<>(false, "User not found.");
        }
        return message;
    }

    // owner
    public Pair<Boolean, String> createUsernameReport() {
        Pair<Boolean, String> message;
        if (db.createUsernameReport(accounts)) {
            message = new Pair<>(true, "Successfully downloaded: Username Report");
        } else{
            message = new Pair<>(false, "Download Failed");
        }
        return message;
    }

    // owner
    public Pair<Boolean, String> createCancelledTransactionReport() {
        Pair<Boolean, String> message;
        if (db.createCancelledYTransactionReport(cancelledTransaction)) {
            message = new Pair<>(true, "Successfully downloaded: Cancelled Transactions Report");
        } else {
            message = new Pair<>(false, "Download Failed.");
        }
        return message;
    }

    // cashier 
    public Pair<Boolean, String> createSummaryOfTransactionsReport() {
        Pair<Boolean, String> message;
        if (db.createSummaryOfTransactionsReport(transactions)) {
            message = new Pair<>(true, "Successfully downloaded: Summary of Transactions Report");
        } else {
            message = new Pair<>(false, "Download Failed.");
        }
        return message;
    }

    // cashier
    public Pair<Boolean, String> createAvailableChangeReport() {
        Pair<Boolean, String> message;
        if (db.createAvailableChangeReport(cashReserve)) {
            message = new Pair<>(true, "Successfully downloaded: List of Available Change Report");
        } else {
            message = new Pair<>(false, "Download Failed.");
        }
        return message;
    }

    // cashier modify currency quantity function: change quantity of available notes and coins 
    public Pair<Boolean, String> modifyCurrencyQuantity(Double currency, long newQuantity) {
        boolean result = false;
        String msg = "";
        if (cashReserve.containsKey(currency)) {
            Long oldQuantity = cashReserve.get(currency);
            if (Objects.equals(oldQuantity, Long.valueOf(newQuantity))) {
                msg = String.format("Quantity for currency $%f remains the same.", currency);
            } else {
                result = true;
                cashReserve.put(currency, Long.valueOf(newQuantity));
                msg = String.format("Successful. For currency $%f, changed quantity to %d.", currency, newQuantity);  
            }
        } 
        Pair<Boolean, String> ret = new Pair<>(result, msg);
        return ret;
    }

    //seller
    public Pair<Boolean, String> generateAvailableProductsReport(){
        Pair<Boolean, String> msg;
        if(db.createAvaliableProductsReport(items)){
            msg = new Pair<>(true, "Successfully downloaded: List of Available Products Report");
        } else {
            msg = new Pair<>(false, "Download Failed.");
        }
        return msg;
    }

    //seller
    public Pair<Boolean, String> generateProductsSoldReport(){
        Pair<Boolean, String> msg;
        if(db.createProductsSoldReport(items)){
            msg = new Pair<>(true, "Successfully downloaded: List of Products Sold Report");
        } else {
            msg = new Pair<>(false, "Download Failed.");
        }
        return msg;
    }

    //seller
    public Pair<Boolean, String> modifyProduct(Integer oldItem,String itemCode, String productName,String price, Long quantity){
      
        Integer codeProduct;
        Double productprice;

        //catch product code
        try{
            codeProduct = Integer.valueOf(itemCode);
        }
        catch(NumberFormatException e){
            return new Pair<>(false, "Item code must be an integer");
        }
        //catch product price
        try{
            productprice = Double.valueOf(price);
        }
        catch(NumberFormatException e){
            return new Pair<>(false, "Product price must be a double");
        }

        //product to modify 
        Item productToModify = this.items.get(oldItem);
     
        if (this.items.containsKey(codeProduct) && ! codeProduct.equals(oldItem)){
            System.out.println(codeProduct.equals(oldItem));
            System.out.println(codeProduct);
            System.out.println(oldItem);
            return new Pair<>(false, "The new item code already exists in the system , choose other item code");
        }
        productToModify.setPrice(productprice);
        productToModify.setName(productName);
        productToModify.setQuantity(quantity);
        this.items.remove(oldItem);
        this.items.put(codeProduct, productToModify);
        return new Pair<>(true, "Successfully modified item product");
 
    }


}
