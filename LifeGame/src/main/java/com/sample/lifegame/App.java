package com.sample.lifegame;

import com.sample.lifegame.ui.AppGUI;
import javax.swing.UIManager;

/**
 *
 * @author Mathieu ANCELIN
 */
public class App {

    public static void main(String[] args) {
        if ("linux".equals(getOsName())) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            } catch (Exception e) {
            }
        }
        AppGUI gui = new AppGUI();
        gui.start();
    }

    public static String getOsName() {
        String os = "";
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") > -1) {
            os = "windows";
        } else if (System.getProperty("os.name").toLowerCase().indexOf("linux") > -1) {
            os = "linux";
        } else if (System.getProperty("os.name").toLowerCase().indexOf("mac") > -1) {
            os = "mac";
        }
        return os;
    }
}
