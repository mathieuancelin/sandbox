package cx.ath.mancel01.modules.module;

import java.net.URL;
import java.net.URLClassLoader;

import cx.ath.mancel01.modules.Modules;
import java.io.File;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModuleClassLoader extends URLClassLoader {

    private static final Logger logger = LoggerFactory.getLogger(ModuleClassLoader.class);

    public static final List<String> bootDelegationList;

    private final Module module;

    private final List<String> managedClasses;

    static {
        bootDelegationList = new ArrayList<String>();
        bootDelegationList.add("java.");
        bootDelegationList.add("javax.");
        bootDelegationList.add("org.w3c.dom");
        bootDelegationList.add("sun.");
        bootDelegationList.add("sunw.");
        bootDelegationList.add("com.sun.");
    }

    public ModuleClassLoader(URL[] urls, Module from) {
        super(urls, Modules.CLASSPATH_MODULE.getLoader());
        this.module = from;
        managedClasses = new ArrayList<String>();
        URL root = module.configuration().rootResource();
        String fileName = root.getFile();
        try {
            ZipFile file = new ZipFile(new File(fileName));
            Enumeration entries = file.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.getName().endsWith(".class")) {
                    String className = entry.getName().substring(0, entry.getName().lastIndexOf("."));
                    className = className.replace("/", ".").replace("$", ".");
                    managedClasses.add(className);
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        for (String pack : bootDelegationList) {
            if (name.startsWith(pack)) {
                logger.debug("Delegating {} to {}", name, Modules.CLASSPATH_MODULE.identifier);
                return Modules.CLASSPATH_MODULE.load(name);
            }
        }
        boolean err = false;
        try {
            return super.loadClass(name);
        } catch (Throwable t) {
            err = true;
            for (String dependency : module.dependencies()) {
                if (module.delegateModules().getModules().containsKey(dependency)) {
                    Module dep =module.delegateModules().getModules().get(dependency);
                    if (dep.canLoad(name)) {
                        logger.debug("Delegating {} to {}", name, dep.identifier);
                        return dep.load(name);
                    }
                }
            }
            throw new IllegalStateException("Missing dependency for class " + name + " from module " + module.identifier);
        } finally {
            if (!err) {
                logger.debug("Loading {} from {}", name, module.identifier);
            }
        }
    }

    public boolean canLoad(String name) {
        boolean can = true;
        for (String className : managedClasses) {
            if (className.equals(name)) {
                return true;
            }
        }
        Class<?> clazz = findLoadedClass(name);
        if (clazz == null) {
            try {
                Class<?> cl = loadClass(name);
            } catch (Throwable t) {
                can = false;
            }
        }
        return can;
    }
}
