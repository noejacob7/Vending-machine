package scrum.a2.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.PrivilegedAction;

import java.util.HashMap;


import javafx.animation.KeyValue;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VerifyController implements Controller{
    private Scene scene;
    private HashMap<String, Controller> sceneController = new HashMap<String, Controller>();
    private VendingMachine model ;
    private Window window;
    private Pane pane;
    @FXML
    private ProgressBar progress;



    public VerifyController(){
        
    }
    public void setPane(Pane pane){
        this.pane = pane;
    }
    public void setUp(){
        //reset
        // this.progress.setProgress(0.0);
        this.scene.setRoot(this.pane);
        
        runProgress();
     

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
    public void runProgress() {
        System.out.println("i am here");
    
        IntegerProperty seconds = new SimpleIntegerProperty();
        this.progress.progressProperty().bind(seconds.divide(3.0));
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(seconds, 0)),
            new KeyFrame(Duration.minutes(3.0/60.0), e-> {
                // do anything you need here on completion...
                System.out.println("Minute over");
                this.model.logout();
                this.sceneController.get("login").setUp();
                this.scene.setRoot(this.sceneController.get("login").getPane());
            }, new KeyValue(seconds, 3))   
        );
        // timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        

        // // Thread.sleep(1000);
     
    }
  

}
