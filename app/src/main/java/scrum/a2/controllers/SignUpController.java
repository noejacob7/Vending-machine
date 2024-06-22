package scrum.a2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.util.Pair;
import scrum.a2.VendingMachine;
import scrum.a2.Window;
import javafx.scene.Scene;
import java.util.*;


public class SignUpController implements Controller{


  

    @FXML
    private TextField userTextArea;
    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordVerify;
    private Label message;



    @FXML
    private Scene scene;
    
    private VendingMachine model;
    private Window window;

    private HashMap<String, Controller> sceneController;
    private Pane pane;

    public void setPane(Pane pane){
        this.pane = pane;
    }

    public SignUpController(){
        this.message = new Label();
    
    }
    public void setScene(Scene scene){
        this.scene = scene;

    }

    public void setSceneController(HashMap<String, Controller> sceneController){
        this.sceneController = sceneController;

    }

    public void setModel(VendingMachine model){
        this.model = model;
    }

    public void setWindow(Window window){
        this.window = window;
    }

    
    public void setMessage(){


        Font font = Font.font("Impact", FontWeight.BOLD, FontPosture.REGULAR, 12);
        this.message.setFont(font);
        this.message.setTextFill(Color.PINK);
        this.message.setTranslateX(40);
        this.message.setTranslateY(530);
        this.pane.getChildren().add(this.message);
    }

    public Pane getPane(){
        return this.pane;
    }
    
    public void setUp(){
        setMessage();
    }

    @FXML
    void checkSignUp(MouseEvent event) throws Exception{
     
        String username = this.userTextArea.getText();
        String password = this.password.getText();
        String verifyPassword = this.passwordVerify.getText();
     

        Pair<Boolean , String>  checkSignUp = this.model.addLogin(username, password, verifyPassword);
    
        if (checkSignUp.getKey()) {

            this.scene.setRoot(this.sceneController.get("order-record").getPane());
        }else{
       
            this.message.setText(checkSignUp.getValue());

        }

    }

    

}
