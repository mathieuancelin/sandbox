package com.sample.osgi.cdi.activator;

import java.util.Enumeration;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class WeldActivator implements BundleActivator {

    private WeldContainer weld;
    private Starter gui;

    @Override
    public void start(BundleContext context) throws Exception {

        Enumeration e = context.getBundle().findEntries("META-INF", "beans.xml", false);
        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());
        }
        weld = new Weld().initialize(context);
        weld.event().select(ContainerInitialized.class).fire(new ContainerInitialized());
        gui = weld.instance().select(Starter.class).get();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }
}
