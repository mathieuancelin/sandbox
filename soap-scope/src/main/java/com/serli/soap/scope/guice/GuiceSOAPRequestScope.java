package com.serli.soap.scope.guice;

import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.serli.soap.scope.SOAPRequestContext;
import com.serli.soap.scope.SOAPRequestContextHolder;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class GuiceSOAPRequestScope implements Scope {

    private Map<Class, Object> proxies = new HashMap<Class, Object>();

    @Override
    public <T> Provider<T> scope(final Key<T> key, final Provider<T> creator) {
        final Class clazz = key.getTypeLiteral().getRawType();
        System.out.println("get " + Thread.currentThread().getName());
        return new Provider<T>() {

            public final T getObject() {
                return creator.get();
            }

            @Override
            public T get() {
                if (!proxies.containsKey(clazz)) {
                    ProxyFactory fact = new ProxyFactory();
                    if (clazz.isInterface())
                        fact.setInterfaces(new Class[] {clazz});
                    else
                        fact.setSuperclass(clazz);
                    fact.setFilter(new MethodFilter() {
                        @Override
                        public boolean isHandled(Method method) {
                            return true;
                        }
                    });
                    Class newBeanClass = fact.createClass();
                    T scopedObject = null;
                    try {
                        scopedObject = (T) newBeanClass.cast(newBeanClass.newInstance());
                    } catch (Exception ex) {
                        throw new IllegalStateException("Impossible de créer un proxy pour l'objet "
                                + clazz + " dans le scope");
                    }
                    final MyProvider<T> provider = new MyProvider(key, creator);
                    MethodHandler mHandler = new MethodHandler() {
                        @Override
                        public Object invoke(Object self, Method m,
                                Method proceed, Object[] args) throws Throwable {
                            T scopedObject = provider.get();
                            return m.invoke(scopedObject, args);
                        }
                    };
                    ((ProxyObject) scopedObject).setHandler(mHandler);
                    proxies.put(clazz, scopedObject);
                }
                return (T) proxies.get(clazz);
            }
        };
    }

    public static class MyProvider<T> implements Provider<T> {

        private final Provider<T> creator;

        private final Key<T> key;

        public MyProvider(Key<T> key, Provider<T> creator) {
            this.creator = creator;
            this.key = key;
        }

        @Override
        public T get() {
            System.out.println("get ...");
            final SOAPRequestContext context = SOAPRequestContextHolder.getRequestContext();
            T scopedObject = (T) context.getBean(key.getTypeLiteral().getRawType().getName());
            if (scopedObject == null) {
                scopedObject = creator.get();
                context.setBean(key.getTypeLiteral().getRawType().getName(), scopedObject);
            }
            return scopedObject;
        }

    }
}
