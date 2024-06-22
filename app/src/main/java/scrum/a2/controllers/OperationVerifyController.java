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
import javafx.scene.text.Text;

public class OperationVerifyController implements Controller{
    @FXML
    private Text message;
    private Scene scene;
    private HashMap<String, Controller> sceneController = new HashMap<String, Controller>();
    private VendingMachine model ;
    private Window window;
    private Pane pane;



    public OperationVerifyController(){
        
    }
    public void setPane(Pane pane){
        this.pane = pane;
    }
    public void setUp(){
        //reset


    }
  

    public void setWindow(Window window){
        this.window = window;
    }
    public void setModel(VendingMachine model) throws Exception{
        this.model = model;
      
    }
    public void setSceneController(HashMap<String, Controller> sceneController){
        this.sceneController = sceneController;
    }
    public Pane getPane(){
        return this.pane;
    }
    public void setScene(Scene scene){

        this.scene = scene;
    }
    public void setMessage(String message){
        this.message.setText(message);
    }
 
    @FXML
    void logOut(MouseEvent event) {
        this.model.logout();
        this.sceneController.get("login").setUp();
        this.scene.setRoot(this.sceneController.get("login").getPane());

    }

}
