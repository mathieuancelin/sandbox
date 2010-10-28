package com.mancelin.nab

import static ScriptHelper.*
import static Couleur.*
import static Led.*
import static Oreille.*
import static Speaker.*

class ExampleTest extends GroovyTestCase {

    void testShow() {

        Integer.metaClass.getDegre { delegate }
        Integer.metaClass.getDegres { delegate }
        Integer.metaClass.getHz { delegate }
        Integer.metaClass.getSeconde { delegate }
        Integer.metaClass.getSecondes { delegate }
                                                              
        def result = new GroovyShell()
                .evaluate( loadScript( new File( "src/test/resources/actions.nabaztag") ) );

        Nabaztag.reference("UU9876", "54554345").execute {

            endors toi

            reveilles toi

            emailsNonLus

            joue "http://bonjour.com/bonjour.mp3"

            parleComme julie

            dis "bonjour tout le monde"

            allume led_du_milieu en vert pendant 3.secondes

            bouge oreille_droite de 12.degres pendant 6.secondes
            
        }
        
    }
}
