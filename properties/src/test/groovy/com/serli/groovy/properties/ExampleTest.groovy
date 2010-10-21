package com.serli.groovy.properties

/**
 * Tests for the {@link Example} class.
 */
class ExampleTest extends GroovyTestCase {

    void testShow() {
        def exemple = new Example()
        assert exemple.config.hello.world == "Hello World!"
        assert exemple.config.hello.kids == "Hello kids!"
        assert exemple.config.dummy == "dummy"
        exemple.dummy()
        exemple.printlnI18N()
        exemple.show()
    }
}
