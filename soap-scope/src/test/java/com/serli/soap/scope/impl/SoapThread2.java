package com.serli.soap.scope.impl;

import com.serli.soap.scope.SOAPRequestListener;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SoapThread2 extends Thread {

    private final SOAPRequestListener listener;

    private final Service1 service1;

    private final Service2 service2;

    private final Semaphore s1;

    private final Semaphore s2;

    private final BeanPublisher publisher;

    public SoapThread2(Semaphore s1, Semaphore s2, SOAPRequestListener listener, Service1 service1, Service2 service2) {
        this.listener = listener;
        this.service1 = service1;
        this.service2 = service2;
        this.s1 = s1;
        this.s2 = s2;
        this.publisher = new BeanPublisher();
    }

    @Override
    public void run() {
        try {
            s1.acquire();
            s2.release();
            listener.incomingRequest();
            this.publisher.setBeanService1(this.service1.getBean().toString());
            this.publisher.setBeanService2(this.service2.getBean().toString());
            this.publisher.setBeanService1TS(this.service1.getBean().intr());
            this.publisher.setBeanService2TS(this.service2.getBean().intr());
            listener.outgoingRequest();

        } catch (InterruptedException ex) {
            Logger.getLogger(SoapThread2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BeanPublisher getPublisher() {
        return publisher;
    }

}
