package org.jboss.weld.environment.osgi;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class WeldActivator implements BundleActivator {
    
    @Override
    public void start(BundleContext context) throws Exception {
        WeldContainerOwner.get().setContext(context);
        WeldContainerOwner.get().init();
        ServiceImpl service = WeldContainerOwner.container().instance().select(ServiceImpl.class).get();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        // TODO : shutdown
    }
}
