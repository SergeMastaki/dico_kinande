/* Dictionnaire kinande-français version 1.0
 * Copyright (C) 2016 Serge Mastaki (email : serge.mastaki@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package serge.mastaki.modele;

import org.jdom2.Element;
import static serge.mastaki.utilitaire.Util.enleverAccents;

/**
 *
 * @author Serge
 */
public class Entree {

    private String mot;
    private String radical;
    private String variante;
    private String traduction;

    /**
     *
     * @param mot
     * @param radical
     * @param variante
     * @param traduction
     */
    public Entree(String mot, String radical, String variante, String traduction) {
        this.mot = mot;
        this.radical = radical;
        this.variante = variante;
        this.traduction = traduction;
    }

    /**
     *
     * @param entree
     */
    public Entree(Element entree) {
        this.mot = entree.getAttributeValue("mot");
        this.radical = entree.getChildText("radical");
        this.variante = entree.getChildText("variante");
        this.traduction = entree.getChildText("traduction");
    }

    /**
     *
     * @param mot
     */
    public Entree(String mot) {
        this(mot, null, null, null);
    }

    /**
     *
     * @param mot
     * @param traduction
     */
    public Entree(String mot, String traduction) {
        this(mot, null, null, traduction);
    }

    /**
     *
     * @return
     */
    public String getMot() {
        return mot;
    }

    /**
     *
     * @return
     */
    public String getRadical() {
        return radical;
    }

    /**
     *
     * @return
     */
    public String getVariante() {
        return variante;
    }

    /**
     *
     * @return
     */
    public String getTraduction() {
        return traduction;
    }

    /**
     *
     * @param mot
     */
    public void setMot(String mot) {
        this.mot = mot;
    }

    /**
     *
     * @param radical
     */
    public void setRadical(String radical) {
        this.radical = radical;
    }

    /**
     *
     * @param variante
     */
    public void setVariante(String variante) {
        this.variante = variante;
    }

    /**
     *
     * @param traduction
     */
    public void setTraduction(String traduction) {
        this.traduction = traduction;
    }

    public boolean contientTout(String[] tabMots) {
        for (String motRech : tabMots) {
            if (motRech.isEmpty()) {
                continue;
            }
            if (!contient(motRech)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean contientAuMoins(String[] tabMots) {
        for (String motRech : tabMots) {
            if (motRech.isEmpty()) {
                continue;
            }
            if (contient(motRech)) {
                return true;
            }
        }
        return false;
    }

    public boolean contient(String motRech) {
        motRech = enleverAccents(motRech).toLowerCase();
        String motEntree = enleverAccents(mot).toLowerCase();
        if (motEntree.contains(motRech)) {
            return true;
        }
        String trad = enleverAccents(traduction).toLowerCase();
        if (trad.contains(motRech)) {
            return true;
        }
        if (radical != null) {
            String rad = enleverAccents(radical).toLowerCase();
            if (rad.contains(motRech)) {
                return true;
            }
        }
        if (variante != null) {
            String var = enleverAccents(variante).toLowerCase();
            if (var.contains(motRech)) {
                return true;
            }
        }
        return false;
    }
}
