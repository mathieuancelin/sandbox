/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package models;

import java.util.Date;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mathieuancelin
 */
@XmlRootElement
public class Temoignage {

    private Long id;
    private String dateCourrier;
    private String fai;
    private String collecteIP;
    private Long nbrMusiques;
    private Long nbrFilms;
    private Long nbrJeux;
    private String codePostal;
    private String typeCourrier;

    @XmlElement(name="type_courrier")
    public String getTypeCourrier() {
        return typeCourrier;
    }

    public void setTypeCourrier(String typeCourrier) {
        this.typeCourrier = typeCourrier;
    }

    @XmlElement(name="codepostal")
    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @XmlElement(name="date_collecteIP")
    public String getCollecteIP() {
        return collecteIP;
    }

    public void setCollecteIP(String collecteIP) {
        this.collecteIP = collecteIP;
    }

    @XmlElement(name="date_courrier")
    public String getDateCourrier() {
        return dateCourrier;
    }

    public void setDateCourrier(String dateCourrier) {
        this.dateCourrier = dateCourrier;
    }

    public String getFai() {
        return fai;
    }

    public void setFai(String fai) {
        this.fai = fai;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlElement(name="film")
    public Long getNbrFilms() {
        return nbrFilms;
    }

    public void setNbrFilms(Long nbrFilms) {
        this.nbrFilms = nbrFilms;
    }

    @XmlElement(name="jeuxvideo")
    public Long getNbrJeux() {
        return nbrJeux;
    }

    public void setNbrJeux(Long nbrJeux) {
        this.nbrJeux = nbrJeux;
    }

    @XmlElement(name="musique")
    public Long getNbrMusiques() {
        return nbrMusiques;
    }

    public void setNbrMusiques(Long nbrMusiques) {
        this.nbrMusiques = nbrMusiques;
    }

    @Override
    public String toString() {
        return "Temoignage { " + "id=" + id + ",dateCourrier=" + dateCourrier
                + ",fai=" + fai + ",collecteIP=" + collecteIP + ",nbrMusiques="
                + nbrMusiques + ",nbrFilms=" + nbrFilms + ",nbrJeux=" + nbrJeux
                + ",codePostal=" + codePostal + ",typeCourrier=" + typeCourrier + " }";
    }
}
