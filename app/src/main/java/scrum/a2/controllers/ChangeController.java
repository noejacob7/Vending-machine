package scrum.a2.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PrivilegedAction;
import java.util.HashMap;
import javafx.scene.input.MouseEvent;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import scrum.a2.VendingMachine;
import scrum.a2.Window;
import java.util.*;
import javafx.scene.text.Text;

public class ChangeController implements Controller{
    private Pane pane;
    @FXML
    private Text totalChange;
    @FXML
    private Text amount;

    @FXML
    private Text errorMessage;
    @FXML
    private Text totalPrice;

    @FXML
    private Label fifty;

    @FXML
    private Label fiftyc;

    @FXML
    private Label fiftytotal;

    @FXML
    private Label five;

    @FXML
    private Label fivec;

    @FXML
    private Label hundred;

    @FXML
    private Label one;

    @FXML
    private Label ten;

    @FXML
    private Label tenc;

    @FXML
    private Label totalfiftyc;

    @FXML
    private Label totalfive;

    @FXML
    private Label totalfivec;

    @FXML
    private Label totalhundred;

    @FXML
    private Label totalone;

    @FXML
    private Label totalten;

    @FXML
    private Label totaltenc;

    @FXML
    private Label totaltwenty;

    @FXML
    private Label totaltwentyc;

    @FXML
    private Label totaltwo;

    @FXML
    private Label twenty;

    @FXML
    private Label twentyc;

    @FXML
    private Label two;
    private HashMap<Double, Label> quantity = new HashMap<Double, Label>();
    private HashMap<Double, Label> total = new HashMap<Double, Label>();

    private Scene scene;
    private HashMap<String, Controller> sceneController = new HashMap<String, Controller>();
    private VendingMachine model ;


    private Window window;
    public ChangeController(){
        
        
    }
    public void setPane(Pane pane){
        this.pane = pane;
    }
    public void setUp(){
      
        this.quantity.put(Double.valueOf(50),this.fifty);
        this.quantity.put(Double.valueOf(20),this.twenty);
        this.quantity.put(Double.valueOf(10),this.ten);
        this.quantity.put(Double.valueOf(5),this.five);
        this.quantity.put(Double.valueOf(2),this.two);
        this.quantity.put(Double.valueOf(1),this.one);
        this.quantity.put(0.50,this.fiftyc);
        this.quantity.put(0.20,this.twentyc);
        this.quantity.put(0.10,this.tenc);
        this.quantity.put(0.05,this.fivec);


        this.total.put(Double.valueOf(50),this.fiftytotal);
        this.total.put(Double.valueOf(20),this.totaltwenty);
        this.total.put(Double.valueOf(10),this.totalten);
        this.total.put(Double.valueOf(5),this.totalfive);
        this.total.put(Double.valueOf(2),this.totaltwo);
        this.total.put(Double.valueOf(1),this.totalone);
        this.total.put(0.50,this.totalfiftyc);
        this.total.put(0.20,this.totaltwentyc);
        this.total.put(0.10,this.totaltenc);
        this.total.put(0.05,this.totalfivec);
        Map<Double, Long> cash = this.model.getChange();
        System.out.println(cash);

        Double total = this.model.calculateMoney(cash);
        System.out.println(total);
      
        this.amount.setText(String.format("Amount Entered   :  $ %.2f",this.model.getAmountEntered()));
        this.totalPrice.setText(String.format("Total Price             :  $ %.2f",this.model.getTotal()));
        this.totalChange.setText(String.format("Total Change         :  $ %.2f",total));
        setTextDetails(cash);
    }
   
    private void setTextDetails(Map<Double,Long> change){
        for(Double amount:change.keySet()){
            this.quantity.get(Double.valueOf(amount)).setText(String.format("Quantity : %d",change.get(amount).intValue()));
            this.total.get(Double.valueOf(amount)).setText(String.format("Total : $ %.2f",(amount* change.get(amount))));
            if (change.get(amount) > 0){
                this.total.get(Double.valueOf(amount)).setTextFill(Color.rgb(165,211,208));
            }
        } 
    }

    public void setModel(VendingMachine model) throws Exception{
        this.model = model;
        


    }
    public void setSceneController(HashMap<String, Controller> sceneController){
        this.sceneController = sceneController;
    }
    public void setScene(Scene scene){

        this.scene = scene;
    }
    public Pane getPane(){
        return this.pane;
    }

    @FXML
    void nextPage(ActionEvent event) {
        // this.sceneCollections = this.window.getSceneCollections();
        // this.scene = this.window.getScene();
        // Pane root = this.sceneCollections.get("verify");
        // this.scene.setRoot(root);
        this.sceneController.get("verify").setUp();
        this.scene.setRoot(this.sceneController.get("verify").getPane());

    }
   
    // @FXML
    // void logout(MouseEvent event) {
    //     this.model.logout();
    //     this.sceneController.get("login").setUp();
    //     this.scene.setRoot(this.sceneController.get("login").getPane());
    // }

}