package org.jboss.weld.environment.osgi;

import org.jboss.weld.environment.se.ShutdownManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

/**
 * Entry point of the OSGi Bundle. Start the Weld container et listen to bundle
 * installation.
 *
 * @author Mathieu ANCELIN
 */
public class WeldActivator implements BundleActivator, BundleListener, ServiceListener {

    private ShutdownManager manager;

    @Override
    public void start(BundleContext context) throws Exception {
        context.addBundleListener(this);
        context.addServiceListener(this);
        WeldContainerOwner.get().setContext(context);
        WeldContainerOwner.get().init();
        manager = WeldContainerOwner.container().instance().select(ShutdownManager.class).get();
        WeldContainerOwner.container().instance().select(WeldStartMessage.class).get();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        manager.shutdown();
    }

    @Override
    public void bundleChanged(BundleEvent event) {
        switch (event.getType()) {
            case BundleEvent.INSTALLED:
                System.out.print("Bundle INSTALLED => "); break;
            case BundleEvent.LAZY_ACTIVATION:
                System.out.print("Bundle LAZY_ACTIVATION => "); break;
            case BundleEvent.RESOLVED:
                System.out.print("Bundle RESOLVED => "); break;
            case BundleEvent.STARTED:
                System.out.print("Bundle STARTED => "); break;
            case BundleEvent.STARTING:
                System.out.print("Bundle STARTING => "); break;
            case BundleEvent.STOPPED:
                System.out.print("Bundle STOPPED => "); break;
            case BundleEvent.STOPPING:
                System.out.print("Bundle STOPPING => "); break;
            case BundleEvent.UNINSTALLED:
                System.out.print("Bundle UNINSTALLED => "); break;
            case BundleEvent.UNRESOLVED:
                System.out.print("Bundle UNRESOLVED => "); break;
            case BundleEvent.UPDATED:
                System.out.print("Bundle UPDATED => "); break;
            default: break;
        }
        System.out.println(event.getBundle().getSymbolicName());
    }

    @Override
    public void serviceChanged(ServiceEvent event) {
        //System.out.println("Service changed : " + event.getType() + " => " + event.getServiceReference());
    }
}
