package controllers;

import java.net.URL;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import models.Temoignage;
import models.Temoignages;
import play.mvc.*;


public class Application extends Controller {

    public static void index() {
        List<Temoignage> temoignages = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Temoignages.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            Temoignages doc = (Temoignages) unmarshaller.unmarshal(new URL("http://www.hadopi-data.fr/export.xml"));
            temoignages = doc.getTemoignages();
            Collections.sort(temoignages, new Comparator<Temoignage>() {
                public int compare(Temoignage o1, Temoignage o2) {
                    if (o1.getId() > o2.getId())
                        return 1;
                    if (o2.getId() > o1.getId())
                        return -1;
                    return 0;
                }
            });
        } catch (Exception ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
        render(temoignages);
    }

}