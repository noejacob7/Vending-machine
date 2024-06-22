package scrum.a2.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import scrum.a2.VendingMachine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PrivilegedAction;
import java.util.HashMap;
import javafx.scene.text.Text;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.util.Pair;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
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
import javafx.scene.control.ComboBox;

public class RoleModifyController  implements Controller{
    private Pane pane;
    private VendingMachine model;
    private Scene scene;
    @FXML
    private Text message;
    private HashMap<String, Controller> sceneController = new HashMap<String, Controller>();


    @FXML
    private Button add;

    @FXML
    private ComboBox<String> roleOptions;

    @FXML
    private TextField userTextArea;

    @FXML
    void addRole(ActionEvent event) {
        System.out.println(userTextArea.getText());
        System.out.println(roleOptions.getValue());
        Pair<Boolean, String> role = this.model.changeRole(userTextArea.getText(),roleOptions.getValue());
        // this.scene.setRoot(this.sceneController.get("operation-verify").getPane());
        if(role.getKey()){

            OperationVerifyController controller = (OperationVerifyController )this.sceneController.get("operation-verify");
            controller.setMessage(role.getValue());
            this.scene.setRoot(this.sceneController.get("operation-verify").getPane());
        }else{
            this.message.setText(role.getValue());
        }

    }

   
    public void setPane(Pane pane){
        this.pane = pane;
    }
    public void setModel(VendingMachine model){
        this.model = model;
        this.roleOptions.getItems().addAll("CUSTOMER","SELLER", "CASHIER", "OWNER");
        this.roleOptions.getSelectionModel().select("CUSTOMER");
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
    public void setRole(String role){
        this.roleOptions.getSelectionModel().select(role);
    }
    public void setUp(){
        this.message.setText("");
        this.userTextArea.setText("");
        this.roleOptions.getSelectionModel().select("CUSTOMER");
        

    }
    @FXML
    void back(MouseEvent event) {
        this.sceneController.get("login").setUp();
        this.scene.setRoot(this.sceneController.get("login").getPane());
    }
   
    
}
