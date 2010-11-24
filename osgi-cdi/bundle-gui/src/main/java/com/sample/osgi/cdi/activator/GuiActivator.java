package com.sample.osgi.cdi.activator;

import com.sample.osgi.cdi.gui.SpellCheckerGui;
import com.sample.osgi.cdi.services.SpellCheckerService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class GuiActivator implements BundleActivator {

    private SpellCheckerGui gui;

    @Override
    public void start(BundleContext context) throws Exception {
        gui = new SpellCheckerGui();
        gui.setSpellService((SpellCheckerService) context.getService(context.getServiceReference(SpellCheckerService.class.getName())));
        System.out.println(gui.getSpellService());
        gui.start();
    }

    @Override
    public void stop(BundleContext context) throws Exception {

    }
}
