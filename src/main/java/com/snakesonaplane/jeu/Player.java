package com.snakesonaplane.jeu;

import com.snakesonaplane.wrappers.UIAbstractFactorySingleton;
import com.snakesonaplane.wrappers.ui.Color;

import java.util.Random;

public class Player implements Cloneable {
    private long position = -1;
    private boolean isAi;
    private String name;

    private Color color;

    public Player(String name, boolean isAi) {
        this.name = name;
        this.isAi = isAi;

        Random random = new Random();
        this.color = UIAbstractFactorySingleton.get().createColor(random.nextDouble(), random.nextDouble(), random.nextDouble(), 1.0d);
    }

    public Player(String name, boolean isAi, Color color) {
        this(name, isAi);
        this.setColor(color);
    }

    public Object clone() {
        Player p = new Player(this.name, this.isAi, this.color);
        p.position = this.position;
        return p;
    }

    public long getPosition() {
        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isAi() {
        return this.isAi;
    }

    public void setAi(boolean ai) {
        isAi = ai;
    }
}
