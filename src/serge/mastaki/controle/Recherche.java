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
package serge.mastaki.controle;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import serge.mastaki.modele.Entree;
import serge.mastaki.modele.ListeModele;
import serge.mastaki.utilitaire.EntreeComparator;
import static serge.mastaki.utilitaire.Util.afficheException;
import serge.mastaki.vue.Fenetre;
import serge.mastaki.vue.JListRollover;
import serge.mastaki.vue.SlidePanel;

/**
 *
 * @author Serge
 */
public class Recherche extends SwingWorker<Integer, Void> {

    private final Fenetre fen;
    private final JListRollover liste;
    private EntreeComparator comparateur = null;

    /**
     *
     * @param fen
     */
    public Recherche(Fenetre fen) {
        this.fen = fen;
        liste = fen.getListeVisible();
        SlidePanel slidePane = fen.getSlidePane();
        if (slidePane.getComposant() == fen.getTabbedPane()) {
            int indexTab = fen.getTabbedPane().getSelectedIndex();
            if (indexTab == 0) {
                comparateur = new EntreeComparator("nnb");
            } else if (indexTab == 1) {
                comparateur = new EntreeComparator("fr");
            }
        } else if (slidePane.getComposant() == fen.getPanRechAv()) {
            comparateur = new EntreeComparator(RechercheAvance.lang);
        }
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    @SuppressWarnings("null")
    protected Integer doInBackground() throws Exception {
        ArrayList<Entree> listeEntrees;
        listeEntrees = ((ListeModele) liste.getModel())
                .getListeEntrees();
        if(listeEntrees == null || comparateur == null){
            return -1;
        }
        String mot = fen.getZoneDeRecherche().getText();
        int taille = listeEntrees.size();
        int i = 0;
        while (!isCancelled() && i < taille) {
            if (comparateur.compare(mot, listeEntrees.get(i).getMot()) <= 0) {
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     *
     */
    @Override
    public void done() {
        try {
            int n = get();
            liste.setSelectedIndex(n);
            liste.ensureIndexIsVisible(n);
        } catch (InterruptedException | ExecutionException ex) {            
            afficheException(ex.getMessage());
        }
    }
}
