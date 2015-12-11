package com.snakesonaplane.jeu;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    List<Player> players;
    long currentPlayer;

    GameState(List<Player> players, long currentPlayer) {
        players = new ArrayList<>();
        for (Player p: players) {
            players.add((Player) p.clone());
        }

        this.currentPlayer = currentPlayer;
    }
}
