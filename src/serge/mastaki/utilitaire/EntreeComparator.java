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
package serge.mastaki.utilitaire;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import serge.mastaki.modele.Entree;
import static serge.mastaki.utilitaire.Util.enleverAccents;

/**
 *
 * @author Serge
 */
public class EntreeComparator implements Comparator<Entree> {

    private final String lang;

    /**
     *
     * @param lang
     */
    public EntreeComparator(String lang) {
        this.lang = lang;
    }

    /**
     *
     * @param e1
     * @param e2
     * @return
     */
    @Override
    public int compare(Entree e1, Entree e2) {
        String mot1 = e1.getMot();
        String mot2 = e2.getMot();
        return compare(mot1, mot2);
    }

    /**
     *
     * @param mot1
     * @param mot2
     * @return
     */
    public int compare(String mot1, String mot2) {
        int n = 0;
        if ("nnb".equals(lang)) {
            String m1 = enleverAccents(mot1);
            String m2 = enleverAccents(mot2);
            n = m1.compareToIgnoreCase(m2);
            if (n == 0) {
                n = mot1.compareToIgnoreCase(mot2);
                if (n == 0) {
                    n = mot1.compareTo(mot2);
                }
            }
        }
        if ("fr".equals(lang)) {
            Collator frCollator = Collator.getInstance(Locale.FRENCH);
            frCollator.setStrength(Collator.IDENTICAL);
            n = frCollator.compare(mot1, mot2);
        }
        return n;
    }
}
