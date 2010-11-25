/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.osgi.cdi.activator;

/**
 *
 * @author mathieu
 */
public class BanklogServiceImpl implements LoggerService {

    @Override
    public void log(String message) {
        System.out.println("Bank : " + message);
    }

}
