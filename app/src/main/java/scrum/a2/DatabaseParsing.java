package scrum.a2;

import javafx.util.Pair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import com.opencsv.CSVWriter;

public class DatabaseParsing {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a z");
    String usernameReportFile = "src/main/report/username_report.csv";
    String cancelledTransactionFile = "src/main/report/cancelled_transactions_report.csv";
    
    // cashier
    String summaryOfTransactionFile = "src/main/report/summaryOfTransactionsCashier.csv";
    String listOfAvailableChangeFile = "src/main/report/listOfAvailableChange.csv";

    //seller
    String listOfAvailableProducts = "src/main/report/listOfProducts.csv";
    String listOfSoldProducts = "src/main/report/listOfSoldProducts.csv";

    public JSONObject readFile(String filename){
        JSONParser parser = new JSONParser();
        JSONObject inventory = new JSONObject();
        try {

            inventory = (JSONObject) parser.parse(new FileReader(filename));

        }catch (Exception e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
        return inventory;
    }

    public Map<Integer, Item> storeInventory(JSONObject inventory){
        Map <Integer, Item> items = new HashMap<>();
        for (Object code: inventory.keySet()){
            JSONArray itemDesc = (JSONArray) inventory.get(code);
            items.put(Integer.parseInt((String) code), new Item( (String) itemDesc.get(0), (Double) itemDesc.get(1), (Long) itemDesc.get(2), (String) itemDesc.get(3) ));
        }
        return items;
    }

    public Map<String, Account> storeLoginDetails(JSONObject account_info){
        Map<String, Account> account = new HashMap<>();
        for (Object username: account_info.keySet()){
            JSONObject info = (JSONObject) account_info.get(username);
            boolean savedCard = true;
            if (info.get("name").equals("")){
                savedCard = false;
            }
            Account eachAccount = new Account( (String) info.get("password"), savedCard, (String) info.get("name"), Long.valueOf((String) info.get("number")), (String) info.get("role"));
            account.put((String) username,  eachAccount);
        }
        return account;
    }

    public Map<String, List<Transactions>> storeTransactions(JSONObject transaction_info){

        Map<String, List<Transactions>> transactions = new HashMap<>();
        for (Object username: transaction_info.keySet()) {
            List<Transactions> allTransactions = new ArrayList<>();
            JSONArray transactionList = (JSONArray) transaction_info.get(username);
            for (Object o : transactionList) {
                JSONObject transactionKey = (JSONObject) o;
                Double amountPaid = Double.parseDouble( (String) transactionKey.get("amountPaid"));
                Double returnedChange = Double.parseDouble( (String) transactionKey.get("returnedChange"));
                String paymentMethod = (String) transactionKey.get("paymentMethod");
                ZonedDateTime date = ZonedDateTime.parse((String) transactionKey.get("date"));
                JSONArray internalJSONTransactionList = (JSONArray) transactionKey.get("itemsSold");
                Map<Integer, Integer> individualTransaction = new HashMap<>();
                for (Object i: internalJSONTransactionList){
                    JSONArray internalTransactionList = (JSONArray) i;
                    individualTransaction.put(Integer.parseInt((String) internalTransactionList.get(0)), Math.toIntExact( (Long) internalTransactionList.get(1)));
                }
                allTransactions.add(new Transactions(date, individualTransaction, amountPaid, returnedChange, paymentMethod));
            }
            transactions.put((String) username, allTransactions);
        }

        return transactions;
    }

    public TreeMap<Double, Long> storeCashReserve(JSONObject cash_reserve_info){
        TreeMap<Double, Long> cashReserve = new TreeMap<>(Collections.reverseOrder());
        for (Object currency: cash_reserve_info.keySet()){
            cashReserve.put(Double.parseDouble((String) currency), (Long) cash_reserve_info.get(currency));
        }
        return cashReserve;
    }

    public Map<String, Integer> storeCardReserve(String filename){
        Map<String, Integer> cardReserve = new HashMap<>();
        JSONParser parser = new JSONParser();
        try {

            JSONArray inventory = (JSONArray) parser.parse(new FileReader(filename));
            for (Object eachCard: inventory){
                JSONObject eachCardJSON = (JSONObject) eachCard;
                cardReserve.put((String) eachCardJSON.get("name"), Integer.valueOf((String) eachCardJSON.get("number")));
            }

        }catch (Exception e){
            System.out.println("The file does not exist");
            e.printStackTrace();
        }
        return cardReserve;
    }

    public boolean writeCSV(List<String[]> data, String path) {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File(path);

        try {
            // create FileWriter object with file as parameter
            FileWriter outputFile = new FileWriter(file);

            // create CSVWriter with '|' as separator
            CSVWriter writer = new CSVWriter(outputFile, ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            // create a List which contains String array
            writer.writeAll(data);
            // closing writer connection
            writer.close();
            return true;
        }
        catch (IOException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            return false;
        }
    }

    public boolean createUsernameReport(Map<String, Account> account){
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Username", "Role" });
        for (String user: account.keySet()){
            data.add(new String[] { user, account.get(user).getRole()});
        }
        return writeCSV(data, usernameReportFile);
    }

    public boolean createCancelledYTransactionReport( List<CancelledTransaction> cancelledTransactions){
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Date/Time", "User", "Reason" });
        for (int i =0; i<cancelledTransactions.size(); i++){
            CancelledTransaction each = cancelledTransactions.get(i);
            data.add(new String[] { each.getDate().toString(), each.getUser(), each.getReason()});
        }
        return writeCSV(data, cancelledTransactionFile);
    }

    // cashier summary of transactions report in csv
    // format: date&time, amount paid, returned change, payment method, items sold
    // 2022-10-24T19:51:31.023421+11:00[Australia/Sydney],$15,$3,cash,[1014:2]+[1011:4]
    public boolean createSummaryOfTransactionsReport(Map<String, List<Transactions>> transactions){
        List<String[]> data = new ArrayList<>(); 
        data.add(new String[] { "Date and Time", "Amount Paid", "Returned Change", "Payment Method", "Items Sold"});
        for (String username: transactions.keySet()) {

            List<Transactions> indivTransactionsList = transactions.get(username);
            for (Transactions t : indivTransactionsList) {
                Double amtPaid = t.getAmountPaid();
                StringBuilder amtPaidSB = new StringBuilder();
                amtPaidSB.append("$");
                amtPaidSB.append(amtPaid.toString());
                String amtPaidString = amtPaidSB.toString();

                Double returnedChge = t.getReturnedChange();
                StringBuilder returnedChgeSB = new StringBuilder();
                returnedChgeSB.append("$");
                returnedChgeSB.append(returnedChge.toString());
                String returnedChangeString = returnedChgeSB.toString();
                
                String paymentMthd = t.getPaymentMethod();
                ZonedDateTime date = t.getDate();
                Map<Integer, Integer> itemsSld = t.getItemsSold();
                String itemsSoldString = "";
                for (Integer c: itemsSld.keySet()) {
                    // c is the item code, second integer is the quantity sold
                    StringBuilder sb = new StringBuilder();
                    sb.append("[");
                    sb.append(c); // the item code 
                    sb.append(":");
                    sb.append(itemsSld.get(c)); // the quantity (Long)
                    sb.append("]"); // the quantity (Long)
                    sb.append("+");
                    // [1014:1]+[1004:2]...
                    itemsSoldString = sb.toString();
                }
                // remove trailing '+'
                StringBuffer sbItemsSold = new StringBuffer(itemsSoldString);  
                sbItemsSold.deleteCharAt(sbItemsSold.length()-1); 
                data.add(new String[] {date.toString(), amtPaidString, returnedChangeString, paymentMthd, sbItemsSold.toString()} );
            }
        }
        return writeCSV(data, summaryOfTransactionFile);
    }

    // cashier  
    // A list of the currently available change (the quantity of each coin 
    // and each note in the vending machine)
    // format: Currency, Quantity
    // $100.00,3
    public boolean createAvailableChangeReport( Map<Double, Long> cashReserve){
        List<String[]> data = new ArrayList<>();
        data.add(new String[] { "Currency", "Quantity" });
        for (Double currency: cashReserve.keySet()) {
            StringBuilder curSB = new StringBuilder();
            curSB.append("$");
            curSB.append(currency);
            data.add(new String[] {curSB.toString(), cashReserve.get(currency).toString()});
        }
        return writeCSV(data, listOfAvailableChangeFile);
    }

    /*
    A list of the currently available items that include the item details.
    A list that includes item codes, item names and the quantity sold for each item
    (e.g. "1001; Mineral Water; 12", "1002; Mars; 1", etc.).
     */
    public boolean createAvaliableProductsReport(Map<Integer, Item> items){
        List<String[]> input = new ArrayList<>();
        input.add(new String[] { "Code", "Name", "Quantity avaliable"});
        for(int code: items.keySet()){
            Item currentProduct = items.get(code);
            if(currentProduct.getQuantity()==0){
                //the item is not included in the report if it is not avaliable
                continue;
            }
            input.add(new String[] { Integer.toString(code), currentProduct.getName().toString(), currentProduct.getQuantity().toString()});
        }
        return writeCSV(input, listOfAvailableProducts);

    }
    public boolean createProductsSoldReport(Map<Integer, Item> items){
        List<String[]> input = new ArrayList<>();
        input.add(new String[] { "Code", "Name", "Quantity sold"});
        for(int code: items.keySet()){
            Item currentProduct = items.get(code);
            if(currentProduct.getQuantity()==0){
                //the item is not included in the report if it is not avaliable
                continue;
            }
            input.add(new String[] { Integer.toString(code), currentProduct.getName().toString(), currentProduct.getQuantitySold().toString()});

        }
        return writeCSV(input, listOfSoldProducts);

    }

}
