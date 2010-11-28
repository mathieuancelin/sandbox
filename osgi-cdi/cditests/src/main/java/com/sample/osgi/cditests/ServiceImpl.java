package com.sample.osgi.cditests;

/**
 *
 * @author mathieu
 */
public class ServiceImpl implements Service {

    @Override
    public void execute() {
        System.out.println("Hello ServiceImpl !");
    }
}
