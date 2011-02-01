package com.sample.lifegame;

import com.sample.lifegame.Cell.Key;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Mathieu ANCELIN
 */
public class Game {

    private int cellsMaxSize = 100;
    private Map<Key, Cell> cells = new HashMap<Key, Cell>();
    private AtomicInteger rounds = new AtomicInteger();

    private Game() {
    }

    public static Game init(int maxCells, int sensibility, boolean highLife, boolean clown) {
        if (maxCells % 2 != 0) {
            throw new RuntimeException("You have to provider a pair number");
        }
        Game game = new Game();
        game.rounds.set(0);
        game.cellsMaxSize = maxCells;
        for (int i = 0; i < maxCells; i++) {
            for (int j = 0; j < maxCells; j++) {
                if (!clown) {
                    int value = (int) ((Math.random() * 10) + 1);
                    if (value <= sensibility) {
                        game.cells.put(new Key(i, j), new Cell(false, i, j, highLife));
                    } else {
                        game.cells.put(new Key(i, j), new Cell(true, i, j, highLife));
                    }
                } else {
                    if (i == 49 && j == 49) {
                        game.cells.put(new Key(i, j), new Cell(true, i, j, highLife));
                    } else if (i == 50 && j == 49) {
                        game.cells.put(new Key(i, j), new Cell(true, i, j, highLife));
                    } else if (i == 51 && j == 49) {
                        game.cells.put(new Key(i, j), new Cell(true, i, j, highLife));
                    } else if (i == 51 && j == 50) {
                        game.cells.put(new Key(i, j), new Cell(true, i, j, highLife));
                    } else if (i == 51 && j == 51) {
                        game.cells.put(new Key(i, j), new Cell(true, i, j, highLife));
                    } else if (i == 50 && j == 51) {
                        game.cells.put(new Key(i, j), new Cell(true, i, j, highLife));
                    } else if (i == 49 && j == 51) {
                        game.cells.put(new Key(i, j), new Cell(true, i, j, highLife));
                    } else {
                        game.cells.put(new Key(i, j), new Cell(false, i, j, highLife));
                    }
                }
            }
        }
        Collection<Cell> cellsValue = game.cells.values();
        for (Cell cell : cellsValue) {
            cell.initCell(game.cells);
        }
        return game;
    }

    public void round() {
        rounds.incrementAndGet();
        naturalSelection();
        godWill();
    }

    private void naturalSelection() {
        Collection<Cell> cellsValue = cells.values();
        for (Cell cell : cellsValue) {
            cell.naturalSelection();
        }
    }

    private void godWill() {
        Collection<Cell> cellsValue = cells.values();
        for (Cell cell : cellsValue) {
            cell.applyGodWill();
        }
    }

    public Map<Key, Cell> getCells() {
        return cells;
    }

    public int getRounds() {
        return rounds.get();
    }

    public int getCellsMaxSize() {
        return cellsMaxSize;
    }

    private class RoundThread extends Thread {

        private int iStart, iStop, jStart, jStop;

        public RoundThread(int iStart, int iStop, int jStart, int jStop) {
            this.iStart = iStart;
            this.iStop = iStop;
            this.jStart = jStart;
            this.jStop = jStop;
        }

        @Override
        public void run() {
            naturalSelection();
        }
    }
}
