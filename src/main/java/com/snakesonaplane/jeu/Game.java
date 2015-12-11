package com.snakesonaplane.jeu;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.snakesonaplane.exceptions.GameStateOutOfBoundException;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game {

    final static long MAX_STATES = 10;

    List<GameState> gameStates;
    int currentGameState = -1;
    long playerToPlay = 0;

    Board board;
    List<Player> players;
    Dice dice;
    MoveAlgorithm moveAlgorithm;

    Game(Board board, List<Player> players, MoveAlgorithm moveAlgorithm, Dice dice) {

        this.board = board;
        this.players = players;
        this.moveAlgorithm = moveAlgorithm;
        this.dice = dice;

        this.gameStates = new ArrayList<>(10);
        this.addCurrentState();
    }

    public boolean play() {
        long diceRoll = this.dice.roll();
        Player player = this.players.get((int) this.playerToPlay);
        long pos = player.getPosition();

        pos = this.moveAlgorithm.getMove(pos, this.board.numberOfCells, diceRoll);
        player.setPosition(this.board.getMove(pos));

        if (player.getPosition() == this.board.numberOfCells) {
            return true;
        }

        this.playerToPlay = (this.playerToPlay + 1) % players.size();
        this.addCurrentState();

        return false;
    }

    public GameState getPreviousState() throws GameStateOutOfBoundException {
        GameState state;
        if (this.currentGameState == -1) {
            throw new GameStateOutOfBoundException();
        }
        return this.gameStates.get(this.currentGameState--);
    }

    public GameState getNextState() throws GameStateOutOfBoundException {
        GameState state;
        if (this.gameStates.size() <= this.currentGameState + 1) {
            throw new GameStateOutOfBoundException();
        }

        return this.gameStates.get(++this.currentGameState);
    }

    public void addCurrentState() {
        this.gameStates.add(new GameState(this.players, this.playerToPlay));
        this.currentGameState++;

        // Erase next states
        for (long i = this.currentGameState; i < this.MAX_STATES; i++) {
            this.gameStates.remove(i);
        }

        if (this.gameStates.size() > 10) {
            List<GameState> new_list = new ArrayList<>();

            for (int i = 1; i < this.gameStates.size(); i++) {
               new_list.add(this.gameStates.get(i));
            }

            this.gameStates = new_list;
            this.currentGameState = 9;
        }
    }

    public long getPlayerToPlay() {
        return playerToPlay;
    }
}
