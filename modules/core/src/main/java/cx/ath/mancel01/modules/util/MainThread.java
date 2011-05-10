package cx.ath.mancel01.modules.util;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainThread extends Thread {

    private final Method main;

    private boolean ended = false;

    private int returnValue;

    public MainThread(Method main) {
        this.main = main;
    }

    @Override
    public void run() {
        int ret = 0;
        try {
            Object arg = new String[] {};
            Object returnedObject = main.invoke(null, arg);
            if (returnedObject != null) {
                ret = (Integer) returnedObject;
            }
        } catch (Exception ex) {
            Logger.getLogger(MainThread.class.getName()).log(Level.SEVERE, null, ex);
            ret = -1;
        }
        ended = true;
        returnValue = ret;
    }

    public boolean isEnded() {
        return ended;
    }

    public int getReturnValue() {
        return returnValue;
    }
}
