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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import serge.mastaki.utilitaire.EntreeComparator;

/**
 * Cette classe encapsule un dictionnaire Kinande
 *
 * @author Serge
 */
public class Dictionnaire {

    private final ArrayList<Entree> listeEntrees;

    public Dictionnaire(URL url, String lang) throws JDOMException, IOException {
        SAXBuilder sxb = new SAXBuilder();
        Document document = sxb.build(url);
        Element racine = document.getRootElement();
        List<Element> listeEnfants = racine.getChildren("entree");
        listeEntrees = new ArrayList<>();
        for (Element enfant : listeEnfants) {
            listeEntrees.add(new Entree(enfant));
        }
        Collections.sort(listeEntrees, new EntreeComparator(lang));
    }
    /**
     *
     * @return
     */
    public ArrayList<Entree> getListeEntrees() {
        return listeEntrees;
    }

    /**
     *
     * @return
     */
    public int size() {
        return listeEntrees.size();
    }

    /**
     *
     * @param index
     * @return
     */
    public Entree getEntreeAt(int index) {
        return listeEntrees.get(index);
    }
}
