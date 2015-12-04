package com.snakesonaplane.jeu;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.TestCase.*;

public class BoardTest {

    Board board;

    @Before
    public void setUp() {
        this.board = new Board();
        BoardElement snake1, snake2, ladder1, ladder2;

        this.board.numberOfLadders = 2;
        this.board.numberOfSnakes = 2;
        this.board.numberOfCells = 32;

        snake1 = new BoardElement();
        snake1.origin = 30;
        snake1.destination = 25;

        snake2 = new BoardElement();
        snake2.origin = 12;
        snake2.destination = 5;

        ladder1 = new BoardElement();
        ladder1.origin = 20;
        ladder1.destination = 32;

        ladder2 = new BoardElement();
        ladder2.origin = 14;
        ladder2.destination = 31;

        this.board.boardElements = new ArrayList<>();
        this.board.boardElements.add(snake1);
        this.board.boardElements.add(snake2);
        this.board.boardElements.add(ladder1);
        this.board.boardElements.add(ladder2);
    }

    @Test
    public void testWithoutBoardElement() {
        long move = this.board.getMove(13);
        assertEquals("move must be 13", 13, move);
    }

    @Test
    public void testWithBoardElement() {
        long move = this.board.getMove(14);
        assertEquals("move must be 31", 31, move);

        move = this.board.getMove(30);
        assertEquals("move must be 25", 25, move);

        move = this.board.getMove(12);
        assertEquals("move must be 5", 5, move);

        move = this.board.getMove(20);
        assertEquals("move must be 32", 32, move);
    }

    @Test
    public void testBoardCreation() {

        Board board = new Board(100L, 4L, 4L);

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
