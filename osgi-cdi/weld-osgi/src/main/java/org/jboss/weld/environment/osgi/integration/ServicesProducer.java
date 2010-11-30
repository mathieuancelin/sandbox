package org.jboss.weld.environment.osgi.integration;

import java.lang.reflect.ParameterizedType;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * Producers for Specific injected types;
 *
 * @author mathieu
 */
public class ServicesProducer {

    @Produces
    public <T> Services<T> getOSGiServices(InjectionPoint p) {
        return new Services<T>(((ParameterizedType)p.getType()).getActualTypeArguments()[0],
                p.getMember().getDeclaringClass());
    }

    @Produces
    public <T> Service<T> getOSGiService(InjectionPoint p) {
        return new Service<T>(((ParameterizedType)p.getType()).getActualTypeArguments()[0],
                p.getMember().getDeclaringClass());
    }
}
