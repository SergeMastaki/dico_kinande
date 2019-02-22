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

import java.awt.EventQueue;
import java.awt.SplashScreen;
import java.io.IOException;
import java.net.URL;
import org.jdom2.JDOMException;
import serge.mastaki.modele.Dictionnaire;
import serge.mastaki.modele.ListeModele;
import static serge.mastaki.utilitaire.Util.afficheException;
import serge.mastaki.vue.Fenetre;
import serge.mastaki.vue.Splash;

/**
 * Cette classe contient la méthode principale qui lance l'exécution du
 * programme.
 *
 * @author Serge Mastaki
 */
public class Lancement {

    private static Dictionnaire dictionnaireFrancais;
    private static Dictionnaire dictionnaireKinande;

    /**
     * Méthode principale
     * <p>
     * Elle crée une fenêtre et définit sa taille et sa position à l'écran.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                Splash.init();
            }
        };
        t.start();
        final SplashScreen splash = Splash.getSplash();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                chargementDictionnaire();
                Fenetre fen = new Fenetre();
                ListeModele listeModeleKin = (ListeModele) fen.getListeKin().getModel();
                listeModeleKin.setListeEntrees(dictionnaireKinande.getListeEntrees());
                listeModeleKin.addEntrees();
                ListeModele listeModeleFr = (ListeModele) fen.getListeFr().getModel();
                listeModeleFr.setListeEntrees(dictionnaireFrancais.getListeEntrees());
                listeModeleFr.addEntrees();
                fen.setLocationRelativeTo(null);
                fen.setVisible(true);
                fen.afficherPageAccueil();
                if (splash != null && splash.isVisible()) {
                    splash.close();
                }
            }
        }
        );
    }

    private static void chargementDictionnaire() {
        try {
            URL url = ClassLoader.getSystemResource("database/dictionnaire_kinande.xml");
            dictionnaireKinande = new Dictionnaire(url, "nnb");
            url = ClassLoader.getSystemResource("database/dictionnaire_francais.xml");
            dictionnaireFrancais = new Dictionnaire(url, "fr");
        } catch (JDOMException | IOException ex) {            
            afficheException(ex.getMessage());
            System.exit(1);
        }
    }

    public static Dictionnaire getDictionnaireFrancais() {
        return dictionnaireFrancais;
    }

    public static Dictionnaire getDictionnaireKinande() {
        return dictionnaireKinande;
    }
}
