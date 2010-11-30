package com.sample.osgi.cdi.activator;

import com.sample.osgi.cdi.starter.Starter;
import com.sample.osgi.cdi.gui.SpellCheckerGui;
import org.jboss.weld.environment.osgi.WeldContainerOwner;
import org.jboss.weld.environment.osgi.WeldContainer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class GuiActivator implements BundleActivator {

    private WeldContainer weld;

    @Override
    public void start(BundleContext context) throws Exception {
        weld = WeldContainerOwner.container();
        weld.instance().select(Starter.class).get();
        weld.instance().select(SpellCheckerGui.class).get();
    }

    @Override
    public void stop(BundleContext context) throws Exception {}
}
