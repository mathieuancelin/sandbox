package com.sample.lifegame;

import java.util.ArrayList;
import java.util.List;
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
    private boolean highLife;
    private static String line;

    private Game() {
    }

    public static Game init(int maxCells, int sensibility, boolean highLife) {
        if (maxCells % 2 != 0) {
            throw new RuntimeException("You have to provider a pair number");
        }
        Game game = new Game();
        game.rounds.set(0);
        game.highLife = highLife;
        CELLS_MAX = maxCells;
        CELLS_MAX_MIN = maxCells - 1;
        for (int i = 0; i < CELLS_MAX; i++) {
            for (int j = 0; j < CELLS_MAX; j++) {
                int value = (int) ((Math.random() * 10) + 1);
                if (value <= sensibility) {
                    game.cells[i][j] = new Cell(false);
                } else {
                    game.cells[i][j] = new Cell(true);
                }
                /// clown / U
//                if (i == 49 && j == 49) {
//                    game.cells[i][j] = new Cell(true, j, i);
//                } else
//                if (i == 50 && j == 49) {
//                    game.cells[i][j] = new Cell(true, j, i);
//                } else
//                if (i == 51 && j == 49) {
//                    game.cells[i][j] = new Cell(true, j, i);
//                } else
//                if (i == 51 && j == 50) {
//                    game.cells[i][j] = new Cell(true);
//                } else
//                if (i == 51 && j == 51) {
//                    game.cells[i][j] = new Cell(true);
//                } else
//                if (i == 50 && j == 51) {
//                    game.cells[i][j] = new Cell(true);
//                } else
//                if (i == 49 && j == 51) {
//                    game.cells[i][j] = new Cell(true);
//                } else
//                game.cells[i][j] = new Cell(false, j, i);

                //// clown / U
            }
        }
        return game;
    }

    private class RoundThread extends Thread {

        private int iStart;
        private int iStop;
        private int jStart;
        private int jStop;

        public RoundThread(int iStart, int iStop, int jStart, int jStop) {
            this.iStart = iStart;
            this.iStop = iStop;
            this.jStart = jStart;
            this.jStop = jStop;
        }

        @Override
        public void run() {
            List<Cell> alives = new ArrayList<Cell>();
            List<Cell> deads = new ArrayList<Cell>();
            round(iStart, iStop, jStart, jStop, alives, deads);
            ripper(alives, deads);
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
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        //round(0, CELLS_MAX, 0, CELLS_MAX);
    }

    private void round(int iStart, int iStop, int jStart, int jStop, List<Cell> alives, List<Cell> deads) {
        Cell cell1 = null;
        Cell cell2 = null;
        Cell cell3 = null;
        Cell cell4 = null;
        Cell cell5 = null;
        Cell cell6 = null;
        Cell cell7 = null;
        Cell cell8 = null;
        for (int i = iStart; i < iStop; i++) {
            for (int j = jStart; j < jStop; j++) {
                Cell actual = cells[i][j];
                if (i == 0 && j == 0) {
                    cell1 = new Cell(false);
                    cell2 = new Cell(false);
                    cell3 = new Cell(false);
                    cell4 = new Cell(false);
                    cell5 = cells[i][j + 1];
                    cell6 = new Cell(false);
                    cell7 = cells[i + 1][j];
                    cell8 = cells[i + 1][j + 1];
                } else if (i == 0 && j == (CELLS_MAX_MIN)) {
                    cell1 = new Cell(false);
                    cell2 = new Cell(false);
                    cell3 = new Cell(false);
                    cell4 = cells[i][j - 1];
                    cell5 = new Cell(false);
                    cell6 = cells[i + 1][j - 1];
                    cell7 = cells[i + 1][j];
                    cell8 = new Cell(false);
                } else if (i == 0 && j > 0 && j < (CELLS_MAX_MIN)) {
                    cell1 = new Cell(false);
                    cell2 = new Cell(false);
                    cell3 = new Cell(false);
                    cell4 = cells[i][j - 1];
                    cell5 = cells[i][j + 1];
                    cell6 = cells[i + 1][j - 1];
                    cell7 = cells[i + 1][j];
                    cell8 = cells[i + 1][j + 1];
                } else if (i > 0 && i < (CELLS_MAX_MIN) && j == 0) {
                    cell1 = new Cell(false);
                    cell2 = cells[i - 1][j];
                    cell3 = cells[i - 1][j + 1];
                    cell4 = new Cell(false);
                    cell5 = cells[i][j + 1];
                    cell6 = new Cell(false);
                    cell7 = cells[i + 1][j];
                    cell8 = cells[i + 1][j + 1];
                } else if (i == (CELLS_MAX_MIN) && j == 0) {
                    cell1 = new Cell(false);
                    cell2 = cells[i - 1][j];
                    cell3 = cells[i - 1][j + 1];
                    cell4 = new Cell(false);
                    cell5 = cells[i][j + 1];
                    cell6 = new Cell(false);
                    cell7 = new Cell(false);
                    cell8 = new Cell(false);
                } else if (i == (CELLS_MAX_MIN) && j == (CELLS_MAX_MIN)) {
                    cell1 = cells[i - 1][j - 1];
                    cell2 = cells[i - 1][j];
                    cell3 = new Cell(false);
                    cell4 = cells[i][j - 1];
                    cell5 = new Cell(false);
                    cell6 = new Cell(false);
                    cell7 = new Cell(false);
                    cell8 = new Cell(false);
                } else if (i == (CELLS_MAX_MIN) && j > 0 && j < (CELLS_MAX_MIN)) {
                    cell1 = cells[i - 1][j - 1];
                    cell2 = cells[i - 1][j];
                    cell3 = cells[i - 1][j + 1];
                    cell4 = cells[i][j - 1];
                    cell5 = cells[i][j + 1];
                    cell6 = new Cell(false);
                    cell7 = new Cell(false);
                    cell8 = new Cell(false);
                } else if (j == (CELLS_MAX_MIN) && i > 0 && i < (CELLS_MAX_MIN)) {
                    cell1 = cells[i - 1][j - 1];
                    cell2 = cells[i - 1][j];
                    cell3 = new Cell(false);
                    cell4 = cells[i][j - 1];
                    cell5 = new Cell(false);
                    cell6 = cells[i + 1][j - 1];
                    cell7 = cells[i + 1][j];
                    cell8 = new Cell(false);
                } else {
                    cell1 = cells[i - 1][j - 1];
                    cell2 = cells[i - 1][j];
                    cell3 = cells[i - 1][j + 1];
                    cell4 = cells[i][j - 1];
                    cell5 = cells[i][j + 1];
                    cell6 = cells[i + 1][j - 1];
                    cell7 = cells[i + 1][j];
                    cell8 = cells[i + 1][j + 1];
                }
                int count = countAlive(cell1, cell2, cell3, cell4, cell5, cell6, cell7, cell8);
                if (count >= 2 && count < 4) {
                    if (count == 3) {
                        alives.add(actual);
                        //actual.alive();
                    }
                } else {
                    if (highLife) {
                        if (count == 6) {
                            alives.add(actual);
                        } else {
                            deads.add(actual);
                        }
                    } else {
                        deads.add(actual);
                        //actual.dead();
                    }
                }
            }
        }
    }

    private void ripper(List<Cell> alives, List<Cell> deads) {
        for (Cell cell : alives) {
            cell.alive();
        }
        alives.clear();
        for (Cell cell : deads) {
            cell.dead();
        }
        deads.clear();
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

    private static int countAlive(Cell... neighbourCells) {
        int alives = 0;
        for (Cell cell : neighbourCells) {
            if (cell.isAlive()) {
                alives++;
            }
        }
        return alives;
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
