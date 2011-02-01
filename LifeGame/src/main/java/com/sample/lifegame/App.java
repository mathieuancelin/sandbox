package com.sample.lifegame;

import com.sample.lifegame.ui.AppGUI;

public class App {

    public static void main(String[] args) {
        new AppGUI();
    }

    private static void play(Game game) {
        game.round();
        System.out.println(game);
    }
}
