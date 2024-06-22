package scrum.a2.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PrivilegedAction;
import javafx.scene.input.MouseEvent;
import java.util.HashMap;

import javafx.util.Pair;

import scrum.a2.Item;
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

public class RecordController implements Controller{
    private Pane pane;
    private Scene scene;
    private HashMap<String, Controller> sceneController = new HashMap<String, Controller>();

    private VendingMachine model ;
    private Window window;
    HashMap<String , String> images = new HashMap<>();
    @FXML
    private ScrollPane scrollPane;

    public RecordController(){
        images.put("Drinks","src/main/resources/images/coffee-glass.png");
        images.put("Chocolates","src/main/resources/images/chocolate2.png");
        images.put("Chips","src/main/resources/images/cracker.png");
        images.put("Candies","src/main/resources/images/candy.png");
    }
    public Group makeItems(int y, Item item , int quantity) throws FileNotFoundException{
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
    public void setPane(Pane pane){
        this.pane = pane;
    }
    public void setUp(){
        try{
            makeCartDisplay(this.model.getTransactions());
        }catch (Exception e){
            e.printStackTrace();
        }
        
    }
 
    @FXML
    void makeOrder(ActionEvent event) {
        this.sceneController.get("snacks-corner").setUp();
        this.scene.setRoot(this.sceneController.get("snacks-corner").getPane());
    }
    public void setWindow(Window window){
        this.window = window;
    }
    public void makeCartDisplay(List<Pair<Item, Long>>  itemCart) throws Exception{

        Group group = new Group();
        int y = 0;
       
        for (Pair<Item,Long> item: itemCart){
            
            Group group1 =makeItems(y,item.getKey(),item.getValue().intValue());
            y+=162;
            group.getChildren().add(group1);
        }
     
        this.scrollPane.setContent(group);
    }
    
    public void setModel(VendingMachine model) {
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
        System.out.println("log out");
        this.model.cancelTransaction("User Cancelled");
        this.model.logout();
        this.sceneController.get("login").setUp();
        this.scene.setRoot(this.sceneController.get("login").getPane());
    }

}
