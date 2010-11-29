package com.sample.osgi.cditests;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    @Test
    public void testCollection() {
        Services<Starter> s = new Services<Starter>();
        s.getClassType();
        WeldContainer container = new Weld().initialize();
        Starter starter = container.instance().select(Starter.class).get();
        starter.execute();
    }
}
