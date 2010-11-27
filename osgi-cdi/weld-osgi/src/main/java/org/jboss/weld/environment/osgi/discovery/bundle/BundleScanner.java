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
package org.jboss.weld.environment.osgi.discovery.bundle;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.jboss.weld.bootstrap.api.Bootstrap;
import org.jboss.weld.bootstrap.spi.BeanDeploymentArchive;
import org.jboss.weld.environment.osgi.discovery.ImmutableBeanDeploymentArchive;
import org.jboss.weld.resources.spi.ResourceLoader;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Scan the installed bundles.
 */
public class BundleScanner {

    private static final Logger log = LoggerFactory.getLogger(BundleScanner.class);
    private final String[] resources;
    private final ResourceLoader resourceLoader;
    private final Bootstrap bootstrap;
    private final BundleContext context;

    public BundleScanner(BundleContext context, ResourceLoader resourceLoader, Bootstrap bootstrap, String... resources) {
        this.resources = resources;
        this.resourceLoader = resourceLoader;
        this.bootstrap = bootstrap;
        this.context = context;
    }

    public BeanDeploymentArchive scan() {
        // TODO : jars
        // TODO : self bundle first, bean priority
        List<String> discoveredClasses = new ArrayList<String>();
        List<URL> discoveredBeanXmlUrls = new ArrayList<URL>();
        Bundle[] bundles = context.getBundles();
        for (Bundle bundle : bundles) {
            Enumeration beansXml = bundle.findEntries("META-INF", "beans.xml", true);
            if (beansXml != null) {
                while (beansXml.hasMoreElements()) {
                    discoveredBeanXmlUrls.add((URL) beansXml.nextElement());
                }
                Enumeration beanClasses = bundle.findEntries("", "*.class", true);
                if (beanClasses != null) {
                    while (beanClasses.hasMoreElements()) {
                        URL url = (URL) beanClasses.nextElement();
                        String clazz = url.getFile().substring(1).replace("/", ".").replace(".class", "");
                        discoveredClasses.add(clazz);
                    }
                }
            }
        }
        // TODO : no more immutable. Scan new bundle deployed and add bindings
        return new ImmutableBeanDeploymentArchive("classpath",
                discoveredClasses, bootstrap.parse(discoveredBeanXmlUrls));
    }
}
