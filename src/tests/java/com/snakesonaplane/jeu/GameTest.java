package com.snakesonaplane.jeu;

import com.snakesonaplane.exceptions.GameStateOutOfBoundException;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;
import com.snakesonaplane.jeu.movealgos.MoveAlgorithm1;
import com.snakesonaplane.wrappers.UIAbstractFactoryFactoryMethod;
import com.snakesonaplane.wrappers.UIAbstractFactorySingleton;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class GameTest {

    Game game;
    List<Player> players;
    Board board;

    @Before
    public void setUp() {
        try {
            UIAbstractFactorySingleton.initialize(UIAbstractFactoryFactoryMethod.UIType.JAVAFX);
        } catch(RuntimeException e) {}

        Player p1, p2, p3;
        this.players = new ArrayList<>();
        MoveAlgorithm algo = new MoveAlgorithm1();
        Dice dice = new Dice(6);

        p1 = new Player("Pierre", true);
        p2 = new Player("Paul", true);
        p3 = new Player("Luc", true);

        players.add(p1);
        players.add(p2);
        players.add(p3);

        this.board = new Board(36, 4, 4);
        this.game = new Game(board, players, algo, dice);
        this.game.gameMementos.add(this.game.createMemento(players, 1));
        this.game.gameMementos.add(this.game.createMemento(players, 2));
        this.game.gameMementos.add(this.game.createMemento(players, 0));
        this.game.gameMementos.add(this.game.createMemento(players, 1));
        this.game.gameMementos.add(this.game.createMemento(players, 2));
        this.game.gameMementos.add(this.game.createMemento(players, 0));
        this.game.gameMementos.add(this.game.createMemento(players, 1));
        this.game.gameMementos.add(this.game.createMemento(players, 2));
        this.game.currentGameStateIndex = 8;
    }

    @Test
    public void testEmptyListLowerBound() throws GameStateOutOfBoundException {
        assertNotNull(this.game.getPreviousState());
    }

    @Test(expected = GameStateOutOfBoundException.class)
    public void testEmptyListLowerOutOfBound() throws GameStateOutOfBoundException {
        for (int i = 0; i < 10; i++) {
            this.game.getPreviousState();
        }
    }

    @Test
    public void testPreviousThenNextState() throws GameStateOutOfBoundException {
        this.game.getPreviousState();
        assertNotNull(this.game.getNextState());
    }

    @Test(expected = GameStateOutOfBoundException.class)
    public void testPreviousThenTwoNext() throws GameStateOutOfBoundException {
        this.game.getPreviousState();
        this.game.getNextState();
        this.game.getNextState();
    }

    @Test
    public void testMax10States() throws GameStateOutOfBoundException {
        this.game.addCurrentState();
        this.game.addCurrentState();
        this.game.addCurrentState();
        this.game.addCurrentState();
        this.game.addCurrentState();
        assertEquals("has 10 states", 10, this.game.gameMementos.size());
    }

    @Test
    public void testGameStateShifting() {
        long expected[] = {2, 0, 1, 2, 0, 1, 2, 0, 0, 0};

        this.game.addCurrentState();
        this.game.addCurrentState();
        this.game.addCurrentState();

        for (int i = 0; i < this.game.gameMementos.size(); i++) {
            assertEquals("The list should have been shifted", expected[i], this.game.gameMementos.get(i).getState().currentPlayer);
        }
    }

}
