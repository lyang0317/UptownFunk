package com.javafx;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class ClassColorfulCircles extends Application {

    public static void main(String[] args) {
        launch(args);
    }   

    @Override
    public void start(Stage primaryStage){      
    	Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        primaryStage.setScene(scene);
        
        Group circles = new Group();
        for (int i = 0; i < 30; i++) {
           Circle circle = new Circle(150, Color.web("white", 0.05));
           circle.setStrokeType(StrokeType.OUTSIDE);
           circle.setStroke(Color.web("white", 0.16));
           circle.setStrokeWidth(4);
           circles.getChildren().add(circle);
        }
        root.getChildren().add(circles);
        circles.setEffect(new BoxBlur(10, 10, 3));
        primaryStage.show();
    }

}
