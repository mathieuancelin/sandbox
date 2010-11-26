package org.jboss.weld.environment.osgi;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.jboss.weld.environment.se.events.ContainerInitialized;
import org.osgi.framework.BundleContext;

/**
 *
 * @author mathieu
 */
public class WeldContainerOwner {

    private static final WeldContainerOwner owner = new WeldContainerOwner();

    private BundleContext context;

    private WeldContainer container;

    static WeldContainerOwner get() {
        return owner;
    }

    void setContext(BundleContext context) {
        this.context = context;
    }

    void init() {
        container = new Weld().initialize(context);
        container.event().select(ContainerInitialized.class).fire(new ContainerInitialized());
    }

    WeldContainer getContainer() {
        return container;
    }

    public static WeldContainer container() {
        return owner.getContainer();
    }

}
