package scrum.a2.controllers;

import scrum.a2.Item;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.ResourceBundle.Control;

import javafx.scene.input.MouseEvent;


import java.util.*;
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

public class CartController implements Controller {
    private Scene scene;
    private HashMap<String, Controller> sceneController = new HashMap<String, Controller>();
    private VendingMachine model ;
    private Window window;
    private Pane pane;

    private Label totalPrice;
    @FXML
    private ScrollPane scrollPane;
    HashMap<String , String> images = new HashMap<>();

    public CartController(){
       
        this.totalPrice = new Label();
        images.put("Drinks","src/main/resources/images/coffee-glass.png");
        images.put("Chocolates","src/main/resources/images/chocolate2.png");
        images.put("Chips","src/main/resources/images/cracker.png");
        images.put("Candies","src/main/resources/images/candy.png");
    }
    public Group makeItems(int y, Item item , Integer quantity) throws FileNotFoundException{
        Group group = new Group();
        Region root = new Region();
        root.setLayoutX(0);
        root.setLayoutY(y+150);
        root.setPrefWidth(358);
        root.setPrefHeight(160);
        root.setStyle("-fx-background-color:#EAE5F5; -fx-background-radius:30;-fx-border-style: solid none solid none; -fx-border-width: 7; -fx-border-radius:26;-fx-border-color: #838AC3;");
        InputStream stream = new FileInputStream(images.get(item.getCategory()));
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setLayoutX(40);
        imageView.setLayoutY(y+180);
      
        group.getChildren().add(root);
        group.getChildren().add(imageView);
        Font font = Font.font("Inika", FontWeight.BOLD, FontPosture.REGULAR, 20);
        Label productlabel = new Label(item.getName());
        productlabel.setFont(font);
        productlabel.setTextFill(Color.BLACK);
        productlabel.setTranslateX(160);
        productlabel.setTranslateY(y+180);
        group.getChildren().add(productlabel);
        Font font3 = Font.font("Inika", FontWeight.NORMAL, FontPosture.REGULAR, 15);
        
        Label pricelabel = new Label(String.format("Price per product : $ %.2f",item.getPrice()));
        pricelabel.setFont(font3);
      
        pricelabel.setTextFill(Color.rgb(132,128,144));
        pricelabel.setTranslateX(160);
        pricelabel.setTranslateY(y+210);
        group.getChildren().add(pricelabel);
        Label quantitylabel = new Label(String.format("Quantity : %d products",quantity));
        
        quantitylabel.setFont(font3);
     
        quantitylabel.setTextFill(Color.rgb(132,128,144));
        quantitylabel.setTranslateX(160);
        quantitylabel.setTranslateY(y+230);
        group.getChildren().add(quantitylabel);
        Label totallabel = new Label(String.format("TOTAL : $ %.2f",quantity*item.getPrice()));
        Font font2 = Font.font("Inika", FontWeight.BOLD, FontPosture.REGULAR, 17);
        totallabel.setFont(font2);
       
        
        totallabel.setTextFill(Color.GREY);
        totallabel.setTranslateX(230);
        totallabel.setTranslateY(y+260);
        group.getChildren().add(totallabel);
        return group;




    }

    public Pane getPane(){
        return this.pane;

    }
    public void setDetails(Pane pane){

        Font font = Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, 15);

        this.totalPrice.setFont(font);
        //Filling color to the this.message
    
        this.totalPrice.setTextFill(Color.WHITE);
        //Setting the position
       
        this.totalPrice.setTranslateX(10);

        this.totalPrice.setTranslateY(762);

        this.totalPrice.setText(String.format("Total Price : $ %.2f",this.model.getTotal()));
     
        pane.getChildren().add(this.totalPrice);
    }
    @FXML
    void processPayment(ActionEvent event) throws Exception{
        this.sceneController.get("transaction").setUp();
        this.scene.setRoot(this.sceneController.get("transaction").getPane());

    }
    public void setWindow(Window window){
        this.window = window;
    }
    public void makeCartDisplay(Map<Item, Integer> itemCart) throws Exception{

        Group group = new Group();
        int y = 0;
        for(Item item: itemCart.keySet()){
            Group group1 =makeItems(y,item,itemCart.get(item));
            y+=162;
            group.getChildren().add(group1);
        }
        this.scrollPane.setContent(group);
    }
    public void setUp() {
        try{
            Map<Item, Integer> itemCart = this.model.showCart();
         
            makeCartDisplay(itemCart);
        }catch(Exception e){
            e.printStackTrace();

        }
        
    }
    public void setPane(Pane pane){
        this.pane = pane;
    }
    
    public void setModel(VendingMachine model){
        this.model = model;

    }
    public void setSceneController(HashMap<String, Controller> sceneController){
        this.sceneController = sceneController;
    }
    public void setScene(Scene scene){

        this.scene = scene;
    }
    @FXML
    void logout(MouseEvent event) {
        this.model.cancelTransaction("User cancelled");
        this.model.logout();
        
        this.sceneController.get("login").setUp();
        this.scene.setRoot(this.sceneController.get("login").getPane());
    }
    @FXML
    void backToSnacks(MouseEvent event) {
        this.scene.setRoot(this.sceneController.get("snacks-corner").getPane());

    }

}
