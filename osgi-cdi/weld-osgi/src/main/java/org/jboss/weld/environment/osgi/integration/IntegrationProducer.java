package org.jboss.weld.environment.osgi.integration;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 *
 * @author mathieuancelin
 */
public class IntegrationProducer {
    
    @Produces
    public Bundle getBundle(InjectionPoint p) {
        Bundle bundle = FrameworkUtil.getBundle(p.getMember().getDeclaringClass());
        if (bundle != null) {
            return bundle;
        } else {
            throw new IllegalStateException("Can't find actual bundle.");
        }
    }

    @Produces
    public BundleContext getBundleContext(InjectionPoint p) {
        Bundle bundle = FrameworkUtil.getBundle(p.getMember().getDeclaringClass());
        if (bundle != null) {
            return bundle.getBundleContext();
        } else {
            throw new IllegalStateException("Can't find actual bundle.");
        }
    }

//    @Produces
//    public org.osgi.service.log.LogService getLogService(InjectionPoint p) {
//        Bundle bundle = FrameworkUtil.getBundle(p.getMember().getDeclaringClass());
//        if (bundle != null) {
//            ServiceTracker tracker = new ServiceTracker(bundle.getBundleContext(), org.osgi.service.log.LogService.class.getName(), null);
//            tracker.open();
//            org.osgi.service.log.LogService service = (org.osgi.service.log.LogService) tracker.getService();
//            if (service != null) {
//                return service;
//            } else {
//                throw new IllegalStateException("Can't find LogService.");
//            }
//        } else {
//            throw new IllegalStateException("Can't find actual bundle.");
//        }
//    }
}
