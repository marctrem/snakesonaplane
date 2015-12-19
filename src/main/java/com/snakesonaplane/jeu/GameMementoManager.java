package com.snakesonaplane.jeu;

import com.snakesonaplane.exceptions.GameStateOutOfBoundException;
import com.snakesonaplane.jeu.Game.GameMemento;

import java.util.ArrayList;
import java.util.List;

public class GameMementoManager {

    final static int MAX_STATES = 10;
    List<GameMemento> mementos;
    int currentGameStateIndex = -1;

    public GameMementoManager() {
        this.mementos = new ArrayList<>(MAX_STATES);
    }

    public GameMemento getPreviousMemento() throws GameStateOutOfBoundException {
        if (this.currentGameStateIndex <= 0) {
            throw new GameStateOutOfBoundException();
        }
        return this.mementos.get(--this.currentGameStateIndex);
    }

    public GameMemento getNextMemento() throws GameStateOutOfBoundException {
        if (this.mementos.size() <= this.currentGameStateIndex + 1) {
            throw new GameStateOutOfBoundException();
        }

        return this.mementos.get(++this.currentGameStateIndex);
    }

    public void addMemento(GameMemento memento) {
        if (memento.getState().players.get((int) memento.getState().currentPlayer).isAi()) {
            return;
        }

        this.mementos.add(memento);

        this.currentGameStateIndex++;

        // Erase next states
        for (int i = this.currentGameStateIndex + 1; i < this.mementos.size(); i++) {
            this.mementos.remove(i);
        }

        if (this.mementos.size() > MAX_STATES) {
            List<GameMemento> new_list = new ArrayList<>(MAX_STATES);

            for (int i = 1; i < this.mementos.size(); i++) {
                new_list.add(this.mementos.get(i));
            }

            this.mementos = new_list;
            this.currentGameStateIndex = MAX_STATES - 1;
        }
    }

    public boolean hasPreviousMemento() {
        return this.currentGameStateIndex > 0;
    }

    public boolean hasNextMemento() {
        return this.currentGameStateIndex < (this.mementos.size() - 1);
    }

}
