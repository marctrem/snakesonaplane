package com.snakesonaplane.jeu;

import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;

import java.util.List;

public class GameCreator {

    private long numberOfFacesOnDice = 0;
    private long numberOfSquares = 0;
    private MoveAlgorithm moveAlgorithm = null;
    private long numberOfLadders = 0;
    private long numberOfSnakes = 0;
    private List<Player.PlayerType> playerTypesLineup;

    @Override
    public String toString() {
        return "GameCreator{" +
                "numberOfFacesOnDice=" + numberOfFacesOnDice +
                ", numberOfSquares=" + numberOfSquares +
                ", moveAlgorithm=" + moveAlgorithm +
                ", numberOfLadders=" + numberOfLadders +
                ", numberOfSnakes=" + numberOfSnakes +
                ", playerTypesLineup=" + playerTypesLineup +
                '}';
    }

    public long getNumberOfFacesOnDice() {
        return numberOfFacesOnDice;
    }

    public GameCreator setNumberOfFacesOnDice(long numberOfFacesOnDice) {
        this.numberOfFacesOnDice = numberOfFacesOnDice;
        return this;
    }

    public long getNumberOfSquares() {
        return numberOfSquares;
    }

    public GameCreator setNumberOfSquares(long numberOfSquares) {
        this.numberOfSquares = numberOfSquares;
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
