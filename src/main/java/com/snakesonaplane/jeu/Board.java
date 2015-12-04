package com.snakesonaplane.jeu;

import com.google.common.math.LongMath;

import java.math.RoundingMode;
import java.util.*;

public class Board {


    long numberOfCells;
    long lengthOfARow;
    long numberOfLadders;
    long numberOfSnakes;

    List<BoardElement> boardElements;


    public Board(long numberOfCells, long numberOfLadders, long numberOfSnakes) {

        this.lengthOfARow = LongMath.sqrt(numberOfCells, RoundingMode.UNNECESSARY);
        if ((numberOfLadders + numberOfSnakes) * 2  >  numberOfCells - 1) throw new AssertionError();

        this.numberOfCells = numberOfCells;
        this.numberOfLadders = numberOfLadders;
        this.numberOfSnakes = numberOfSnakes;
        this.boardElements = new ArrayList<>();

        generateBoard();
    }

    Board() {
    }

    public long getMove(long currentCell) {

        for (BoardElement elem: this.boardElements) {
            if (elem.origin == currentCell) {
                return elem.destination;
            }
        }

        return currentCell;
    }

    private void generateBoard() {
        Random r = new Random();
        List<Integer> freeCells = new ArrayList<>();

        for (int i = 0; i < this.numberOfCells; i++) {
            freeCells.add(i);
        }

        for (int i = 0; i < this.numberOfSnakes; i++) {
            boardElements.add(createSnake(r, freeCells));
        }

        for (int i = 0; i < this.numberOfLadders; i++) {
            boardElements.add(createLadder(r, freeCells));
        }
    }

    private BoardElement createLadder(Random r, List<Integer> freeCells) {
        int iOrigin;
        int iDestination;
        BoardElement elem = new BoardElement();

        // - 1 makes sure there's at least one possible destination cell
        iOrigin = r.nextInt(freeCells.size() - 1);

        elem.origin = freeCells.get(iOrigin);
        freeCells.remove(iOrigin);

        iDestination = freeCells.size() - r.nextInt(freeCells.size() - iOrigin) - 1;
        elem.destination = freeCells.get(iDestination);
        freeCells.remove(iDestination);
        return elem;
    }

    private BoardElement createSnake(Random r, List<Integer> freeCells) {
        int iOrigin;
        int iDestination;
        BoardElement elem = new BoardElement();

        iOrigin = r.nextInt(freeCells.size());
        // Make sure the origin of the snake isn't the first cell
        iOrigin = iOrigin == 0 ? 1: iOrigin;
        elem.origin = freeCells.get(iOrigin);
        freeCells.remove(iOrigin);

        iDestination = r.nextInt(iOrigin);
        elem.destination = freeCells.get(iDestination);
        freeCells.remove(iDestination);

        return elem;
    }

}
