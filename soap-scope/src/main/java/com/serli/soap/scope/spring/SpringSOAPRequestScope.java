package com.serli.soap.scope.spring;

import com.serli.soap.scope.SOAPRequestContext;
import com.serli.soap.scope.SOAPRequestContextHolder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

public class SpringSOAPRequestScope implements Scope {

    /**
     * {@inheritDoc }.
     */
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        final SOAPRequestContext context = SOAPRequestContextHolder.getRequestContext();
        Object scopedObject  = context.getBean(name);
        if (scopedObject == null) {
            scopedObject = objectFactory.getObject();
            context.setBean(name, scopedObject);
        }
        return scopedObject;
    }

    /**
     * {@inheritDoc }.
     */
    @Override
    public Object remove(String name) {
        final SOAPRequestContext context = SOAPRequestContextHolder.getRequestContext();
        final Object scopedObject = context.getBean(name);
        if (scopedObject != null) {
            context.removeBean(name);
            return scopedObject;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc }.
     */
    @Override
    public void registerDestructionCallback(String name, Runnable callback) {
        final SOAPRequestContext context = SOAPRequestContextHolder.getRequestContext();
        if (context != null) {
            context.registerRequestDestructionCallback(name, callback);
        }
    }

    /**
     * {@inheritDoc }.
     */
    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    /**
     * {@inheritDoc }.
     */
    @Override
    public String getConversationId() {
        return Thread.currentThread().getName();
    }
}