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

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import serge.mastaki.vue.Fenetre;
import serge.mastaki.vue.JListRollover;

/**
 *
 * @author Serge Mastaki
 */
public class ListeListener extends MouseAdapter {

    private final Fenetre fen;

    /**
     *
     * @param fen
     */
    public ListeListener(Fenetre fen) {
        this.fen = fen;
    }

    /**
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        JListRollover liste = fen.getListeVisible();
        if (liste == null) {
            return;
        }
        Point point = new Point(e.getX(), e.getY());
        int mouseOver = liste.locationToIndex(point);
        int index = liste.getSelectedIndex();
        if (mouseOver == index) {
            new AfficheTraduction(fen).execute();
        }
    }
}
