package com.snakesonaplane.jeu.movealgos;

public class MoveAlgorithm3 implements MoveAlgorithm {
    @Override
    public long getMove(long currentCell, long boardSize, long diceRoll) {
        long dest = currentCell + diceRoll;
        return dest >= boardSize ? boardSize - (dest - boardSize) : dest;
    }
}
