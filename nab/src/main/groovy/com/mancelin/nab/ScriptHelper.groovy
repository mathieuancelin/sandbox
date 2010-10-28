package com.mancelin.nab

class ScriptHelper {
    
    static String loadScript(File f) {
        return enhanceScript(f.getText())
    }

    static String enhanceScript(String script) {
        String head = "import com.mancelin.nab.Nabaztag\n\n"
        head += "import static com.mancelin.nab.Led.*\n"
        head += "import static com.mancelin.nab.Speaker.*\n"
        head += "import static com.mancelin.nab.Oreille.*\n"
        head += "import static com.mancelin.nab.Couleur.*\n\n"
        head += "Integer.metaClass.getDegre { delegate }\n"
        head += "Integer.metaClass.getDegres { delegate }\n"
        head += "Integer.metaClass.getHz { delegate }\n"
        head += "Integer.metaClass.getSeconde { delegate }\n"
        head += "Integer.metaClass.getSecondes { delegate }\n\n"
        return head + script
    }
}
