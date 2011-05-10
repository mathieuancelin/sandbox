package cx.ath.mancel01.modules.module;

import cx.ath.mancel01.modules.api.Configuration;
import cx.ath.mancel01.modules.Modules;
import cx.ath.mancel01.modules.util.MainThread;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Module {

    public static final String VERSION_SEPARATOR = ":";

    private static final Logger logger = LoggerFactory.getLogger(Module.class);

    public final String identifier;

    public final String name;

    public final String version;

    private ModuleClassLoader moduleClassloader;

    private final Collection<String> dependencies;

    private final Modules delegateModules;

    private final Configuration configuration;

    public Module(final Configuration configuration, Modules modules) {
        this.identifier = configuration.name() + VERSION_SEPARATOR + configuration.version();
        this.name = configuration.name();
        this.version = configuration.version();
        this.dependencies = configuration.dependencies();
        this.configuration = configuration;
        this.delegateModules = modules;
        if (configuration.rootResource() != null) {
            this.moduleClassloader = new ModuleClassLoader(new URL[] {configuration.rootResource()}, this);
        }
    }

    public void start() {
        if (configuration.startable()) {
            Class<?> main = load(configuration.mainClass());
            try {
                Method mainMethod = main.getMethod("main", String[].class);
                MainThread t = new MainThread(mainMethod);
                t.start();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public Class<?> load(String name) {
        if (canLoad(name)) {
            try {
                return moduleClassloader.loadClass(name);
            } catch (ClassNotFoundException ex) {
                throw new IllegalStateException("Missing dependency for class "
                        + name + "from module " + identifier);
            }
        } else {
            for (String dependency : dependencies) {
                if (delegateModules.getModules().containsKey(dependency)) {
                    Module module = delegateModules.getModules().get(dependency);
                    if (module.canLoad(name)) {
                        logger.debug("Delegating {} to {}", name, module.identifier);
                        return module.load(name);
                    }
                }
            }
            throw new IllegalStateException("Missing dependency for class "
                    + name + " from module " + identifier);
        }
    }

    public boolean canLoad(String name) {
        return moduleClassloader.canLoad(name);
    }

    public Collection<String> dependencies() {
        return dependencies;
    }

    public void validate() {
        List<String> missing = new ArrayList<String>();
        for (String dependency : dependencies) {
            if (!delegateModules.getModules().containsKey(dependency)) {
                missing.add("Missing dependency : " + dependency);
            }
        }
        if (!missing.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String miss : missing) {
                builder.append(miss);
                builder.append("\n");
            }
            throw new IllegalStateException("Missing dependencies for module "
                    + identifier + " :\n" + builder.toString());
        }
    }

    Modules delegateModules() {
        return delegateModules;
    }

    Configuration configuration() {
        return configuration;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Module other = (Module) obj;
        if ((this.identifier == null) ? (other.identifier != null) : !this.identifier.equals(other.identifier)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.identifier != null ? this.identifier.hashCode() : 0);
        return hash;
    }

    public static String getIdentifier(String name, String version) {
        return name + Module.VERSION_SEPARATOR + version;
    }
}
