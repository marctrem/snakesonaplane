package com.snakesonaplane.jeu;

import com.snakesonaplane.jeu.movealgos.MoveAlgorithm;

import java.util.List;

public class Game {

    GameState currentGameState;
    List<GameState> gameStates;

    Board board;
    List<Player> players;
    Dice dice;
    MoveAlgorithm moveAlgorithm;

    Game(Board board, List<Player> players, MoveAlgorithm moveAlgorithm, Dice dice) {

        this.board = board;
        this.players = players;
        this.moveAlgorithm = moveAlgorithm;
        this.dice = dice;

    }
}
