package com.sample.lifegame;

/**
 *
 * @author mathieu
 */
public class Cell {

    private boolean alive;

    private int x;
    private int y;

    public Cell(boolean alive) {
        this.alive = alive;
        this.x = -1;
        this.y = -1;
    }

    public Cell(boolean alive, int x, int y) {
        this.alive = alive;
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void alive() {
        alive = true;
    }

    public void dead() {
        alive = false;
    }

    @Override
    public String toString() {
        return "cell : " + System.identityHashCode(this) + " [" + x + ", " + y + "] => " + isAlive();
    }

}
