package com.snakesonaplane.jeu;

import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class GameCreator {

    private int numberOfFacesOnDice = 0;
    private long numberOfCells = 0;
    private MoveAlgorithm moveAlgorithm = null;
    private long numberOfLadders = 0;
    private long numberOfSnakes = 0;
    private List<Player.PlayerType> playerTypesLineup;


    public Game create() {

        // Todo: do some validity checks.

        Board board = new Board(this.numberOfCells, this.numberOfLadders, this.numberOfSnakes);
        List<Player> players = new ArrayList<>(playerTypesLineup.size());
        Dice dice = new Dice(this.numberOfFacesOnDice);

        return new Game(board, players, this.moveAlgorithm, dice);

    }

    @Override
    public String toString() {
        return "GameCreator{" +
                "numberOfFacesOnDice=" + numberOfFacesOnDice +
                ", numberOfCells=" + numberOfCells +
                ", moveAlgorithm=" + moveAlgorithm +
                ", numberOfLadders=" + numberOfLadders +
                ", numberOfSnakes=" + numberOfSnakes +
                ", playerTypesLineup=" + playerTypesLineup +
                '}';
    }

    public int getNumberOfFacesOnDice() {
        return numberOfFacesOnDice;
    }

    public GameCreator setNumberOfFacesOnDice(int numberOfFacesOnDice) {
        this.numberOfFacesOnDice = numberOfFacesOnDice;
        return this;
    }

    public long getNumberOfCells() {
        return numberOfCells;
    }

    public GameCreator setNumberOfCells(long numberOfCells) {
        this.numberOfCells = numberOfCells;
        return this;
    }

    public MoveAlgorithm getMoveAlgorithm() {
        return moveAlgorithm;
    }

    public GameCreator setMoveAlgorithm(MoveAlgorithm moveAlgorithm) {
        this.moveAlgorithm = moveAlgorithm;
        return this;
    }

    public long getNumberOfLadders() {
        return numberOfLadders;
    }

    public GameCreator setNumberOfLadders(long numberOfLadders) {
        this.numberOfLadders = numberOfLadders;
        return this;
    }

    public long getNumberOfSnakes() {
        return numberOfSnakes;
    }

    public GameCreator setNumberOfSnakes(long numberOfSnakes) {
        this.numberOfSnakes = numberOfSnakes;
        return this;
    }

    public List<Player.PlayerType> getPlayerTypesLineup() {
        return playerTypesLineup;
    }

    public GameCreator setPlayerTypesLineup(List<Player.PlayerType> playerTypesLineup) {
        this.playerTypesLineup = playerTypesLineup;
        return this;
    }
}
