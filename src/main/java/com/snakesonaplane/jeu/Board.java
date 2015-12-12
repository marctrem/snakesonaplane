package com.snakesonaplane.jeu;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Board {


    int numberOfCells;
    long numberOfLadders;
    long numberOfSnakes;

    List<BoardElement> boardElements;


    public Board(int numberOfCells, long numberOfLadders, long numberOfSnakes) {

        this.numberOfCells = numberOfCells;
        this.numberOfLadders = numberOfLadders;
        this.numberOfSnakes = numberOfSnakes;
        this.boardElements = new ArrayList<>();

        while (!generateBoard());
    }

    // Be able to instantiate class in tests.
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

    private boolean generateBoard() {
        Random r = new Random();
        List<Integer> freeCells = new ArrayList<>();
        List<Integer> testSnake = new ArrayList<>((int) this.numberOfSnakes);

        for (int i = 0; i < this.numberOfCells; i++) {
            freeCells.add(i);
        }

        for (int i = 0; i < this.numberOfSnakes; i++) {
            boardElements.add(createSnake(r, freeCells));
            testSnake.add(boardElements.get(i).origin);
        }

        if (snakesAreProperlyArranged(testSnake)) {
            return false;
        }

        for (int i = 0; i < this.numberOfLadders; i++) {
            boardElements.add(createLadder(r, freeCells));
        }

        return true;
    }

    boolean snakesAreProperlyArranged(List<Integer> testSnake) {
        int contiguousCounter = 0;

        testSnake.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return i1 - i2;
            }
        });

        for (int i = 0; i < testSnake.size() - 1; i++) {
            if (testSnake.get(i + 1) - testSnake.get(i) == 1) {
                contiguousCounter++;
                if (contiguousCounter == 5) {
                    return false;
                }
            } else {
                contiguousCounter = 0;
            }
        }

        return true;
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


    public List<BoardElement> getBoardElements() {
        return boardElements;
    }

    public int getNumberOfCells() {
        return numberOfCells;
    }
}
