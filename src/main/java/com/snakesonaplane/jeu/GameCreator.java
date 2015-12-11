package com.snakesonaplane.jeu;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.snakesonaplane.exceptions.UnableToCreateGameException;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameCreator {

    private int numberOfFacesOnDice = 0;
    private long numberOfCells = 0;
    private MoveAlgorithm moveAlgorithm = null;
    private long numberOfLadders = 0;
    private long numberOfSnakes = 0;
    private List<Player> playerTypesLineup;


    public Game create() throws UnableToCreateGameException {

        // Todo: do some validity checks.
        if (this.numberOfFacesOnDice == 0
                || this.numberOfCells == 0
                || this.moveAlgorithm == null
                || this.numberOfLadders == 0
                || this.numberOfSnakes == 0
                || this.playerTypesLineup.size() == 0) {
           throw new UnableToCreateGameException();
        }

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
        if (numberOfFacesOnDice != 6
                || numberOfFacesOnDice != 8
                || numberOfFacesOnDice != 20) {
            this.numberOfFacesOnDice = numberOfFacesOnDice;
        }
        return this;
    }

    public long getNumberOfCells() {
        return numberOfCells;
    }

    public GameCreator setNumberOfCells(long numberOfCells) {
        if (numberOfCells >= 40 && numberOfCells <= 200) {
            this.numberOfCells = numberOfCells;
        }
        return this;
    }

    public MoveAlgorithm getMoveAlgorithm() {
        return moveAlgorithm;
    }

    public GameCreator setMoveAlgorithm(MoveAlgorithm moveAlgorithm) {
        if (moveAlgorithm != null) {
            this.moveAlgorithm = moveAlgorithm;
        }
        return this;
    }

    public long getNumberOfLadders() {
        return numberOfLadders;
    }

    public GameCreator setNumberOfLadders(long numberOfLadders) {
        if (numberOfLadders > 0) {
            this.numberOfLadders = numberOfLadders;
        }
        return this;
    }

    public long getNumberOfSnakes() {
        return numberOfSnakes;
    }

    public GameCreator setNumberOfSnakes(long numberOfSnakes) {
        if (numberOfSnakes > 0) {
            this.numberOfSnakes = numberOfSnakes;
        }
        return this;
    }

    public List<Player> getPlayerTypesLineup() {
        return playerTypesLineup;
    }

    public GameCreator setPlayerTypesLineup(List<Player> playerTypesLineup) {
        if (playerTypesLineup.size() > 0) {
            this.playerTypesLineup = playerTypesLineup;
        }
        return this;
    }

    public void loadConfig(String filename) throws FileNotFoundException, YamlException {
        YamlReader reader = new YamlReader(new FileReader(filename));
        Map data = (Map) reader.read();
        this.setNumberOfCells((long) data.get("nbCells"));
        this.setNumberOfSnakes((long) data.get("nbSnakes"));
        this.setNumberOfLadders((long) data.get("nbLadders"));
    }
}
