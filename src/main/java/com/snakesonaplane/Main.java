package com.snakesonaplane;

import com.snakesonaplane.wrappers.UIAbstractFactoryFactoryMethod;
import com.snakesonaplane.wrappers.UIAbstractFactorySingleton;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        UIAbstractFactorySingleton.initialize(UIAbstractFactoryFactoryMethod.UIType.JAVAFX);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //noinspection ConstantConditions
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
        primaryStage.setTitle("Snakes on a (two-dimensional) plane");
        primaryStage.setScene(new Scene(root, 500, 275));
        primaryStage.show();
    }
}
