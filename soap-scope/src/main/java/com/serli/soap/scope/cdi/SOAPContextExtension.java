package com.serli.soap.scope.cdi;

import com.serli.soap.scope.SOAPScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

public class SOAPContextExtension implements Extension {

    public void beforBeanDiscovery(@Observes BeforeBeanDiscovery event, BeanManager manager) {
        event.addScope(SOAPScoped.class, true, false);
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery event, BeanManager manager) {
        event.addContext(new CDISOAPRequestScope());
    }

}
