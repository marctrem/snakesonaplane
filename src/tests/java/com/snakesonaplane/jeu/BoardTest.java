package com.snakesonaplane.jeu;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class BoardTest {
    @Test
    public void testBoardCreation() {

        // MyClass is tested
        Board board = new Board(100L, 4L, 4L);

        // assert statements
        assertEquals("numberOfCells must be 100", 100, board.numberOfCells);
        assertEquals("numberOfSnakes must be 4", 4, board.numberOfSnakes);
        assertEquals("numberOfLadders must be 4", 4, board.numberOfLadders);
        assertEquals("number Of Elements must be 8", 8, board.boardElements.size());
    }

    @Test(expected = AssertionError.class)
    public void testTooManyElements() {
        Board board = new Board(16, 4, 4);
    }

    @Test
    public void maxElements() {
        Board board = new Board(16, 4, 3);

        assertEquals("numberOfCells must be 16", 16, board.numberOfCells);
        assertEquals("numberOfSnakes must be 3", 3, board.numberOfSnakes);
        assertEquals("numberOfLadders must be 4", 4, board.numberOfLadders);
        assertEquals("number Of Elements must be 7", 7, board.boardElements.size());
    }
}
