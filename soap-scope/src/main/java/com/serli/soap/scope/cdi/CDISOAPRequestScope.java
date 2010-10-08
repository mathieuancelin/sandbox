package com.serli.soap.scope.cdi;

import com.serli.soap.scope.SOAPRequestContext;
import com.serli.soap.scope.SOAPRequestContextHolder;
import com.serli.soap.scope.SOAPScoped;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

public class CDISOAPRequestScope implements Context {

    private Map<Class, Object> proxies = new HashMap<Class, Object>();

    @Override
    public Class<? extends Annotation> getScope() {
        return SOAPScoped.class;
    }

    @Override
    public <T> T get(final Contextual<T> contextual, final CreationalContext<T> creationalContext) {  
        final Bean bean = (Bean) contextual;
        if (!proxies.containsKey(bean.getBeanClass())) {
            ProxyFactory fact = new ProxyFactory();
            fact.setSuperclass(bean.getBeanClass());
            fact.setFilter(new MethodFilter() {
                @Override
                public boolean isHandled(Method method) {
                    return true;
                }
            });
            Class newBeanClass = fact.createClass();
            T scopedObject = null;
            try {
                scopedObject = (T) newBeanClass.cast(newBeanClass.newInstance());
            } catch (Exception ex) {
                throw new IllegalStateException("Impossible de créer un proxy pour l'objet " 
                        + bean.getBeanClass() + " dans le scope");
            }
            MethodHandler mHandler = new MethodHandler() {
                @Override
                public Object invoke(Object self, Method m,
                        Method proceed, Object[] args) throws Throwable {
                    final SOAPRequestContext context = SOAPRequestContextHolder.getRequestContext();
                    T scopedObject = (T) context.getBean(bean.getBeanClass().getName());
                    if (scopedObject == null) {
                        scopedObject = (T) bean.create(creationalContext);
                        context.setBean(bean.getBeanClass().getName(), scopedObject);
                    }
                    return m.invoke(scopedObject, args);
                }
            };
            ((ProxyObject) scopedObject).setHandler(mHandler);
            proxies.put(bean.getBeanClass(), scopedObject);
        }
        return (T) proxies.get(bean.getBeanClass());
    }

    @Override
    public <T> T get(Contextual<T> contextual) {
        Bean bean = (Bean) contextual;
        return (T) proxies.get(bean.getBeanClass());
    }

    @Override
    public boolean isActive() {
        final SOAPRequestContext context = SOAPRequestContextHolder.getRequestContext();
        return context.isRequestActive();
    }
}
