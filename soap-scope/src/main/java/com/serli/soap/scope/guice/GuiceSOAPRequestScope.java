package com.serli.soap.scope.guice;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.serli.soap.scope.SOAPRequestContext;
import com.serli.soap.scope.SOAPRequestContextHolder;

public class GuiceSOAPRequestScope implements Scope {

    @Override
    public <T> Provider<T> scope(final Key<T> key, final Provider<T> creator) {
        final SOAPRequestContext context = SOAPRequestContextHolder.getRequestContext();
        T scopedObject  = (T) context.getBean(key.getTypeLiteral().getRawType().getName());
        if (scopedObject == null) {
            scopedObject = creator.get();
            context.setBean(key.getTypeLiteral().getRawType().getName(), scopedObject);
        }
        final T returnedObject = scopedObject;
        return new Provider<T>() {
            @Override
            public T get() {
                return returnedObject;
            }
        };
    }
}