package com.serli.groovy.properties

/**
 * Tests for the {@link Example} class.
 */
class ExampleTest extends GroovyTestCase {

    void testShow() {
        new Example().show()
        new Example().troulala()
        new Example().printlnI18N()
    }
}
