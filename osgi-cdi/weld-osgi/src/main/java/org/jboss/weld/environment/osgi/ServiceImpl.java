/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jboss.weld.environment.osgi;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

/**
 *
 * @author mathieu
 */
@Singleton
public class ServiceImpl {

    @PostConstruct
    public void startingWeld() {
        System.out.println("Weld is now started inside OSGi !!!");
    }
}
