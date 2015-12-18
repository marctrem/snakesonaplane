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

        p1 = new Player("Pierre", true);
        p2 = new Player("Paul", true);
        p3 = new Player("Luc", true);

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
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 1));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 0));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 1));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 0));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 1));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 2));
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
        long expected[] = {2, 0, 1, 2, 0, 1, 2, 4, 5, 6};

        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 4));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 5));
        this.game.gameMementoManager.addMemento(this.game.createMemento(players, 6));

        for (int i = 0; i < this.game.gameMementoManager.mementos.size(); i++) {
            assertEquals("The list should have been shifted", expected[i],
                    this.game.gameMementoManager.mementos.get(i).getState().currentPlayer);
        }
    }

}
