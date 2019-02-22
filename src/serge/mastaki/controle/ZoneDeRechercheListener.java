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

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import serge.mastaki.vue.Fenetre;
import serge.mastaki.vue.JListRollover;

/**
 *
 * @author Serge
 */
public class ZoneDeRechercheListener extends KeyAdapter {

    private final Fenetre fen;
    private final int HAUT = -1;
    private final int BAS = 1;
    private final JPopupMenu menuChoix;

    /**
     *
     * @param fen
     */
    public ZoneDeRechercheListener(Fenetre fen) {
        this.fen = fen;
        menuChoix = fen.getMenuChoix();
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
        if (fen.isModeAvance()) {
            menuChoix.show(fen.getContentPane(),
                    fen.getZoneDeRecherche().getX() + fen.getZoneDeRecherche().getWidth(),
                    fen.getZoneDeRecherche().getY());
            fen.getZoneDeRecherche().requestFocusInWindow();
        }
        new Recherche(fen).execute();
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        JListRollover liste = fen.getListeVisible();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                defilement(liste, HAUT);
                break;
            case KeyEvent.VK_DOWN:
                defilement(liste, BAS);
                break;
            case KeyEvent.VK_ENTER:
                new AfficheTraduction(fen).execute();
                break;
        }
    }

    private void defilement(JList liste, int dir) {
        if (liste == null) {
            return;
        }
        int fvIndex = liste.getFirstVisibleIndex();
        int lvIndex = liste.getLastVisibleIndex();
        int lsIndex = liste.getSelectedIndex();
        int nsIndex = 0;
        if (lsIndex < fvIndex) {
            nsIndex = fvIndex;
        } else if (lsIndex > lvIndex) {
            nsIndex = lvIndex;
        } else {
            nsIndex = Math.max(nsIndex, lsIndex + dir);
        }
        liste.setSelectedIndex(nsIndex);
        liste.ensureIndexIsVisible(nsIndex);
    }
}
