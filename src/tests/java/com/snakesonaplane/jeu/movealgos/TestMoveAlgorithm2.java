package com.snakesonaplane.jeu.movealgos;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestMoveAlgorithm2 {

    @Test
    public void baseTest() {
        MoveAlgorithm algo = new MoveAlgorithm2();
        long move = algo.getMove(15, 20, 3);

        assertEquals("move must be 18", 18, move);
    }

    @Test
    public void testExactVictory() {
        MoveAlgorithm algo = new MoveAlgorithm2();
        long move = algo.getMove(15, 20, 5);

        assertEquals("move must be 20", 20, move);
    }

    @Test
    public void testVictoryOverflow() {
        MoveAlgorithm algo = new MoveAlgorithm2();
        long move = algo.getMove(15, 20, 6);

        assertEquals("move must be 15", 15, move);
    }
}
