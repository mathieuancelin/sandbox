package com.serli.groovy.properties

/**
 * Example Groovy class.
 */
@WithProperties
class Example {

    def show() {
        println "$ExampleProperties.hello.world"
    }

    def troulala() {
        println ExampleProperties.troulala
    }

    def printlnI18N() {
        println "in the properties file : $ExampleProperties.props.text"
    }
}
