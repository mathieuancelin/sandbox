package com.sample.lifegame;

import com.sample.lifegame.ui.AppGUI;
import javax.swing.UIManager;

public class App {

    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        new AppGUI();
    }

    private static void play(Game game) {
        game.round();
        System.out.println(game);
    }
}
