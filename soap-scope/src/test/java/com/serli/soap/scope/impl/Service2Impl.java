package com.serli.soap.scope.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Service2Impl implements Service2 {

    @Inject @com.google.inject.Inject
    private ScopedBean bean;

    @Override
    public ScopedBean getBean() {
        return bean;
    }
}
