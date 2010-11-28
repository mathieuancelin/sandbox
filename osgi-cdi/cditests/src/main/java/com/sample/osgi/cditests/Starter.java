package com.sample.osgi.cditests;

import java.util.Collection;
import javax.inject.Inject;

/**
 *
 * @author mathieu
 */
public class Starter {

    @Inject @OSGiService
    private Collection<Service> services;

    public void execute() {
        for (Service serv : services) {
            serv.execute();
        }
    }
}
