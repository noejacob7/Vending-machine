package scrum.a2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import java.util.*;
import scrum.a2.VendingMachine;
import scrum.a2.Window;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import javafx.scene.input.MouseEvent;

public class ModifyCashController implements Controller {
  
    private Pane pane;
    
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

    @FXML
    private Spinner<Integer> two;
    private HashMap<Double, Spinner<Integer>> spinner = new HashMap<Double, Spinner<Integer>>();
    private Map<Double, Long> value = new HashMap<Double, Long>();
    public ModifyCashController(){
        

    }
   
    public void setScene(Scene scene){
        this.scene = scene;
    }
    public void setInitial(){
        Map<Double, Long> cashReserve = this.model.getCashReserve();
        for (Double key: this.spinner.keySet()){
            Integer cashQuantity = (int) (long) cashReserve.get(key);
            this.spinner.get(key).getValueFactory().setValue(cashQuantity);
            this.value.put(key, cashReserve.get(key));
        }
        Double amt = this.model.calculateMoney(cashReserve);
        this.amount.setText(String.format("Amount Entered : $ %.2f",amt));


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
        setInitial();
       
    }
    public void setModel(VendingMachine model){
        this.model = model;

    }
    public Pane getPane(){
        return this.pane;
    }
    @FXML
    void completePayment(ActionEvent event) throws Exception {
        // modify
        this.model.setCashReserve(this.value);
        // Pair<Boolean,String> msg = this.model.createAvailableChangeReport();
        OperationVerifyController controller = (OperationVerifyController )this.sceneController.get("operation-verify");
        Double amount = this.model.calculateMoney(this.value);
        controller.setMessage(String.format("Successful modified cash register! , current amount %.2f",amount));
        
        this.scene.setRoot(this.sceneController.get("operation-verify").getPane());
        
        


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
        this.model.logout();
        this.sceneController.get("login").setUp();
        this.scene.setRoot(this.sceneController.get("login").getPane());
    }
   
 


}
