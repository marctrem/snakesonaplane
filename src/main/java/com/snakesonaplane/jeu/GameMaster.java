package com.snakesonaplane.jeu;

import com.snakesonaplane.ctrl.BoardCtrl;
import com.snakesonaplane.ctrl.GameSetupCtrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class GameMaster implements GameSetupCtrl.GameSetupReceiver {

    private Stage gameStage;

    public GameMaster() {
        this.gameStage = new Stage();
        this.gameStage.show();
    }

    public void startGame() {
        this.spawnGameSetupWindow();

    }

    private void spawnGameSetupWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("game_setup.fxml"));
            Parent root = loader.load();

            GameSetupCtrl gameSetupCtrl = loader.getController();
            gameSetupCtrl.setGameMaster(this);

            gameStage.setTitle("Snakes on a (two-dimensional) plane");
            this.gameStage.setScene(new Scene(root, 500, 275));
            //.show();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load game_setup fxml file.");
        }
    }

    private void spawnBoardWindow() {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("board.fxml"));
            Parent root = loader.load();
            BoardCtrl boardCtrl = loader.getController();
            boardCtrl.setGameMaster(this);
            this.gameStage.setTitle("Snakes on a (two-dimensional) plane");
            this.gameStage.setScene(new Scene(root, 500, 275));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load board fxml file.");
        }
    }


    @Override
    public void onGameSetupCompleted(List<GameSetupCtrl.PlayerSetupInfo> playerSetupInfoList) {
        System.out.println("Setup completed!");

        // Todo: Convert received list to what is expected by the game.
        spawnBoardWindow();
    }
}
