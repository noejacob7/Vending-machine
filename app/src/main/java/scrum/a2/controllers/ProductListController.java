package scrum.a2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import scrum.a2.Item;

import scrum.a2.VendingMachine;
import scrum.a2.Window;
import javafx.geometry.Pos;

import java.io.FileNotFoundException;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ProductListController implements Controller{
    private HashMap<String, Controller> sceneController= new HashMap<String, Controller>();
    private VendingMachine model ;

    private Scene scene;
    private Window window;

    @FXML
    private ToggleButton candyButton;

    @FXML
    private ToggleButton chipButton;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private ToggleButton chocolateButton;
    private ToggleButton currCategories;
    @FXML
    private ToggleButton drinkButton;
    private Pane pane;

    Font font = Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, 15);
    HashMap<String , String> images = new HashMap<>();

    public ProductListController() {
    
        images.put("Drinks","src/main/resources/images/coffee-glass.png");
        images.put("Chocolates","src/main/resources/images/chocolate2.png");
        images.put("Chips","src/main/resources/images/cracker.png");
        images.put("Candies","src/main/resources/images/candy.png");

    }
    public void setPane(Pane pane){
        this.pane = pane;
    }
   

    public void setSceneController(HashMap<String, Controller> sceneController){
        this.sceneController = sceneController;
    }
    private void checkQuantity(Item item, ImageView myimage, int x, int y, Button buttonAdd, String color){
        if (item.getQuantity() <= 0){
            try{
                String path = "src/main/resources/images/out-of-stock.png";
                InputStream stream = new FileInputStream(path);
                Image image = new Image(stream);
                myimage.setImage(image);
                buttonAdd.setStyle("-fx-background-color:grey; -fx-background-radius : 30;");
                buttonAdd.setCursor(Cursor.DEFAULT);
            }catch (Exception e){
                e.printStackTrace();
            }
            
        }else{
            try{
                String path = images.get(item.getCategory());
                InputStream stream = new FileInputStream(path);
                Image image = new Image(stream);
                myimage.setImage(image);
               
     
                buttonAdd.setStyle(String.format("-fx-background-color:%s; -fx-background-radius : 30;",color));
                buttonAdd.setCursor(Cursor.HAND);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
    private Group makeProduct(String path, int x, int y,Integer key,Item item) {
        if (item.getQuantity() <=0){
            path = "src/main/resources/images/out-of-stock.png";
        }
        Group labelGroup = new Group();
        try {
        

            InputStream stream = new FileInputStream(path);
            Image image = new Image(stream);
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setLayoutX(x);
            imageView.setLayoutY(y);
          
            labelGroup.getChildren().add(imageView);
            //product
            Font font = Font.font("Inika", FontWeight.NORMAL, FontPosture.REGULAR, 12);
            Label productlabel = new Label(String.format("%d | %s",key,item.getName()));
            productlabel.setMaxWidth(100);
            productlabel.setFont(font);
            productlabel.setTextFill(Color.rgb(132,128,144));
            productlabel.setTranslateX(x+10);
            productlabel.setTranslateY(y+110);
            productlabel.setAlignment(Pos.CENTER);
            labelGroup.getChildren().add(productlabel);
            

            //price
            Label pricelabel = new Label(String.format("$ %.2f",item.getPrice()));
            pricelabel.setFont(font);
            pricelabel.setTextFill(Color.rgb(132,128,144));
            pricelabel.setTranslateX(x+35);
            pricelabel.setTranslateY(y+125);
            pricelabel.setAlignment(Pos.CENTER);
            labelGroup.getChildren().add(pricelabel);
            Label quantitylabel = new Label(String.format("%d packs",item.getQuantity()));
            quantitylabel.setFont(font);
            quantitylabel.setTextFill(Color.rgb(132,128,144));
            quantitylabel.setTranslateX(x+35);
            quantitylabel.setTranslateY(y+138);
            quantitylabel.setAlignment(Pos.CENTER);
            labelGroup.getChildren().add(quantitylabel);

        
            
            //button
            Button button = new Button();
            button.setPrefWidth(100);
            button.setPrefHeight(21);
    
            button.setLayoutX(x+10);
            button.setLayoutY(y+155);
            button.setStyle("-fx-background-color:rgb(174,191,226); -fx-background-radius : 30;");
            button.setText("MODIFY");
            button.setFont(font);
            button.setCursor(Cursor.HAND);
            button.setOnAction(value ->  {
                //go next page
        
                ModifyProductController m = (ModifyProductController)this.sceneController.get("modify-product");
                m.setProduct(key, item);
                this.scene.setRoot(this.sceneController.get("modify-product").getPane());

            });
            labelGroup.getChildren().add(button);

          

            
       
        }catch(Exception e){
            e.printStackTrace();
        }
        return labelGroup;
        
    }
    private void displayProducts(Map<Integer,Item> products, String productPath){
        Map<Integer, Item> allProducts = products;
    
        Group productGroup = new Group();
        int xInitial = 90;
        int yInitial = 100;
        int index = 0;
        for (Integer key: products.keySet()){
          
            Item productItem = allProducts.get(key);
          

            Group product = makeProduct(productPath,xInitial,yInitial,key, productItem);
            xInitial+=120;
            if ((index+1)%3 == 0){
                yInitial=yInitial+180;
                xInitial=90;
            }
            productGroup.getChildren().add(product);
            index+=1;


        }
 

        this.scrollPane.setContent(productGroup);
    }
    //initial drink products
    private void makeDrinkProduct(){
        Map<Integer, Item> drinks = this.model.getDrinks();
      
        displayProducts(drinks, "src/main/resources/images/coffee-glass.png");

    }
    public void setUp(){
        
        this.makeDrinkProduct();
        // this.setDetails();
    }


    public void setModel(VendingMachine model){
        this.model = model;
    }
    public void setScene(Scene scene){

        this.scene = scene;
    }

    private void turnOffCurrToggle(){
        if (this.currCategories!= null){
            this.currCategories.setStyle("-fx-background-color : rgb(164,174,227); -fx-background-radius : 30; ");
            this.currCategories.setSelected(false);

        }else if (this.currCategories == null){
            this.drinkButton.setStyle("-fx-background-color : rgb(164,174,227); -fx-background-radius : 30; ");
            this.drinkButton.setSelected(false);

        }
    }


    @FXML
    void candyToggle(MouseEvent event) {
        turnOffCurrToggle();
        this.candyButton.setStyle("-fx-background-color : rgb(244,200,210); -fx-background-radius : 30; ");
        this.currCategories = this.candyButton;
        Map<Integer, Item> drinks = this.model.getCandies();
        displayProducts(drinks, "src/main/resources/images/candy.png");

    }
    public void setWindow(Window window){
        this.window = window;
    }

    @FXML
    void checkOutButton(ActionEvent event)throws Exception{
        
      
        
        
        this.sceneController.get("modify-product").setUp();
        this.scene.setRoot(this.sceneController.get("modify-product").getPane());
        

    }

    @FXML
    void chipToggle(MouseEvent event) {

        turnOffCurrToggle();
        this.chipButton.setStyle("-fx-background-color : rgb(244,200,210); -fx-background-radius : 30; ");
        this.currCategories = this.chipButton;
        Map<Integer, Item> drinks = this.model.getChips();
        displayProducts(drinks, "src/main/resources/images/cracker.png");


    }

    @FXML
    void chocolateToggle(MouseEvent event) {


        turnOffCurrToggle();
        this.chocolateButton.setStyle("-fx-background-color : rgb(244,200,210); -fx-background-radius : 30; ");
        this.currCategories = this.chocolateButton;
        Map<Integer, Item> drinks = this.model.getChocolates();
        displayProducts(drinks, "src/main/resources/images/chocolate2.png");


    }
    public Pane getPane() {
        return this.pane;
    }

    @FXML
    void drinkToggle(MouseEvent event) {
        turnOffCurrToggle();
        this.drinkButton.setStyle("-fx-background-color : rgb(244,200,210); -fx-background-radius : 30; ");
        this.currCategories = this.drinkButton;
        Map<Integer, Item> drinks = this.model.getDrinks();
        displayProducts(drinks, "src/main/resources/images/coffee-glass.png");

    }
  
    @FXML
    void back(MouseEvent event) {
        this.model.logout();
        this.sceneController.get("login").setUp();
        this.scene.setRoot(this.sceneController.get("login").getPane());
    }
}
