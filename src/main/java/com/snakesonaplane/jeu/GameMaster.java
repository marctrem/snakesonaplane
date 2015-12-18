package com.snakesonaplane.jeu;

import com.esotericsoftware.yamlbeans.YamlException;
import com.snakesonaplane.ctrl.BoardCtrl;
import com.snakesonaplane.ctrl.GameSetupCtrl;
import com.snakesonaplane.exceptions.UnableToCreateGameException;
import com.snakesonaplane.exceptions.UnknownAlgorithmException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;


public class GameMaster implements
        GameSetupCtrl.GameSetupReceiver,
        BoardCtrl.DiceRollListener,
        Game.PlayerReadyCallback

{

    private Stage gameStage;
    private BoardCtrl boardController;

    private Game game;

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
            this.gameStage.setScene(new Scene(root, 800, 600));
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
            this.boardController = loader.getController();
            this.boardController.setGameMaster(this);
            this.gameStage.setTitle("Snakes on a (two-dimensional) plane");
            this.gameStage.setScene(new Scene(root, 800, 600));

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load board fxml file.");
        }
    }


    @Override
    public void onGameSetupCompleted(List<Player> players, String algoName, Integer numberOfFacesOnDice) {
        System.out.println("Setup completed!");

        GameCreator gameCreator = new GameCreator();

        try {
            gameCreator
                    .loadConfig("game_config/game_config.yaml")
                    .setMoveAlgorithm(algoName)
                    .setPlayerTypesLineup(players)
                    .setNumberOfFacesOnDice(numberOfFacesOnDice);

            this.game = gameCreator.create();


            System.out.println(this.game);

            spawnBoardWindow();


            this.boardController.setupBoard(this.game.board);


            if (this.game.getPlayerList().get((int) game.getPlayerToPlay()).isAi()) {
                gameLoop();
            } else {
                onPlayerReadyToPlay();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (YamlException e) {
            e.printStackTrace();
        } catch (UnknownAlgorithmException e) {
            e.printStackTrace();
        } catch (UnableToCreateGameException e) {
            e.printStackTrace();
        }


    }

    public void gameLoop() {
        boolean victory = false;
        victory = game.play();

        while (game.getPlayerList().get((int) game.getPlayerToPlay()).isAi() && !victory) {
            victory = game.play();
        }

        if (victory) {
            // Todo: stuff to do on victory
            System.out.println("Player " + game.getPlayerList().get((int) game.getPlayerToPlay()).getName() + " WON !");
        } else {
            onPlayerReadyToPlay();
        }
    }

    @Override
    public void onDiceRolled() {
        this.boardController.setPlayBtnClickable(false);
        gameLoop();
    }

    @Override
    public void onPlayerReadyToPlay() {
        this.boardController.setPlayBtnClickable(true);
    }

    public void onUndoRequested() {
    }

    public void onRedoRequested() {
    }

    public boolean isUndoAvailable() {
        return true;
    }

    public boolean isRedoAvailable() {
        return false;
    }
}
