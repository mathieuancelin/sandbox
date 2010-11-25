/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.osgi.cdi.activator;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author mathieu
 */
@Singleton
public class Starter {

    @Inject
    private PaymentService service;

    @PostConstruct
    public void init() {
        service.pay(123.4);
    }
}
