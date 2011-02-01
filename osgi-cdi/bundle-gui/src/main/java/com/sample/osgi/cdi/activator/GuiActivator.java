package com.sample.osgi.cdi.activator;

import com.sample.osgi.cdi.starter.Starter;
import com.sample.osgi.cdi.gui.SpellCheckerGui;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.InjectionTarget;
import org.jboss.weld.environment.osgi.WeldContainerOwner;
import org.jboss.weld.environment.osgi.WeldContainer;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class GuiActivator implements BundleActivator {

    private SpellCheckerGui gui;
    private WeldContainer weld;

    @Override
    public void start(BundleContext context) throws Exception {
        weld = WeldContainerOwner.container();
        //weld.instance().select(Starter.class).get();
        //weld.getBeanManager().getBeans(Starter.class);
        AnnotatedType<Starter> type = weld.getBeanManager().createAnnotatedType(Starter.class);
        InjectionTarget<Starter> it = weld.getBeanManager().createInjectionTarget(type);
        CreationalContext ctx = weld.getBeanManager().createCreationalContext(null);
        Starter instance = it.produce(ctx);  //call the constructor
        it.inject(instance, ctx);  //call initializer methods and perform field injection
        it.postConstruct(instance);  //call the @PostConstruct method
        gui = weld.instance().select(SpellCheckerGui.class).get();
        gui.start();
    }

    @Override
    public void stop(BundleContext context) throws Exception {}
}
