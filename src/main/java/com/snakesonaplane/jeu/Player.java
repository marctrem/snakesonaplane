package com.snakesonaplane.jeu;

public class Player implements Cloneable {
    private long position = -1;
    private boolean isAi;
    private String name;

    public Player(String name, boolean isAi) {
        this.name = new String(name);
        this.isAi = isAi;
    }

    public Object clone() {
        Player p = new Player(this.name, this.isAi);
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

    public boolean isAi() {
        return this.isAi;
    }
}
