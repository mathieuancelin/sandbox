package cx.ath.mancel01.modules;

import cx.ath.mancel01.modules.api.Configuration;
import cx.ath.mancel01.modules.module.Module;
import java.io.File;
import java.util.Collections;
import org.junit.Assert;
import org.junit.Test;

public class ModulesTest {

    @Test(expected=ClassNotFoundException.class)
    public void testNADependencies() throws Exception {
        Class.forName("cx.ath.mancel01.modules.module1.HelloUtils");
    }

    @Test(expected=ClassNotFoundException.class)
    public void testNADependencies2() throws Exception {
        Class.forName("cx.ath.mancel01.modules.module2.App");
    }

    @Test(expected=ClassNotFoundException.class)
    public void testNADependencies3() throws Exception {
        Class.forName("cx.ath.mancel01.modules.module3.Logging");
    }

    @Test
    public void testModulesCircular() throws Exception {
        Configuration configuration1 =
                Modules.getConfigurationFromJar(
                new File("../module1-circular/target/module1-circular-1.0-SNAPSHOT.jar").toURI().toURL());
        Configuration configuration2 = Modules.
                getConfigurationFromJar(
                new File("../module2/target/module2-1.0-SNAPSHOT.jar").toURI().toURL());
        Configuration configuration3 = Modules.
                getConfigurationFromJar(
                new File("../module3/target/module3-1.0-SNAPSHOT.jar").toURI().toURL());
        Modules modules = new Modules();
        modules.addModules(configuration1, configuration2, configuration3);
        modules.startModule(Module.getIdentifier(configuration2.name(), configuration2.version()));
    }

    @Test
    public void testModules() throws Exception {
        Configuration configuration1 = 
                Modules.getConfigurationFromJar(
                new File("../module1/target/module1-1.0-SNAPSHOT.jar").toURI().toURL());
        Configuration configuration2 = Modules.
                getConfigurationFromJar(
                new File("../module2/target/module2-1.0-SNAPSHOT.jar").toURI().toURL());
        Configuration configuration3 = Modules.
                getConfigurationFromJar(
                new File("../module3/target/module3-1.0-SNAPSHOT.jar").toURI().toURL());
        Modules modules = new Modules();
        modules.addModule(configuration1);
        modules.addModule(configuration3);
        modules.addModule(configuration2);
        modules.startModule(Module.getIdentifier(configuration2.name(), configuration2.version()));
    }

    @Test
    public void testModules2() throws Exception {
        Configuration configuration1 =
                Modules.getConfigurationFromJar(
                new File("../module1/target/module1-1.0-SNAPSHOT.jar").toURI().toURL());
        Configuration configuration2 = Modules.
                getConfigurationFromJar(
                new File("../module2/target/module2-1.0-SNAPSHOT.jar").toURI().toURL());
        Configuration configuration3 = Modules.
                getConfigurationFromJar(
                new File("../module3/target/module3-1.0-SNAPSHOT.jar").toURI().toURL());
        Modules modules = new Modules();
        modules.addModules(configuration1, configuration2, configuration3);
        modules.startModule(Module.getIdentifier(configuration2.name(), configuration2.version()));
    }

    @Test(expected=RuntimeException.class)
    public void testModules3() throws Exception {
        Configuration configuration2 = Modules.
                getConfigurationFromJar(
                new File("../module2/target/module2-1.0-SNAPSHOT.jar").toURI().toURL());
        Configuration configuration3 = Modules.
                getConfigurationFromJar(
                new File("../module3/target/module3-1.0-SNAPSHOT.jar").toURI().toURL());
        Modules modules = new Modules();
        modules.addModules(configuration2, configuration3);
        modules.startModule(Module.getIdentifier(configuration2.name(), configuration2.version()));
    }

    @Test
    public void testResources() throws Exception {
        Configuration configuration1 =
                Modules.getConfigurationFromJar(
                new File("../module1/target/module1-1.0-SNAPSHOT.jar").toURI().toURL());
        Configuration configuration2 = Modules.
                getConfigurationFromJar(
                new File("../module2/target/module2-1.0-SNAPSHOT.jar").toURI().toURL());
        Configuration configuration3 = Modules.
                getConfigurationFromJar(
                new File("../module3/target/module3-1.0-SNAPSHOT.jar").toURI().toURL());
        Modules modules = new Modules();
        modules.addModules(configuration1, configuration2, configuration3);
        Module module1 = modules.getModule(configuration1.identifier());
        Module module2 = modules.getModule(configuration2.identifier());
        Assert.assertNotNull(module1.getResource("META-INF/configuration.properties"));
        Assert.assertNotNull(module1.getResourceAsStream("META-INF/configuration.properties"));
        Assert.assertEquals(3, Collections.list(module2.getResources("META-INF/configuration.properties")).size());
    }

}
