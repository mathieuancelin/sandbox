package com.sample.lifegame.ui;

import com.sample.lifegame.Cell;
import com.sample.lifegame.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.util.Collection;
import javax.swing.JComponent;

/**
 *
 * @author Mathieu ANCELIN
 */
public class GameComponent extends JComponent {
    
    private Game game;

    private double cellX;
    private double cellY;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setCellSize(Dimension cellSize) {
        if (game != null) {
            this.cellX = cellSize.getWidth() / game.getCellsMaxSize();
            this.cellY = cellSize.getHeight() / game.getCellsMaxSize();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Collection<Cell> cells = game.getCells().values();
            for (Cell cell : cells) {
                if (cell.isAlive()) {
                    g2.setColor(Color.RED);
                } else {
                    g2.setColor(Color.WHITE);
                }
                g2.fill(new Rectangle2D.Double(cellX * cell.getJ(), cellY * cell.getI(), cellX, cellY));
                g2.setColor(Color.BLACK);
                g2.draw(new Rectangle2D.Double(cellX * cell.getJ(), cellY * cell.getI(), cellX, cellY));
            }
        }
    }
}
