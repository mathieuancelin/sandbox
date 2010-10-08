package com.serli.soap.scope.impl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Service1Impl implements Service1 {

    @Inject
    private ScopedBean bean;

    @Inject
    private Service2 service;

    @Override
    public ScopedBean getBean() {
        return bean;
    }

    @Override
    public boolean isSameScopedBean() {
        return bean.toString().equals(service.getBean().toString());
    }
}
