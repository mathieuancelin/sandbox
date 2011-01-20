package models;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mathieuancelin
 */
@XmlRootElement
public class Temoignages {

    private List<Temoignage> temoignages;

    @XmlElement(name="temoignage")
    public List<Temoignage> getTemoignages() {
        return temoignages;
    }

    public void setTemoignages(List<Temoignage> temoignages) {
        this.temoignages = temoignages;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (temoignages != null) {
            for (Temoignage t : temoignages) {
                builder.append(t.toString());
                builder.append("\n");
            }
        }
        return builder.toString();
    }
}
