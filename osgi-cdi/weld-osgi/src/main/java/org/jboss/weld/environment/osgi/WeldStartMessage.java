package org.jboss.weld.environment.osgi;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Singleton;

/**
 * Dummy class that display lifecycle messages.
 * 
 * @author Mathieu ANCELIN - SERLI (mathieu.ancelin@serli.com)
 */
@Singleton
public class WeldStartMessage {

    @PostConstruct
    public void startingWeld() {
        System.out.println("\nWeld is now started inside OSGi !!!\n");
    }

    @PreDestroy
    public void stoppingWeld() {
        System.out.println("\nWeld stopping ...\n");
    }
}
