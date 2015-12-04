package com.snakesonaplane.jeu.movealgos;

/**
 * Created by marc on 11/22/15.
 */
public class MoveAlgorithm2 implements MoveAlgorithm {
    @Override
    public long getMove(long currentCell, long boardSize, long diceRoll) {
        long dest = currentCell + diceRoll;
        return dest > boardSize ? currentCell: dest;
    }
}
