/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.osgi.cdi.starter;

/**
 *
 * @author mathieu
 */
public interface PaymentService {
    void pay(double price);

    void start();
}
