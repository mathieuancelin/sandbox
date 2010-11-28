package org.jboss.weld.environment.osgi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import org.jboss.weld.environment.osgi.beans.InstanceManager;
import org.jboss.weld.environment.osgi.integration.ServicesProducer;

public class WeldOSGiExtension implements Extension {

    public void registerWeldSEBeans(@Observes BeforeBeanDiscovery event, BeanManager manager) {
        event.addAnnotatedType(manager.createAnnotatedType(ShutdownManager.class));
        event.addAnnotatedType(manager.createAnnotatedType(InstanceManager.class));
        event.addAnnotatedType(manager.createAnnotatedType(WeldContainer.class));
        event.addAnnotatedType(manager.createAnnotatedType(WeldStartMessage.class));
        event.addAnnotatedType(manager.createAnnotatedType(ServicesProducer.class));
    } 
    // TODO : provide dynamic injection
    // TODO : provide service lookup
    // TODO : add @OSGiService Qualifier for service lookup and dynamic ?
    // TODO : add startable components (@OSGiBean) with lifecycle to be started at bundle install
    // TODO : add injection for service registry, context, bundle, log service, entreprise stuff

    public void registerWeldSEContexts(@Observes AfterBeanDiscovery event) {}
}
