package scrum.a2;

import scrum.a2.controllers.*;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;

import java.io.IOException;
import javafx.scene.layout.Pane;
import javafx.scene.Parent;
import java.net.URL;
import javafx.util.Duration;

import java.util.*;

public class Window{

 
    private Scene scene;



    private HashMap<String, Controller> sceneController=  new HashMap<>();

    private VendingMachine model;
    private String[] scenePath = {
        "../../../../resources/main/login.fxml",
    "../../../../resources/main/signUp.fxml", 
    "../../../../resources/main/snacksCorner.fxml",
    "../../../../resources/main/cart.fxml",
    "../../../../resources/main/verification.fxml",
    "../../../../resources/main/record.fxml",
    "../../../../resources/main/cashPayment.fxml",
    "../../../../resources/main/change.fxml",
    "../../../../resources/main/owner_operations.fxml",
    "../../../../resources/main/role_modify.fxml",
    "../../../../resources/main/verify_operation.fxml",
    "../../../../resources/main/product-list.fxml",
    "../../../../resources/main/modify-product.fxml",
    "../../../../resources/main/cash_front.fxml",
    "../../../../resources/main/cashier.fxml",
    "../../../../resources/main/sellerOperation.fxml"}; 

    public Window(VendingMachine model, Stage primaryStage) throws Exception{
        // this.timer = new Timeline(new KeyFrame(Duration.seconds(3600), new EventHandler<ActionEvent>() {
        this.scene = new Scene(new Pane());
        this.model = model;
        loadLoginScene();
        signUpScene();
        snacksCornerScene();
        cartScene();
        verificationScene();
        recordScene();
        transactionScene();
        changeScene();
        ownerLoad();
        modifyRoleLoad();
        opertionVerifyLoad();
        ProductListLoad();
        ModifyProductLoad();
        ModifyCashLoad();
        SellerLoad();
        CashierLoad();
        Duration delay = Duration.seconds(120);
        PauseTransition transition = new PauseTransition(delay);
        transition.setOnFinished(evt-> logout());
        scene.addEventFilter(InputEvent.ANY, evt -> transition.playFromStart());
        
        this.scene.setRoot(this.sceneController.get("login").getPane());
        primaryStage.setTitle("Vending Machine System");
        primaryStage.setScene(this.scene);

        primaryStage.show();
        transition.play();

    }
    public void logout(){
        LoginController controller = (LoginController) this.sceneController.get("login");
        this.model.cancelTransaction("Time Out : exceed 2 minutes inactivity");
        this.model.logout();
        controller.setUp();
        this.scene.setRoot(controller.getPane());
    }
    
    public void loadLoginScene() throws IOException{

        URL fxmlLocation = getClass().getResource(scenePath[0]);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);

        Pane root = loader.load();
        LoginController controller = loader.getController();
        controller.setPane(root);

        controller.setModel(this.model);
        controller.setScene(this.scene);
        this.sceneController.put("login", controller);
        controller.setSceneController(this.sceneController);


    }
  

    public void signUpScene() throws IOException{

        URL fxmlLocation = getClass().getResource(scenePath[1]);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
     
        SignUpController controller = loader.getController();
        controller.setPane(root);
        controller.setModel(this.model);
        controller.setScene(this.scene);
        this.sceneController.put("signUp", controller);
        controller.setSceneController(this.sceneController);

    }
    public void snacksCornerScene() throws Exception{

        URL fxmlLocation = getClass().getResource(scenePath[2]);
       
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
    
        SnacksCornerController controller = loader.getController();
        controller.setSceneController(this.sceneController);
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        this.sceneController.put("snacks-corner",controller);


    }
    public void cartScene() throws Exception{

        URL fxmlLocation = getClass().getResource(scenePath[3]);
        System.out.println(fxmlLocation);
     
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
        
        CartController controller = loader.getController();
        controller.setSceneController(this.sceneController);
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        this.sceneController.put("cart",controller);




    }
    public void verificationScene() throws Exception{

        URL fxmlLocation = getClass().getResource(scenePath[4]);
        System.out.println(fxmlLocation);
     
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
        
        VerifyController controller = loader.getController();
        controller.setSceneController(this.sceneController);
        controller.setScene(this.scene);
        controller.setPane(root);
        controller.setModel(this.model);
        // controller.setWindow(this);
        this.sceneController.put("verify",controller);


    }
    public void recordScene() throws Exception{

        URL fxmlLocation = getClass().getResource(scenePath[5]);
        
     
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
        // this.sceneCollections.put("order-record",root);
        RecordController controller = loader.getController();
        // controller.setSceneCollections(this.sceneCollections);
        controller.setScene(this.scene);
        controller.setPane(root);
        controller.setSceneController(this.sceneController);
        controller.setModel(this.model);
        this.sceneController.put("order-record",controller);
    }
    public cashPaymentController transactionScene() throws Exception{
      
        URL fxmlLocation = getClass().getResource(scenePath[6]);
        
    
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
        
        cashPaymentController controller = loader.getController();

        controller.setScene(this.scene);

        controller.setPane(root);
        controller.setModel(this.model);
        this.sceneController.put("transaction",controller);
        controller.setSceneController(this.sceneController);
        return controller;
  
        

    }
    public void changeScene() throws Exception{
    
        URL fxmlLocation = getClass().getResource(scenePath[7]);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
        ChangeController controller = loader.getController();
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        controller.setSceneController(this.sceneController);
        this.sceneController.put("change",controller);        
    }
    public void ownerLoad() throws Exception{
    
        URL fxmlLocation = getClass().getResource(scenePath[8]);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
        OwnerController controller = loader.getController();
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        controller.setSceneController(this.sceneController);
        this.sceneController.put("owner",controller);        
    }
    public void modifyRoleLoad() throws Exception{
    
        URL fxmlLocation = getClass().getResource(scenePath[9]);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
        RoleModifyController controller = loader.getController();
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        controller.setSceneController(this.sceneController);
        controller.setUp();
        this.sceneController.put("modify-role",controller);        
    }
    public void opertionVerifyLoad() throws Exception{
    
        URL fxmlLocation = getClass().getResource(scenePath[10]);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Pane root = loader.load();
        OperationVerifyController controller = loader.getController();
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        controller.setSceneController(this.sceneController);
        this.sceneController.put("operation-verify",controller);        
    }
    public void ProductListLoad() throws Exception{
    
        URL fxmlLocation = getClass().getResource(scenePath[11]);
        // System.out.println(fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        // System.out.println(loader);
        Pane root = loader.load();
        ProductListController controller = loader.getController();
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        controller.setSceneController(this.sceneController);
        this.sceneController.put("product-list",controller);        
    }
    public void ModifyProductLoad() throws Exception{
    
        URL fxmlLocation = getClass().getResource(scenePath[12]);
        System.out.println(fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        System.out.println(loader);
        Pane root = loader.load();
        ModifyProductController controller = loader.getController();
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        controller.setSceneController(this.sceneController);
        this.sceneController.put("modify-product",controller);        
    }
    public void ModifyCashLoad() throws Exception{
    
        URL fxmlLocation = getClass().getResource(scenePath[13]);
        System.out.println(fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        System.out.println(loader);
        Pane root = loader.load();
        ModifyCashController controller = loader.getController();
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        controller.setSceneController(this.sceneController);
        this.sceneController.put("modify-cash",controller);        
    }
    public void CashierLoad() throws Exception{
    
        URL fxmlLocation = getClass().getResource(scenePath[14]);
        System.out.println(fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        System.out.println(loader);
        Pane root = loader.load();
        CashierController controller = loader.getController();
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        controller.setSceneController(this.sceneController);
        this.sceneController.put("cashier",controller);        
    }
    public void SellerLoad() throws Exception{
    
        URL fxmlLocation = getClass().getResource(scenePath[15]);
        System.out.println(fxmlLocation);
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        System.out.println(loader);
        Pane root = loader.load();
        SellerController controller = loader.getController();
        controller.setScene(this.scene);
        controller.setModel(this.model);
        controller.setPane(root);
        
        controller.setSceneController(this.sceneController);
        
        this.sceneController.put("seller",controller);        
    }


    public Scene getScene(){
        return this.scene;
    }

}
