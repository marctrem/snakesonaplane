package com.snakesonaplane.jeu.movealgos;

import com.snakesonaplane.jeu.BoardCell;


public interface MoveAlgorithm {
    long getMove(long currentCell, long boardSize, long diceRoll);
}
