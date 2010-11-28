/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sample.osgi.cditests;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 *
 * @author mathieu
 */
public class ProducerBean {

    @Produces @OSGiService
    public <T> Collection<T> OSGiServices(InjectionPoint p,
            @New ServiceImpl s1, @New ServiceImpl2 s2, @New ServiceImpl3 s3) {
        System.out.println(((Class)((ParameterizedType)p.getType()).getActualTypeArguments()[0]).getName());
        List result = new ArrayList();
        result.add(s1);
        result.add(s2);
        result.add(s3);
        return result;
    }
}
