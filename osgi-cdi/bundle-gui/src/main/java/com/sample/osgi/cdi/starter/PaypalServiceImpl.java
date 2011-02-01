/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.osgi.cdi.starter;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author mathieu
 */
@Singleton
public class PaypalServiceImpl implements PaymentService {

    @Inject
    private LoggerService log;

    @Override
    public void pay(double price) {
        log.log("Paying " + price);
    }

    @PostConstruct
    @Override
    public void start() {
        System.out.println("start paypal");
    }

}
