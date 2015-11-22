package com.snakesonaplane.jeu;

import com.google.common.math.LongMath;

import java.math.RoundingMode;
import java.util.List;

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

        // Todo: create board elements

    }
}
