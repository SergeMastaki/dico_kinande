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
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import serge.mastaki.modele.Entree;
import serge.mastaki.modele.ListeModele;
import static serge.mastaki.utilitaire.Util.enleverAccents;
import serge.mastaki.vue.Fenetre;
import serge.mastaki.vue.JListRollover;

/**
 *
 * @author Serge
 */
public class RechercheAvance extends SwingWorker<Void, Entree> {

    private final Fenetre fen;
    static String lang;
    private final ListeModele modele;
    private final int mode;
    public static final int ET = 0;
    public static final int OU = 1;

    /**
     *
     * @param fen
     * @param lang
     * @param mode
     */
    public RechercheAvance(Fenetre fen, String lang, int mode) {
        this.fen = fen;
        this.mode = mode;
        RechercheAvance.lang = lang;
        JListRollover liste = fen.getListeRechAv();
        modele = (ListeModele) liste.getModel();
    }

    @Override
    protected Void doInBackground() throws Exception {
        ArrayList<Entree> listeEntrees = new ArrayList<>();
        final ArrayList<Entree> resultats = new ArrayList<>();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                fen.getLabelRechAv().setText("0 % effectué");
                modele.clear();
                modele.setListeEntrees(resultats);
            }
        });
        int avancement = 0;
        setProgress(0);
        switch (lang) {
            case "nnb":
                listeEntrees = Lancement.getDictionnaireKinande().getListeEntrees();
                break;
            case "fr":
                listeEntrees = Lancement.getDictionnaireFrancais().getListeEntrees();
                break;
        }
        String expression = fen.getZoneDeRecherche().getText();
        expression = enleverAccents(expression);
        String[] tabMots = expression.split("\\W");
        for (Entree entree : listeEntrees) {
            if (isCancelled()) {
                break;
            }
            avancement++;
            setProgress(100 * avancement / listeEntrees.size());
            switch (mode) {
                case ET:
                    if (entree.contientTout(tabMots)) {
                        resultats.add(entree);
                        publish(entree);
                    }
                    break;
                case OU:
                    if (entree.contientAuMoins(tabMots)) {
                        resultats.add(entree);
                        publish(entree);
                    }
                    break;
            }
        }
        return null;
    }

    @Override
    protected void done() {
        int n = modele.getSize();
        String message;
        switch (n) {
            case 0:
                message = "aucune correspondance";
                break;
            case 1:
                message = "1 résultat trouvé";
                break;
            default:
                message = n + " résultats trouvés";
        }
        fen.getLabelRechAv().setText(message);
    }

    @Override
    protected void process(List<Entree> chunks) {
        int n = getProgress();
        fen.getLabelRechAv().setText(n + " % effectué" + ((n > 1) ? "s" : ""));
        for (Entree entree : chunks) {
            modele.addElement(entree.getMot());
        }
    }

}
