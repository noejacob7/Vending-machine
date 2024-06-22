package scrum.a2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;

import java.util.*;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
import scrum.a2.VendingMachine;
import scrum.a2.Window;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;

public class cashPaymentController implements Controller {
    
    @FXML
    private Group cancelTransaction;
    @FXML
    private Text errorMessage1;

    @FXML
    private CheckBox isCreditSave;
    @FXML
    private Group cashPane;
    @FXML
    private Group creditPane;
    @FXML
    private Text creditCardMessage;
    @FXML
    private Text totalPrice;
    private Pane pane;
    @FXML
    private TextField creditCardName;

    @FXML
    private PasswordField creditCardPass;
    
    @FXML
    private Text amount;
    private Scene scene;

    private HashMap<String, Controller> sceneController = new HashMap<String, Controller>();

    private VendingMachine model;
    @FXML
    private Text errorMessage;
    @FXML
    private Spinner<Integer> fifty;

    @FXML
    private Spinner<Integer> fiftyc;

    @FXML
    private Spinner<Integer> five;

    @FXML
    private Spinner<Integer> fivec;


    @FXML
    private Spinner<Integer> one;

    @FXML
    private Spinner<Integer> ten;

    @FXML
    private Spinner<Integer> tenc;

    @FXML
    private Spinner<Integer> twenty;

    @FXML
    private Spinner<Integer> twentyc;
    private boolean isCash = true;

    @FXML
    private Spinner<Integer> two;
    private HashMap<Double, Spinner<Integer>> spinner = new HashMap<Double, Spinner<Integer>>();
    private Map<Double, Long> value = new HashMap<Double, Long>();
    public cashPaymentController(){
        

    }
   
    public void setScene(Scene scene){
        this.scene = scene;
    }
  
  
    public void setUp(){

        this.spinner.put(Double.valueOf(50),this.fifty);
        this.spinner.put(Double.valueOf(20),this.twenty);
        this.spinner.put(Double.valueOf(10),this.ten);
        this.spinner.put(Double.valueOf(5),this.five);
        this.spinner.put(Double.valueOf(2),this.two);
        this.spinner.put(Double.valueOf(1),this.one);
        this.spinner.put(0.50,this.fiftyc);
        this.spinner.put(0.20,this.twentyc);
        this.spinner.put(0.10,this.tenc);
        this.spinner.put(0.05,this.fivec);
        for(Double note: this.spinner.keySet()){
            Spinner<Integer> mySpin = this.spinner.get(note);
            mySpin.getValueFactory().setValue(0);
            this.value.put(note,Long.valueOf(0));
        }
        this.errorMessage.setText("");
        this.creditCardMessage.setText("");
        this.totalPrice.setText(String.format("Total Price : $ %.2f",this.model.getTotal()));
        Pair<String , Long> cardDetails = this.model.getCardDetails();
    
        
        System.out.println(this.creditCardName.getText());
        if (cardDetails.getValue() != null){
            this.creditCardName.setText(cardDetails.getKey());
            this.creditCardPass.setText(String.format("%d",cardDetails.getValue()));
        }else{
            System.out.println("null");
            this.creditCardPass.setText("");
            this.creditCardName.setText("");
        }
        if (this.model.getUser().equals("anonymous")){
            this.isCreditSave.setVisible(false);
        }else{
            this.isCreditSave.setVisible(true);
        }
    }
    public void setModel(VendingMachine model){
        this.model = model;

    }
    public Pane getPane(){
        return this.pane;
    }
    private void payCash(){
        for(Double note: this.spinner.keySet()){
            Spinner<Integer> mySpin = this.spinner.get(note);
            this.value.put(note,Long.valueOf(mySpin.getValue()));
        }
    
        Pair <Boolean, String> message = this.model.checkoutWithCash(this.value);
        if (message.getKey()){
            this.sceneController.get("change").setUp();
            this.scene.setRoot(this.sceneController.get("change").getPane());

        }else{
            this.errorMessage1.setText(String.format("%s. Would you like to cancel transaction.",message.getValue()));
            this.cancelTransaction.setVisible(true);
            this.errorMessage.setText(message.getValue());
        }

    }
    private void payCard(){
        // Pair<Boolean, String> message = this.model.checkoutWithCard
    }
    @FXML
    void completePayment(ActionEvent event) throws Exception {
        if (this.isCash){
            this.payCash();
        }else{

            Pair<Boolean, String> message = this.model.checkoutWithCard(this.creditCardName.getText(), this.creditCardPass.getText(), this.isCreditSave.isSelected());
            System.out.println(this.isCreditSave.isSelected());
            if (message.getKey()){
                VerifyController controller =(VerifyController)this.sceneController.get("verify");
                controller.setUp();
                // this.scene.setRoot(this.sceneController.get("verify").getPane());
                // controller.runProgress();

            }else{

                this.creditCardMessage.setText(message.getValue());

            }
    
            
            
        }
        
        
        
        

    }
    public void setPane(Pane pane){
        this.pane = pane;
    }
    @FXML
    void updateAmount(MouseEvent event) {
        for(Double note: this.spinner.keySet()){
            Spinner<Integer> mySpin = this.spinner.get(note);
            this.value.put(note,Long.valueOf(mySpin.getValue()));
        }
        Double amt = this.model.calculateMoney(this.value);
        this.amount.setText(String.format("Amount Entered : $ %.2f",amt));

    }
    public void setSceneController(HashMap<String, Controller> sceneController) {
        this.sceneController = sceneController;
    }
    @FXML
    void back(MouseEvent event) {
        this.scene.setRoot(this.sceneController.get("cart").getPane());

    }
    @FXML
    void credit(MouseEvent event) {
        this.cancelTransaction.setVisible(false);
        this.cashPane.setVisible(false);
        this.creditPane.setVisible(true);
        this.isCash =false;
        

    }
    @FXML
    void cash(MouseEvent event) {
        this.cashPane.setVisible(true);
        this.creditPane.setVisible(false);
        this.isCash = true;

    }
    @FXML
    void logout(MouseEvent event) {
        
        
        this.model.logout();
        this.sceneController.get("login").setUp();

        this.scene.setRoot(this.sceneController.get("login").getPane());
        this.cashPane.setVisible(true);
        this.creditPane.setVisible(false);
        this.isCash =true;
    }
    @FXML
    void cancelTransact(MouseEvent event) {
        //call model cancel transaction
        this.cancelTransaction.setVisible(false);
        this.model.cancelTransaction(this.errorMessage.getText());
        this.model.logout();
        this.sceneController.get("login").setUp();
        this.scene.setRoot(this.sceneController.get("login").getPane());

    }
    @FXML
    void tryAgain(MouseEvent event) {
        this.cancelTransaction.setVisible(false);
    }
    
    


}
