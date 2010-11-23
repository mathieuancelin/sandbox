package com.serli.osgi.cdi.activator;

import com.serli.osgi.cdi.gui.SpellCheckerGui;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class CheckerActivator implements BundleActivator {

    private Weld weld;

    private WeldContainer container;

    private SpellCheckerGui gui;

    @Override
    public void start(BundleContext bc) throws Exception {
        weld = new Weld();
        container = weld.initialize();
        gui = container.instance().select(SpellCheckerGui.class).get();
        gui.start();
    }

    @Override
    public void stop(BundleContext bc) throws Exception {
        weld.shutdown();
    }
}
