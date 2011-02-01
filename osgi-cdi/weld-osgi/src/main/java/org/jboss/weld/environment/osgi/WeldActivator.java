package org.jboss.weld.environment.osgi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.jboss.weld.environment.osgi.events.ContainerShutdown;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.FrameworkUtil;
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

    // TODO : use log service

    @Override
    public void start(BundleContext context) throws Exception {
        context.addBundleListener(this);
        context.addServiceListener(this);
        WeldContainerOwner.get().setContext(context);
        WeldContainerOwner.get().init();
        manager = WeldContainerOwner.container().instance().select(ShutdownManager.class).get();
        WeldContainerOwner.container().instance().select(WeldStartMessage.class).get();
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
        exec.scheduleAtFixedRate(new Thread() {

            @Override
            public void run() {
                WeldContainerOwner.container().event().select(String.class).fire("scheduled");
            }

        }, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        WeldContainerOwner.container().event().fire(new ContainerShutdown());
        manager.shutdown();
    }

    @Override
    public void bundleChanged(BundleEvent event) {
        if (WeldContainerOwner.container() != null)
            WeldContainerOwner.container().event().fire(event);
        if (event.getType() == BundleEvent.STOPPED)
                System.out.print("Bundle STOPPED => " + event.getBundle().getSymbolicName());
        // TODO : scan the bundle and add bindings
        // TODO : register services in the registry
        // TODO : start startable OSGiBeans
        /**switch (event.getType()) {
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
        System.out.println(event.getBundle().getSymbolicName());**/
    }

    @Override
    public void serviceChanged(ServiceEvent event) {
        if (WeldContainerOwner.container() != null)
            WeldContainerOwner.container().event().fire(event);
        //System.out.println("Service changed : " + event.getType() + " => " + event.getServiceReference());
    }
}
