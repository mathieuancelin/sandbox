package com.serli.soap.scope.ds;

import com.serli.soap.scope.SOAPRequestContext;
import com.serli.soap.scope.SOAPRequestContextHolder;
import com.serli.soap.scope.SOAPScoped;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.CustomScopeHandler;
import java.lang.annotation.Annotation;

public class DSSOAPRequestScope  extends CustomScopeHandler {

    @Override
    public Class<? extends Annotation> getScope() {
        return SOAPScoped.class;
    }

    @Override
    public <T> T getScopedInstance(Class<T> interf, Class<? extends T> clazz, InjectorImpl injector) {
        final SOAPRequestContext context = SOAPRequestContextHolder.getRequestContext();
        T scopedObject  = (T) context.getBean(interf.getName());
        if (scopedObject == null) {
            scopedObject = injector.getUnscopedInstance(interf);
            context.setBean(interf.getName(), scopedObject);
        }
        return scopedObject;
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean isBeanValid(Class from, Class to) {
        return from.isInterface();
    }
}