package scrum.a2.controllers;

import javafx.event.ActionEvent;
import scrum.a2.VendingMachine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PrivilegedAction;
import java.util.HashMap;
import javafx.util.Pair;
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

public class OwnerController implements Controller{
    
    private Pane pane;
    private VendingMachine model;
    private Scene scene;
    private HashMap<String, Controller> sceneController = new HashMap<String, Controller>();
    
    @FXML
    void modifyCash(ActionEvent event) {
        this.sceneController.get("modify-cash").setUp();
        this.scene.setRoot(this.sceneController.get("modify-cash").getPane());


    }
    @FXML
    void logout(MouseEvent event) {
        this.model.logout();
        this.sceneController.get("login").setUp();
        this.scene.setRoot(this.sceneController.get("login").getPane());
    }

    @FXML
    void modifyProduct(ActionEvent event) {
        this.sceneController.get("product-list").setUp();
        this.scene.setRoot(this.sceneController.get("product-list").getPane());

    }
    @FXML
    void reportAccount(ActionEvent event) {
        Pair<Boolean,String> msg = this.model.createUsernameReport();
        OperationVerifyController controller = (OperationVerifyController )this.sceneController.get("operation-verify");
        controller.setMessage(msg.getValue());
        this.scene.setRoot(this.sceneController.get("operation-verify").getPane());

    }

    @FXML
    void modifyRole(ActionEvent event) {
        this.sceneController.get("modify-role").setUp();
        this.scene.setRoot(this.sceneController.get("modify-role").getPane());

    }

    @FXML
    void reportCash(ActionEvent event) {
        Pair<Boolean,String> msg = this.model.createAvailableChangeReport();
        OperationVerifyController controller = (OperationVerifyController )this.sceneController.get("operation-verify");
        controller.setMessage(msg.getValue());
        this.scene.setRoot(this.sceneController.get("operation-verify").getPane());

    }

    @FXML
    void reportProduct(ActionEvent event) {
        Pair<Boolean,String> msg = this.model.generateAvailableProductsReport();
        OperationVerifyController controller = (OperationVerifyController )this.sceneController.get("operation-verify");
        controller.setMessage(msg.getValue());
        
        this.scene.setRoot(this.sceneController.get("operation-verify").getPane());
    }
    @FXML
    void generateCancel(ActionEvent event) {
        Pair<Boolean,String> msg = this.model.createCancelledTransactionReport();
        OperationVerifyController controller = (OperationVerifyController )this.sceneController.get("operation-verify");
        controller.setMessage(msg.getValue());
        
        this.scene.setRoot(this.sceneController.get("operation-verify").getPane());
    }

    @FXML
    void summaryCash(ActionEvent event) {
        Pair<Boolean,String> msg = this.model.createSummaryOfTransactionsReport();
        OperationVerifyController controller = (OperationVerifyController )this.sceneController.get("operation-verify");
        controller.setMessage(msg.getValue());
        
        this.scene.setRoot(this.sceneController.get("operation-verify").getPane());
    }

    @FXML
    void summaryProduct(ActionEvent event) {
        Pair<Boolean,String> msg = this.model.generateProductsSoldReport();
        OperationVerifyController controller = (OperationVerifyController )this.sceneController.get("operation-verify");
        controller.setMessage(msg.getValue());
        this.scene.setRoot(this.sceneController.get("operation-verify").getPane());

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
    public void setUp(){

    }
    
}
