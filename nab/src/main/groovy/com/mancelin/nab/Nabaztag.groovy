package com.mancelin.nab

class Nabaztag {

    private String sn

    private String token

    private Nabaztag toi

    static reference(String sn, String token) {
        new Nabaztag(sn: sn, token: token)
    }

    def execute(Closure c) {
        assert sn != null
        assert token != null
        this.toi = this
        def clone = c.clone()
        clone.delegate = new Nabaztag()
        clone.resolveStrategy = Closure.DELEGATE_ONLY
        clone()
    }

    def bouge = { Oreille o ->
        [de: {Integer deg ->
            [pendant: {Integer duree ->
                println( "bouge $o de $deg degres, pendant $duree secondes")}]
        }]
    }

    def allume = { Led l ->
        [en: {Couleur c ->
            [pendant: {Integer duree
                -> println( "allume $l en $c pendant $duree secondes")}]
        }]
    }

    def tempo = { Integer tempo ->
        println "reglage du tempo : $tempo"
    }

    def parleComme = { Speaker s ->
        println "parle comme $s"
    }

    def dis = { String message -> 
        println "nabaztag : $message"
    }

    def joue = { String url ->
        println "streaming : $url"
    }

    def endors = { Nabaztag nab ->
        println "endorsToi"
    }

    def reveilles = { Nabaztag nab ->
        println "reveille"
    }

    def emailsNonLus = {
        println "3 emails non lus"
    }

}