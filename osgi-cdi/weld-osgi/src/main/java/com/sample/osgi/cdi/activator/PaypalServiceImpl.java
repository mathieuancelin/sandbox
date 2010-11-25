/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.osgi.cdi.activator;

import javax.inject.Inject;

/**
 *
 * @author mathieu
 */
public class PaypalServiceImpl implements PaymentService {

    @Inject
    private LoggerService log;

    @Override
    public void pay(double price) {
        log.log("Paying " + price);
    }

}
