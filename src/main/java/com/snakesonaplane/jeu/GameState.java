package com.snakesonaplane.jeu;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    List<Player> players;
    long currentPlayer;

    GameState(List<Player> players, long currentPlayer) {
        this.players = new ArrayList<>();
        for (Player p: players) {
            this.players.add((Player) p.clone());
        }

        this.currentPlayer = currentPlayer;
    }
}
