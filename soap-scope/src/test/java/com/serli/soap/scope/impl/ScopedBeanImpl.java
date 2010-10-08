package com.serli.soap.scope.impl;

import com.serli.soap.scope.SOAPScoped;
import java.util.UUID;

@SOAPScoped
public class ScopedBeanImpl implements ScopedBean {

    private String name;

    private String ID;

    public ScopedBeanImpl() {
        this.name = Thread.currentThread().getName();
        this.ID = UUID.randomUUID().toString();
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String intr() {
        return "ScopedBeanImpl@" + System.identityHashCode(this) + " {name=" + name + ", ID=" + ID + '}';
    }
}
