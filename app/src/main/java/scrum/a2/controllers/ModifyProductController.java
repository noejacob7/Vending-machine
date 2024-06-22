package scrum.a2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import scrum.a2.VendingMachine;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import scrum.a2.VendingMachine;
import javafx.scene.image.Image;
import scrum.a2.Window;
import scrum.a2.Item;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PrivilegedAction;
import java.util.HashMap;
import javafx.scene.Scene;
import javafx.util.Pair;

public class ModifyProductController implements Controller{
    
    private Pane pane;
    private VendingMachine model;
    private Scene scene;
    private HashMap<String, Controller> sceneController = new HashMap<String, Controller>();
    HashMap<String , String> images = new HashMap<>();
    @FXML
    private ToggleButton candyButton;

    @FXML
    private ToggleButton chipButton;

    @FXML
    private ToggleButton chocolateButton;

    @FXML
    private ToggleButton chocolateButton1;

    @FXML
    private Text errorMessage;

    @FXML
    private TextField itemCode;

    @FXML
    private ImageView productImage;

    @FXML
    private TextField productName;

    @FXML
    private TextField productPrice;
    private Integer oldProductCode;

    @FXML
    private Spinner<Integer> quantity;
    
    public ModifyProductController(){
        images.put("Drinks","src/main/resources/images/coffee-glass.png");
        images.put("Chocolates","src/main/resources/images/chocolate2.png");
        images.put("Chips","src/main/resources/images/cracker.png");
        images.put("Candies","src/main/resources/images/candy.png");
    }


    @FXML
    void back(MouseEvent event) {
        this.scene.setRoot(this.sceneController.get("product-list").getPane());

    }
    @FXML
    void logout(MouseEvent event) {
        this.model.logout();
        this.sceneController.get("login").setUp();
        this.scene.setRoot(this.sceneController.get("login").getPane());
    }

    @FXML
    void candyToggle(MouseEvent event) {

    }

    @FXML
    void chipToggle(MouseEvent event) {

    }

    @FXML
    void chocolateToggle(MouseEvent event) {

    }

    @FXML
    void drinkToggle(MouseEvent event) {

    }

    @FXML
    void modifyProduct(ActionEvent event) {
        Long newQuantity = Long.valueOf(this.quantity.getValue().longValue());

        Pair<Boolean,String> msg = this.model.modifyProduct(this.oldProductCode, itemCode.getText(),
        productName.getText(), productPrice.getText(),newQuantity);
        if (msg.getKey()){
            OperationVerifyController controller = (OperationVerifyController )this.sceneController.get("operation-verify");
            controller.setMessage(msg.getValue());
            this.scene.setRoot(this.sceneController.get("operation-verify").getPane());
        }else {
            this.errorMessage.setText(msg.getValue());
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
    public Pane getPane(){
        return this.pane;
    }
    public void setProduct(Integer code,Item item){
        try{
            InputStream stream = new FileInputStream(images.get(item.getCategory()));
            Image image = new Image(stream);
            Integer itemQuantity = (int) (long) item.getQuantity();
            this.productImage.setImage(image);
            this.oldProductCode = code;

            this.itemCode.setPromptText(String.valueOf(code));
            this.quantity.getValueFactory().setValue(itemQuantity);
            this.productName.setPromptText(item.getName());
            this.productPrice.setPromptText(String.format("$ %.2f",item.getPrice()));
            this.productPrice.setText("");
            this.productName.setText("");
            this.itemCode.setText("");
            this.errorMessage.setText("");
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void setUp(){
      

    }

}
