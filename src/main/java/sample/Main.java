package sample;

import com.snakesonaplane.jeu.GameCreator;
import com.snakesonaplane.jeu.Player;
import com.snakesonaplane.exceptions.UnableToCreateGameException;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithm1;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
        primaryStage.setTitle("Snakes on a (two-dimensional) plane");
        primaryStage.setScene(new Scene(root, 500, 275));
        primaryStage.show();


        GameCreator g = new GameCreator();

        g.setPlayerTypesLineup(Arrays.asList(new Player("Bob", false), new Player("Roger", true), new Player("Rock", false)));
        g.setMoveAlgorithm(new MoveAlgorithm1());


        GameCreator gameCreator = new GameCreator();

        gameCreator
                .setMoveAlgorithm(new MoveAlgorithm1())
                .setPlayerTypesLineup(Arrays.asList(new Player("Bob", false), new Player("Roger", true), new Player("Rock", false)))
                .setNumberOfCells(36)
                .setNumberOfLadders(2)
                .setNumberOfSnakes(2)
                .setNumberOfFacesOnDice(42);

        try {
            gameCreator.create();
        } catch (UnableToCreateGameException e) {
            System.out.println("Impossible de créer le jeu");
        }


        System.out.println(g);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
