package com.sample.osgi.cdi.activator;

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

    @Override
    public void start(BundleContext context) throws Exception {
        startWeld();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
    }

    private void startWeld() {
        weld = new Weld().initialize();
        weld.event().select(ContainerInitialized.class).fire(new ContainerInitialized());
    }
}
