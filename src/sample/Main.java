package sample;

import com.snakesonaplane.jeu.GameCreator;
import com.snakesonaplane.jeu.Player;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithm1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Collections;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


        GameCreator g = new GameCreator();

        g.setPlayerTypesLineup(Arrays.asList(Player.PlayerType.ARTIFICIAL, Player.PlayerType.HUMAN, Player.PlayerType.HUMAN));
        g.setMoveAlgorithm(new MoveAlgorithm1());

        System.out.println(g);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
