package com.serli.soap.scope;

import com.serli.soap.scope.impl.ScopedBean;
import com.serli.soap.scope.impl.ScopedBeanImpl;
import com.serli.soap.scope.impl.SoapThread2;
import com.serli.soap.scope.impl.SoapThread;
import com.serli.soap.scope.impl.Service1;
import com.serli.soap.scope.impl.Service1Impl;
import com.serli.soap.scope.impl.Service2;
import com.serli.soap.scope.impl.Service2Impl;
import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import java.util.concurrent.Semaphore;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DSScopeTest {

    private Service1 service1;
    private Service2 service2;
    private DSInjector injector;
    private SOAPRequestListener listener = new SOAPRequestListener();

    @Before
    public void init() {
        injector = DependencyShot.getInjector(new Binder() {

            @Override
            public void configureBindings() {
                bind(Service1.class).to(Service1Impl.class);
                bind(Service2.class).to(Service2Impl.class);
                bind(ScopedBean.class).to(ScopedBeanImpl.class);
            }
        });
        service1 = injector.getInstance(Service1.class);
        service2 = injector.getInstance(Service2.class);
    }

    @Test
    public void testScopedSoap() {
        listener = new SOAPRequestListener();
        listener.incomingRequest();
        Assert.assertTrue(service1.isSameScopedBean());
        listener.outgoingRequest();
    }

    @Test
    public void testThreadedScopedSoap() throws InterruptedException {
        listener = new SOAPRequestListener();
        for (int i = 0; i < 1000; i++) {
            Semaphore s1 = new Semaphore(0);
            Semaphore s2 = new Semaphore(0);
            SoapThread thread = new SoapThread(s1, s2, listener, service1, service2);
            SoapThread2 thread2 = new SoapThread2(s1, s2, listener, service1, service2);
            thread.start();
            thread2.start();
            thread.join();
            thread2.join();
            Assert.assertEquals(thread.getPublisher().getBeanService1TS(), thread.getPublisher().getBeanService2TS());
            Assert.assertEquals(thread2.getPublisher().getBeanService1TS(), thread2.getPublisher().getBeanService2TS());
            Assert.assertFalse(thread2.getPublisher().getBeanService1TS().equals(thread.getPublisher().getBeanService1TS()));
            Assert.assertFalse(thread2.getPublisher().getBeanService2TS().equals(thread.getPublisher().getBeanService2TS()));
        }
    }
}
