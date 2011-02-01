package com.sample.lifegame;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mathieu
 */
public class Game {

    public static int CELLS_MAX = 100;

    public static int CELLS_MAX_MIN = 99;

    private Cell[][] cells = new Cell[CELLS_MAX][CELLS_MAX];

    private int rounds;

    private boolean highLife;

    private static String line;

    private List<Cell> alives = new ArrayList<Cell>();
    
    private List<Cell> deads = new ArrayList<Cell>();

    private Game() {}

    public static Game init(int maxCells, boolean highLife) {
        Game game = new Game();
        game.highLife = highLife;
        CELLS_MAX = maxCells;
        CELLS_MAX_MIN = maxCells - 1;
        for (int i = 0; i < CELLS_MAX; i++) {
            for (int j = 0; j < CELLS_MAX; j++) {
                int value = (int)((Math.random() * 10) + 1);
                if (value <= 8) {
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

    public void round() {
        rounds++;
        Cell cell1 = null;
        Cell cell2 = null;
        Cell cell3 = null;
        Cell cell4 = null;
        Cell cell5 = null;
        Cell cell6 = null;
        Cell cell7 = null;
        Cell cell8 = null;
        for (int i = 0; i < CELLS_MAX; i++) {
            for (int j = 0; j < CELLS_MAX; j++) {
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
                } else if (i > 0  && i < (CELLS_MAX_MIN) && j == 0) {
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
                        if(count == 6) {
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

    public void ripper() {
        for (Cell cell : alives) {
            cell.alive();
        }
        for (Cell cell : deads) {
            cell.dead();
        }
        alives.clear();
        deads.clear();
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int getRounds() {
        return rounds;
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
