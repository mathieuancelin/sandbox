/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jboss.weld.environment.osgi.context;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

/**
 *
 * @author mathieu
 */
public class OSGIContext implements Context {

    @Override
    public Class<? extends Annotation> getScope() {
        return Dependent.class;
    }

    private Map<Class, Object> instances = new HashMap<Class, Object>();

    @Override
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        System.out.println("create " + ((Bean)contextual).getBeanClass().getName());
        if (!instances.containsKey(((Bean)contextual).getBeanClass())) {
            instances.put(((Bean)contextual).getBeanClass(), contextual.create(creationalContext));
        }
        return (T) instances.get(((Bean)contextual).getBeanClass());
    }

    @Override
    public <T> T get(Contextual<T> contextual) {
        System.out.println("create or not " + ((Bean)contextual).getBeanClass().getName());
        return (T) instances.get(((Bean)contextual).getBeanClass());
    }

    @Override
    public boolean isActive() {
        return true;
    }

}
