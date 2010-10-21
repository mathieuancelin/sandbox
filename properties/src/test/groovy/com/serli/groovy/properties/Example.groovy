package com.serli.groovy.properties

@WithProperties
class Example {

    def show() {
        println "$config.hello.world"
    }

    def dummy() {
        println config.dummy
    }

    def printlnI18N() {
        println "in the properties file : $config.hello.kids"
    }
}
