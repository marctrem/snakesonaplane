package com.snakesonaplane.jeu;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.snakesonaplane.exceptions.UnableToCreateGameException;
import com.snakesonaplane.exceptions.UnknownAlgorithmException;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithmFactoryMethod;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameCreator {

    private int numberOfFacesOnDice = 0;
    private int numberOfCells = 0;
    private MoveAlgorithm moveAlgorithm = null;
    private long numberOfLadders = 0;
    private long numberOfSnakes = 0;
    private List<Player> playerTypesLineup;


    public Game create() throws UnableToCreateGameException {

        if (!isBuilderValid()) {
            throw new UnableToCreateGameException();
        }

        Board board = new Board(this.numberOfCells, this.numberOfLadders, this.numberOfSnakes);
        List<Player> players = new ArrayList<>(playerTypesLineup.size());
        Dice dice = new Dice(this.numberOfFacesOnDice);

        return new Game(board, players, this.moveAlgorithm, dice);
    }

    public boolean isBuilderValid() throws UnableToCreateGameException {
        return !(this.numberOfFacesOnDice == 0
                || this.numberOfCells == 0
                || this.moveAlgorithm == null
                || this.numberOfLadders == 0
                || this.numberOfSnakes == 0
                || this.playerTypesLineup.size() == 0
                || !(this.numberOfCells >= 40 && this.numberOfCells <= 200)

                || !(this.numberOfFacesOnDice == 6
                || this.numberOfFacesOnDice == 8
                || this.numberOfFacesOnDice == 20)
                || this.playerTypesLineup.size() < 2);
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

    public GameCreator setNumberOfCells(int numberOfCells) {
        this.numberOfCells = numberOfCells;
        return this;
    }

    public MoveAlgorithm getMoveAlgorithm() {
        return moveAlgorithm;
    }

    public GameCreator setMoveAlgorithm(String moveAlgo) throws UnknownAlgorithmException {
        if (moveAlgo != null) {
            MoveAlgorithmFactoryMethod factoryMethod = new MoveAlgorithmFactoryMethod();
            MoveAlgorithm algo = factoryMethod.getMoveAlgorithm(factoryMethod.getByName(moveAlgo));
            this.moveAlgorithm = algo;
        }
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

    public List<Player> getPlayerTypesLineup() {
        return playerTypesLineup;
    }

    public GameCreator setPlayerTypesLineup(List<Player> playerTypesLineup) {
        this.playerTypesLineup = playerTypesLineup;
        return this;
    }

    public GameCreator loadConfig(String filename) throws FileNotFoundException, YamlException {
        YamlReader reader = new YamlReader(new FileReader(filename));
        Map data = (Map) reader.read();
        this.setNumberOfCells(Integer.parseInt((String) data.get("nbCells")));
        this.setNumberOfSnakes(Long.parseLong((String) data.get("nbSnakes")));
        this.setNumberOfLadders(Long.parseLong((String) data.get("nbLadders")));
        return this;
    }
}
