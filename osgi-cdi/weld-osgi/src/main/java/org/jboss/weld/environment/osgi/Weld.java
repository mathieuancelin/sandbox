/**
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.weld.environment.osgi;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.UnsatisfiedResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import org.jboss.weld.bootstrap.WeldBootstrap;

import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.api.Environments;
import org.jboss.weld.bootstrap.spi.Deployment;
import org.jboss.weld.environment.osgi.discovery.bundle.WeldOSGiResourceLoader;
import org.jboss.weld.environment.osgi.discovery.bundle.WeldOSGiBundleDeployment;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * The preferred method of booting Weld SE.
 * </p>
 * 
 * <p>
 * Typical usage of this API looks like this:
 * </p>
 * 
 * <pre>
 * WeldContainer weld = new Weld().initialize();
 * weld.instance().select(Foo.class).get();
 * weld.event().select(Bar.class).fire(new Bar());
 * weld.shutdown();
 * </pre>
 * 
 * @author Peter Royle
 * @author Pete Muir
 */
public class Weld {

    private ShutdownManager shutdownManager;
    private BundleContext context;

    /**
     * Boots Weld and creates and returns a WeldContainer instance, through which
     * beans and events can be accessed.
     */
    public WeldContainer initialize(BundleContext context) {
        this.context = context;
        ResourceLoader resourceLoader = new WeldOSGiResourceLoader(context);
        Bootstrap bootstrap = null;
        bootstrap = (Bootstrap) new WeldBootstrap();
        Deployment deployment = createDeployment(resourceLoader, bootstrap);
        // Set up the container
        bootstrap.startContainer(Environments.SE, deployment);
        // Start the container
        bootstrap.startInitialization();
        bootstrap.deployBeans();
        //getInstanceByType(bootstrap.getManager(deployment.loadBeanDeploymentArchive(ShutdownManager.class)), ShutdownManager.class).setBootstrap(bootstrap);
        bootstrap.validateBeans();
        bootstrap.endInitialization();

        // Set up the ShutdownManager for later
        this.shutdownManager = getInstanceByType(bootstrap.getManager(deployment.loadBeanDeploymentArchive(ShutdownManager.class)), ShutdownManager.class);
        this.shutdownManager.setBootstrap(bootstrap);
        return getInstanceByType(bootstrap.getManager(deployment.loadBeanDeploymentArchive(WeldContainer.class)), WeldContainer.class);
    }

    protected Deployment createDeployment(ResourceLoader resourceLoader, Bootstrap bootstrap) {
        return new WeldOSGiBundleDeployment(context, resourceLoader, bootstrap);
    }

    /**
     * Utility method allowing managed instances of beans to provide entry points
     * for non-managed beans (such as {@link WeldContainer}). Should only called
     * once Weld has finished booting.
     *
     * @param manager the BeanManager to use to access the managed instance
     * @param type the type of the Bean
     * @param bindings the bean's qualifiers
     * @return a managed instance of the bean
     * @throws IllegalArgumentException if the given type represents a type
     *            variable
     * @throws IllegalArgumentException if two instances of the same qualifier
     *            type are given
     * @throws IllegalArgumentException if an instance of an annotation that is
     *            not a qualifier type is given
     * @throws UnsatisfiedResolutionException if no beans can be resolved * @throws
     *            AmbiguousResolutionException if the ambiguous dependency
     *            resolution rules fail
     * @throws IllegalArgumentException if the given type is not a bean type of
     *            the given bean
     *
     */
    protected <T> T getInstanceByType(BeanManager manager, Class<T> type, Annotation... bindings) {
        final Bean<?> bean = manager.resolve(manager.getBeans(type));
        if (bean == null) {
            throw new UnsatisfiedResolutionException("Unable to resolve a bean for " + type + " with bindings " + Arrays.asList(bindings));
        }
        CreationalContext<?> cc = manager.createCreationalContext(bean);
        return type.cast(manager.getReference(bean, type, cc));
    }

    /**
     * Shuts down Weld.
     */
    public void shutdown() {
        shutdownManager.shutdown();
    }
}
