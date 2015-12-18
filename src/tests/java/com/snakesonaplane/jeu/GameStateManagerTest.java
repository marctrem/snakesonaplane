package com.snakesonaplane.jeu;

import com.snakesonaplane.exceptions.GameStateOutOfBoundException;
import com.snakesonaplane.exceptions.UnknownAlgorithmException;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithmFactoryMethod;
import com.snakesonaplane.wrappers.UIAbstractFactoryFactoryMethod;
import com.snakesonaplane.wrappers.UIAbstractFactorySingleton;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class GameStateManagerTest {
    Game game;
    List<Player> players;

    @Before
    public void setUp() {
        try {
            UIAbstractFactorySingleton.initialize(UIAbstractFactoryFactoryMethod.UIType.JAVAFX);
        } catch(RuntimeException e) {}

        Player p1, p2, p3;
        this.players = new ArrayList<>();

        p1 = new Player("Pierre", false);
        p2 = new Player("Paul", false);
        p3 = new Player("Luc", false);

        this.players.add(p1);
        this.players.add(p2);
        this.players.add(p3);

        Dice dice = new Dice(6);
        MoveAlgorithmFactoryMethod fact = new MoveAlgorithmFactoryMethod();
        MoveAlgorithm algo = null;

        try {
            algo = fact.getMoveAlgorithm(MoveAlgorithmFactoryMethod.Algo.MOVE_ALGO_1);
        } catch (UnknownAlgorithmException e) {}

        Board board = new Board(100, 2, 2);

        this.game = new Game(board, this.players, algo, dice);
        this.players.get(0).setPosition(5);

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 1));
        this.players.get(1).setPosition(3);

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
        this.players.get(2).setPosition(1);

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 0));
        this.players.get(0).setPosition(8);

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 1));
        this.players.get(1).setPosition(4);

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
        this.players.get(2).setPosition(9);

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 0));
        this.players.get(0).setPosition(10);

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 1));
        this.players.get(1).setPosition(12);

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
    }

    @Test
    public void testHasPreviousMemento() {
        for (int i = 0; i < 8; i++) {
            assertTrue("has previous memento", this.game.gameMementoManager.hasPreviousMemento());
            try {
                this.game.gameMementoManager.getPreviousMemento();
            } catch (GameStateOutOfBoundException e) {}
        }
        assertFalse("has no more previous memento", this.game.gameMementoManager.hasPreviousMemento());
    }

    @Test
    public void testHasNextMemento() {
        assertFalse("has no next memento", this.game.gameMementoManager.hasNextMemento());

        for (int i = 0; i < 3; i++) {
            try {
                this.game.gameMementoManager.getPreviousMemento();
            } catch (GameStateOutOfBoundException e) {}
        }

        for (int i = 0; i < 3; i++) {
            assertTrue("has next memento", this.game.gameMementoManager.hasNextMemento());
            try {
                this.game.gameMementoManager.getNextMemento();
            } catch (GameStateOutOfBoundException e) {}
        }
        assertFalse("has no next memento", this.game.gameMementoManager.hasNextMemento());

    }

    @Test
    public void testPreviousState() {
        Game.GameMemento mementos[] = {null, null, null};


        try {
            mementos[0] = this.game.gameMementoManager.getPreviousMemento();
            mementos[1] = this.game.gameMementoManager.getPreviousMemento();
            mementos[2] = this.game.gameMementoManager.getPreviousMemento();
        } catch (GameStateOutOfBoundException e) {
            e.printStackTrace();
        }


        int expected[][] = {{10, 4, 9}, {8, 4, 9}, {8, 4, 1}};

        for (int i = 0; i < mementos.length; i++) {
            for (int j = 0; j < expected.length; j++) {
                assertEquals("should load the last state", expected[i][j], mementos[i].getState().players.get(j).getPosition());
            }

        }
    }

    @Test
    public void testNextState() {
        Game.GameMemento mementos[] = {null, null, null};


        try {
            for (int i = 0; i < 3; i++) {
                this.game.gameMementoManager.getPreviousMemento();
            }
            for (int i = 0; i < 3; i++) {
                mementos[i] = this.game.gameMementoManager.getNextMemento();
            }
        } catch (GameStateOutOfBoundException e) {
            e.printStackTrace();
        }


        int expected[][] = {{8, 4, 9}, {10, 4, 9}, {10, 12, 9}};

        for (int i = 0; i < mementos.length; i++) {
            for (int j = 0; j < expected.length; j++) {
                assertEquals("should load the last state", expected[i][j], mementos[i].getState().players.get(j).getPosition());
            }

        }
    }

    @Test
    public void testEmptyListLowerBound() throws GameStateOutOfBoundException {
        assertNotNull(this.game.gameMementoManager.getPreviousMemento());
    }

    @Test(expected = GameStateOutOfBoundException.class)
    public void testEmptyListLowerOutOfBound() throws GameStateOutOfBoundException {
        for (int i = 0; i < 10; i++) {
            this.game.gameMementoManager.getPreviousMemento();
        }
    }

    @Test
    public void testPreviousThenNextState() throws GameStateOutOfBoundException {
        this.game.gameMementoManager.getPreviousMemento();
        assertNotNull(this.game.gameMementoManager.getNextMemento());
    }

    @Test(expected = GameStateOutOfBoundException.class)
    public void testPreviousThenTwoNext() throws GameStateOutOfBoundException {
        this.game.gameMementoManager.getPreviousMemento();
        this.game.gameMementoManager.getNextMemento();
        this.game.gameMementoManager.getNextMemento();
    }

    @Test
    public void testMax10States() throws GameStateOutOfBoundException {
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
        assertEquals("has 10 states", 10, this.game.gameMementoManager.mementos.size());
    }

    @Test
    public void testGameStateShifting() {
        long expected[] = {2, 0, 1, 2, 0, 1, 2, 0, 1, 2};

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 0));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 1));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));

        for (int i = 0; i < this.game.gameMementoManager.mementos.size(); i++) {
            assertEquals("The list should have been shifted", expected[i],
                    this.game.gameMementoManager.mementos.get(i).getState().currentPlayer);
        }
    }

}
