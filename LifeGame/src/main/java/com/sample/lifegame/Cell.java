package com.sample.lifegame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathieu
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

    public void initCell(Cell[][] cells) {
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

    private  void addCell(Cell[][] cells, int i, int j) {
        Cell cell = null;
        try {
            cell = cells[i][j];
        } catch (Exception e) {}
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

}
