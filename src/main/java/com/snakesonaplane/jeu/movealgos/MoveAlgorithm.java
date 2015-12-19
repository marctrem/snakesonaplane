package com.snakesonaplane.jeu.movealgos;


public interface MoveAlgorithm {
    long getMove(long currentCell, long boardSize, long diceRoll);
}
