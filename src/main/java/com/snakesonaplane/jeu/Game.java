package com.snakesonaplane.jeu;

import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;

import java.util.List;

public class Game {

    Board board;

    GameState currentGameState;
    GameMementoManager gameMementoManager;
    Dice dice;
    MoveAlgorithm moveAlgorithm;

    class GameMemento {
        private GameState state;

        private GameMemento(List<Player> players, long currentPlayer) {
            this.state = new GameState(players, currentPlayer);
        }

        GameState getState() {
            return this.state;
        }

        void setState(GameState state) {
            this.state = state;
        }
    }

    Game(Board board, List<Player> players, MoveAlgorithm moveAlgorithm, Dice dice) {

        this.board = board;
        this.moveAlgorithm = moveAlgorithm;
        this.dice = dice;

        GameMemento memento = new GameMemento(players, 0);

        this.currentGameState = memento.getState();

        this.gameMementoManager = new GameMementoManager();
        this.gameMementoManager.addMemento(memento);
    }

    public boolean play() {
        long diceRoll = this.dice.roll();
        Player player = this.currentGameState.players.get((int) this.currentGameState.currentPlayer);
        System.out.println("It's " + player.getName() + "'s turn");
        System.out.println("Actual cell : " + player.getPosition());
        long pos = player.getPosition();
        System.out.println("Dice roll is : " + diceRoll);

        pos = this.moveAlgorithm.getMove(pos, this.board.numberOfCells, diceRoll);
        player.setPosition(this.board.getMove(pos));
        System.out.println("Arrival cell : " + player.getPosition());

        if (player.getPosition() == this.board.numberOfCells) {
            return true;
        }

        this.currentGameState.currentPlayer =
                (this.currentGameState.currentPlayer + 1) % this.currentGameState.players.size();

        this.gameMementoManager.addMemento(this.createMemento(this.getPlayerList(), this.getPlayerToPlay()));

        return false;
    }


    public long getPlayerToPlay() {
        return this.currentGameState.currentPlayer;
    }
    public List<Player> getPlayerList() {
        return this.currentGameState.players;
    }

    public interface PlayerReadyCallback {
        void onPlayerReadyToPlay();
    }

    public GameMemento createMemento(List<Player> players, long currentPlayer) {
        return new GameMemento(players, currentPlayer);
    }

    public void setMemento(GameMemento memento) {
        this.currentGameState = memento.getState();
    }
}
