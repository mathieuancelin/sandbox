package com.sample.lifegame;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Mathieu ANCELIN
 */
public class Cell {

    private boolean alive;
    private int j;
    private int i;
    private List<Cell> neighbours = new ArrayList<Cell>();
    private boolean nextState;
    private boolean highLife;

    public Cell(boolean alive, int i, int j, boolean highLife) {
        this.highLife = highLife;
        this.alive = alive;
        this.j = j;
        this.i = i;
    }

    public void initCell(Map<Key, Cell> cells) {
        addCell(cells, i - 1, j - 1);
        addCell(cells, i - 1, j);
        addCell(cells, i - 1, j + 1);
        addCell(cells, i, j - 1);
        addCell(cells, i, j + 1);
        addCell(cells, i + 1, j - 1);
        addCell(cells, i + 1, j);
        addCell(cells, i + 1, j + 1);
    }

    public void addNeighBour(Cell cell) {
        if (!neighbours.contains(cell)) {
            neighbours.add(cell);
        }
    }

    public void naturalSelection() {
        int count = countAlives();
        if (count >= 2 && count < 4) {
            if (count == 3) {
                nextState = true;
            } else {
                nextState = alive;
            }
        } else {
            if (highLife) {
                if (count == 6) {
                    nextState = true;
                } else {
                    nextState = false;
                }
            } else {
                nextState = false;
            }
        }
    }

    public void applyGodWill() {
        this.alive = nextState;
    }

    private int countAlives() {
        int alives = 0;
        for (Cell cell : neighbours) {
            if (cell.isAlive()) {
                alives++;
            }
        }
        return alives;
    }

    private void addCell(Map<Key, Cell> cells, int i, int j) {
        Cell cell = null;
        try {
            cell = cells.get(new Key(i, j));
        } catch (Exception e) {
        }
        if (cell != null) {
            addNeighBour(cell);
        }
    }

    public boolean isAlive() {
        return alive;
    }

    private void alive() {
        alive = true;
    }

    private void dead() {
        alive = false;
    }

    public int getJ() {
        return j;
    }

    public int getI() {
        return i;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell) {
            Cell cell = (Cell) obj;
            if (i == cell.i) {
                if (j == cell.j) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.j;
        hash = 97 * hash + this.i;
        return hash;
    }

    @Override
    public String toString() {
        return "cell : " + System.identityHashCode(this) + " [" + j + ", " + i + "] => " + isAlive();
    }

    public static class Key {

        private final int i;
        private final int j;

        public Key(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key) {
                Key key = (Key) obj;
                if (i == key.i) {
                    if (j == key.j) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 61 * hash + this.i;
            hash = 61 * hash + this.j;
            return hash;
        }
    }
}
