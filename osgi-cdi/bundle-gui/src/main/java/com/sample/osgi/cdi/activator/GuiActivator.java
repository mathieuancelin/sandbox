package com.sample.osgi.cdi.activator;

import com.sample.osgi.cdi.starter.Starter;
import com.sample.osgi.cdi.gui.SpellCheckerGui;
import com.sample.osgi.cdi.services.SpellCheckerService;
import org.jboss.weld.environment.osgi.WeldContainerOwner;
import org.jboss.weld.environment.se.WeldContainer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class GuiActivator implements BundleActivator {

    private SpellCheckerGui gui;
    private WeldContainer weld;
    private Starter starter;

    @Override
    public void start(BundleContext context) throws Exception {
        weld = WeldContainerOwner.container();
        starter = weld.instance().select(Starter.class).get();
        gui = weld.instance().select(SpellCheckerGui.class).get();
        gui.setSpellService((SpellCheckerService) context.getService(context.getServiceReference(SpellCheckerService.class.getName())));
        gui.start();
    }

    @Override
    public void stop(BundleContext context) throws Exception {}
}
