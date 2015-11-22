package com.snakesonaplane.jeu;


import java.util.Random;

public class Dice {

    private int numberOfFaces;
    private Random random;


    public Dice(int numberOfFaces) {
        this.numberOfFaces = numberOfFaces;
        random = new Random();
    }

    public long roll() {
        return random.nextInt(numberOfFaces) + 1;
    }
}
