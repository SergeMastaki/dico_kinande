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

import java.util.ArrayList;
import javax.swing.DefaultListModel;

/**
 *
 * @author Serge
 */
public class ListeModele extends DefaultListModel {

    private ArrayList<Entree> listeEntrees;

    /**
     *
     * @return
     */
    public ArrayList<Entree> getListeEntrees() {
        return listeEntrees;
    }

    public void setListeEntrees(ArrayList<Entree> listeEntrees) {
        this.listeEntrees = listeEntrees;
    }

    public void addEntrees() {
        for (Entree entree : listeEntrees) {
            this.addElement(entree.getMot());
        }
    }

}
