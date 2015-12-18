package com.snakesonaplane.jeu;

import com.snakesonaplane.exceptions.GameStateOutOfBoundException;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Game {

    final static long MAX_STATES = 10;
    int currentGameStateIndex = -1;
    Board board;

    GameState currentGameState;
    List<GameMemento> gameMementos;

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

    Dice dice;
    MoveAlgorithm moveAlgorithm;
    Game(Board board, List<Player> players, MoveAlgorithm moveAlgorithm, Dice dice) {

        this.board = board;
        this.moveAlgorithm = moveAlgorithm;
        this.dice = dice;

        GameMemento memento = new GameMemento(players, 0);

        this.currentGameState = memento.getState();

        this.gameMementos = new ArrayList<>(10);

        this.gameMementos.add(memento);
        ++this.currentGameStateIndex;
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

        this.addCurrentState();

        return false;
    }

    public GameMemento getPreviousState() throws GameStateOutOfBoundException {
        GameState state;
        if (this.currentGameStateIndex == -1) {
            throw new GameStateOutOfBoundException();
        }
        return this.gameMementos.get(this.currentGameStateIndex--);
    }

    public GameMemento getNextState() throws GameStateOutOfBoundException {
        GameState state;
        if (this.gameMementos.size() <= this.currentGameStateIndex + 1) {
            throw new GameStateOutOfBoundException();
        }

        return this.gameMementos.get(++this.currentGameStateIndex);
    }

    public void addCurrentState() {
        this.gameMementos.add(new GameMemento(this.currentGameState.players, this.currentGameState.currentPlayer));

        this.currentGameStateIndex++;

        // Erase next states
        for (long i = this.currentGameStateIndex; i < MAX_STATES; i++) {
            this.gameMementos.remove(i);
        }

        if (this.gameMementos.size() > 10) {
            List<GameMemento> new_list = new ArrayList<>(10);

            for (int i = 1; i < this.gameMementos.size(); i++) {
               new_list.add(this.gameMementos.get(i));
            }

            this.gameMementos = new_list;
            this.currentGameStateIndex = 9;
        }
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
}
