package scrum.a2.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.*;



import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import scrum.a2.VendingMachine;
import scrum.a2.Window;

public class LoginController implements Controller {


  
    private Pane pane;
    @FXML
    private PasswordField password;

    @FXML
    private Button registerButton;

    @FXML
    private TextField userTextArea;

    @FXML
    private VendingMachine model;

    private Label message;

    private Scene scene;
    private HashMap<String,Controller> sceneController;

    public LoginController(){
        this.message = new Label();


    }
    public void setPane(Pane pane){
        this.pane = pane;
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }
    public void setSceneController(HashMap<String, Controller> sceneController){
        this.sceneController = sceneController;
    }
    public void setModel(VendingMachine model){
        this.model = model;
        setMessage();
    }
 
    public HashMap<String, Controller> getSceneController(){
        return this.sceneController;
    }
    public void setUp(){
        this.message.setText("");
        this.userTextArea.setText("");
        this.password.setText("");
        
    }
    public void setMessage(){

        Font font = Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, 12);
        this.message.setFont(font);
        //Filling color to the this.message
        this.message.setTextFill(Color.PINK);
        //Setting the position
        this.message.setTranslateX(40);
        this.message.setTranslateY(540);
        this.pane.getChildren().add(this.message);
    }
    @FXML
    void registerAccount(ActionEvent event) {

        Pane root = this.sceneController.get("signUp").getPane();
        this.scene.setRoot(root);



    }
    public Pane getPane() {
        return this.pane;
    }
    @FXML
    void checkLoginStatus(MouseEvent event) throws Exception {

        String username = this.userTextArea.getText();
        String pass = this.password.getText();

        Pair<Boolean,String> loginStatus = this.model.checkLogin(username, pass);
        if (loginStatus.getKey()){
            String role = loginStatus.getValue().toUpperCase();
            System.out.println(role);
            if (role.equals("CUSTOMER")){
                this.sceneController.get("order-record").setUp();

                this.scene.setRoot(this.sceneController.get("order-record").getPane());
            }else if (role.equals("SELLER")){

                this.sceneController.get("seller").setUp();
                this.scene.setRoot(this.sceneController.get("seller").getPane());

            }else if (role.equals("CASHIER")){

                this.sceneController.get("cashier").setUp();
                this.scene.setRoot(this.sceneController.get("cashier").getPane());

            }else if (role.equals("OWNER")){

                this.sceneController.get("owner").setUp();
                this.scene.setRoot(this.sceneController.get("owner").getPane());

            }
        }else{
            this.message.setText(loginStatus.getValue());
        }

    }
    @FXML
    void guest(ActionEvent event) {

        this.sceneController.get("order-record").setUp();
        this.scene.setRoot(this.sceneController.get("order-record").getPane());

    }
  

}
