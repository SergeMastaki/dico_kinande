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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import serge.mastaki.vue.Fenetre;
import serge.mastaki.vue.SlidePanel;

/**
 * Ecoute les actions sur les boutons de recherche, de recherche avancée ou de
 * retour de la fenêtre et le changement d'état (arrêt ou marche) du
 * <code>Timer</code> qui anime le panneau contenant la liste des mots.
 *
 * @author Serge Mastaki
 */
public class BtnListener implements ActionListener, PropertyChangeListener {

    private final Fenetre fen;
    private final SlidePanel slidePane;
    private int dir;

    /**
     * Initialise les champs <code>fen</code> et <code>slidePane</code>.
     *
     * @param fen Référence le classe <code>serge.mastaki.vue.Fenetre</code>
     * dont on gère les événements
     */
    public BtnListener(Fenetre fen) {
        this.fen = fen;
        slidePane = fen.getSlidePane();
    }

    /**
     * Gère les actions de l'utilisateur à l'appui des boutons de recherche, de
     * recherche avancée ou de retour.
     *
     * @param e Evénement associé à l'action sur les boutons de la fenêtre
     * écoutée.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "search":
                afficheTraduction();
                break;
            case "advancedSearch":
                if (slidePane.getComposant() == fen.getTabbedPane()) {
                    slidePane.getTimer().stop();
                    slidePane.slide(SlidePanel.GAUCHE, 5);
                    dir = SlidePanel.GAUCHE;
                }
                ouvrirDialogRechAv();
                break;
            case "back":
                if (slidePane.getComposant() == fen.getTabbedPane()) {
                    if (slidePane.isRunning()) {
                        slidePane.getTimer().stop();
                        slidePane.slide(SlidePanel.DROITE, 5);
                        dir = SlidePanel.DROITE;
                    }
                } else {
                    if (slidePane.isRunning() || dir == SlidePanel.DROITE) {
                        slidePane.getTimer().stop();
                        slidePane.slide(SlidePanel.GAUCHE, 5);
                        dir = SlidePanel.GAUCHE;
                    }
                }
                break;
        }
    }

    /**
     * Permet d'automatiser le changement de direction du panneau lorsque
     * celui-ci devient invisible. Elle permet aussi de charger le panneau
     * approprié en fonction du mode de recherche de la fenêtre.
     *
     * @param evt Evénement associé au changement de propriété du
     * <code>Timer</code> qui déplace le panneau.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!slidePane.isRunning() && dir == SlidePanel.GAUCHE) {
            slidePane.removeAll();
            if (fen.isModeAvance()) {
                slidePane.add(fen.getPanRechAv(), null);
            } else {
                slidePane.add(fen.getTabbedPane(), null);
            }
            slidePane.slide(SlidePanel.DROITE, 5);
            dir = SlidePanel.DROITE;
        }
    }

    /**
     * Ouvre une boîte de dialogue à l'appui du bouton de recherche avancée et
     * permet de choisir la langue de recherche ou d'annuler l'opération. En cas
     * d'annulation, elle commande un retour rapide du panneau.
     */
    private void ouvrirDialogRechAv() {
        Object[] options = {"kinande",
            "français",
            "annuler"};
        int choix = JOptionPane.showOptionDialog(fen,
                "Vous souhaitez effectuer la recherche\n"
                + "dans quelle langue?",
                "Langue de la recherche",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[2]);
        String lang = new String();
        switch (choix) {
            case 0:
                lang = "nnb";
                break;
            case 1:
                lang = "fr";
                break;
            case 2:
            case JOptionPane.CLOSED_OPTION:
                if (slidePane.isRunning()) {
                    slidePane.getTimer().stop();
                    slidePane.slide(SlidePanel.DROITE, 5);
                    dir = SlidePanel.DROITE;
                }
                break;
        }
        if (choix == 0 || choix == 1) {
            JPopupMenu menuChoix = fen.getMenuChoix();
            boolean ouSelected = ((JRadioButtonMenuItem) menuChoix.getComponent(0)).isSelected();
            int mode = ouSelected ? RechercheAvance.ET : RechercheAvance.OU;
            new RechercheAvance(fen, lang, mode).execute();
        }
    }

    private void afficheTraduction() {
        final Recherche recherche = new Recherche(fen);
        recherche.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("state".equals(evt.getPropertyName()) && recherche.isDone()) {
                    new AfficheTraduction(fen).execute();
                }
            }
        });
        recherche.execute();
    }
}
