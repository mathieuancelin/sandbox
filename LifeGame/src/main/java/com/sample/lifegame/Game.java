package com.sample.lifegame;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author mathieu
 */
public class Game {

    public static int CELLS_MAX = 100;
    public static int CELLS_MAX_MIN = 99;
    private Cell[][] cells = new Cell[CELLS_MAX][CELLS_MAX];
    private AtomicInteger rounds = new AtomicInteger();
    private static String line;

    private Game() {
    }

    public static Game init(int maxCells, int sensibility, boolean highLife, boolean clown) {
        if (maxCells % 2 != 0) {
            throw new RuntimeException("You have to provider a pair number");
        }
        Game game = new Game();
        game.rounds.set(0);
        CELLS_MAX = maxCells;
        CELLS_MAX_MIN = maxCells - 1;
        for (int i = 0; i < CELLS_MAX; i++) {
            for (int j = 0; j < CELLS_MAX; j++) {
                if (!clown) {
                    int value = (int) ((Math.random() * 10) + 1);
                    if (value <= sensibility) {
                        game.cells[i][j] = new Cell(false, i, j, highLife);
                    } else {
                        game.cells[i][j] = new Cell(true, i, j, highLife);
                    }
                } else {
                    if (i == 49 && j == 49) {
                        game.cells[i][j] = new Cell(true, i, j, highLife);
                    } else if (i == 50 && j == 49) {
                        game.cells[i][j] = new Cell(true, i, j, highLife);
                    } else if (i == 51 && j == 49) {
                        game.cells[i][j] = new Cell(true, i, j, highLife);
                    } else if (i == 51 && j == 50) {
                        game.cells[i][j] = new Cell(true, i, j, highLife);
                    } else if (i == 51 && j == 51) {
                        game.cells[i][j] = new Cell(true, i, j, highLife);
                    } else if (i == 50 && j == 51) {
                        game.cells[i][j] = new Cell(true, i, j, highLife);
                    } else if (i == 49 && j == 51) {
                        game.cells[i][j] = new Cell(true, i, j, highLife);
                    } else {
                        game.cells[i][j] = new Cell(false, i, j, highLife);
                    }
                }
            }
        }
        for (int i = 0; i < CELLS_MAX; i++) {
            for (int j = 0; j < CELLS_MAX; j++) {
                game.cells[i][j].initCell(game.cells);
            }
        }
        return game;
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
            naturalSelection(iStart, iStop, jStart, jStop);
        }
    }

    public void round() {
        rounds.incrementAndGet();
        Thread thread1 = new RoundThread(0, CELLS_MAX / 2, 0, CELLS_MAX / 2);
        Thread thread2 = new RoundThread(CELLS_MAX / 2, CELLS_MAX, 0, CELLS_MAX / 2);
        Thread thread3 = new RoundThread(0, CELLS_MAX / 2, CELLS_MAX / 2, CELLS_MAX);
        Thread thread4 = new RoundThread(CELLS_MAX / 2, CELLS_MAX, CELLS_MAX / 2, CELLS_MAX);
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException ex) {}
//        naturalSelection(0, CELLS_MAX, 0, CELLS_MAX);
        godWill(0, CELLS_MAX, 0, CELLS_MAX);
    }

    private void naturalSelection(int iStart, int iStop, int jStart, int jStop) {
        for (int i = iStart; i < iStop; i++) {
            for (int j = jStart; j < jStop; j++) {
                cells[i][j].naturalSelection();
            }
        }
    }

    private void godWill(int iStart, int iStop, int jStart, int jStop) {
        for (int i = iStart; i < iStop; i++) {
            for (int j = jStart; j < jStop; j++) {
                cells[i][j].applyGodWill();
            }
        }
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRounds() {
        return rounds.get();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < CELLS_MAX; i++) {
            builder.append(getLine());
            builder.append("\n");
            for (int j = 0; j < CELLS_MAX; j++) {
                builder.append("|");
                if (cells[i][j].isAlive()) {
                    builder.append("x");
                } else {
                    builder.append(" ");
                }
            }
            builder.append("|\n");
        }
        builder.append(getLine());
        return builder.toString();
    }

    private static String getLine() {
        if (line == null) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < ((2 * CELLS_MAX) + 1); i++) {
                builder.append("-");
            }
            line = builder.toString();
        }
        return line;
    }
}
