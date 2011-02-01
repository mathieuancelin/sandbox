package com.sample.lifegame.ui;

import com.sample.lifegame.Cell;
import com.sample.lifegame.Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
/**
 *
 * @author mathieu
 */
public class GameComponent extends JComponent {
    
    private Game game;

    private double cellX;
    private double cellY;

    public void setGame(Game game) {
        this.game = game;
    }

    public void setCellSize(Dimension cellSize) {
        this.cellX = cellSize.getWidth() / game.CELLS_MAX;
        this.cellY = cellSize.getHeight() / game.CELLS_MAX;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (game != null) {
            int x = 0;
            int y = 0;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Cell[][] cells = game.getCells();
            for (int i = 0; i < Game.CELLS_MAX; i++) {
                for (int j = 0; j < Game.CELLS_MAX; j++) {
                    if (cells[i][j].isAlive()) {
                        g2.setColor(Color.RED);
                    } else {
                        g2.setColor(Color.WHITE);
                    }
                    g2.fill(new Rectangle2D.Double(x, y, cellX, cellY));
//                    g2.fill(new Ellipse2D.Double(x, y, cellX, cellY));
                    g2.setColor(Color.BLACK);
                    g2.draw(new Rectangle2D.Double(x, y, cellX, cellY));
                    x += cellX;
                }
                x = 0;
                y += cellY;
            }
        }
    }
}
