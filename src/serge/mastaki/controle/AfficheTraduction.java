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

import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.StyleSheet;
import serge.mastaki.modele.Entree;
import serge.mastaki.modele.ListeModele;
import static serge.mastaki.utilitaire.Util.afficheException;
import serge.mastaki.vue.Fenetre;
import serge.mastaki.vue.HtmlPane;
import serge.mastaki.vue.JListRollover;

/**
 *
 * @author Serge Mastaki
 */
public class AfficheTraduction extends SwingWorker<String, Void> {

    private final Fenetre fen;
    private final HtmlPane pane;
    private final JListRollover liste;

    /**
     *
     * @param fen
     */
    public AfficheTraduction(Fenetre fen) {
        this.fen = fen;
        liste = fen.getListeVisible();
        pane = fen.getHtmlPane();
        HTMLDocument doc = (HTMLDocument) pane.getDocument();
        StyleSheet styleSheet = doc.getStyleSheet();
        styleSheet.addRule("h1 { font-size : x-large;"
                + "color : #33628c;"
                + "border-bottom : 1px solid;"
                + "padding-bottom : 4 px;"
                + "margin : 2px ; }");
        styleSheet.addRule("em { font-size : large; }");
        styleSheet.addRule("span { font-size : large;"
                + "color : green;"
                + "font-style : normal; }");
        styleSheet.addRule("p{ font-size : large; "
                + "margin-top: 2px;}");
        pane.setDocument(doc);
    }

    /**
     *
     * @return @throws Exception
     */
    @Override
    protected String doInBackground() throws Exception {
        if (liste == null) {
            cancel(true);
        }
        int n = liste.getSelectedIndex();
        if (n < 0) {
            return null;
        }
        Entree entree = ((ListeModele) liste.getModel()).getListeEntrees().get(n);
        if (entree == null) {
            return null;
        }
        String mot = entree.getMot();
        String radical = entree.getRadical();
        String variante = entree.getVariante();
        String traduction = entree.getTraduction();
        String message = "<h1>" + mot + "</h1>";
        if (radical != null && variante != null) {
            message += "<span>Radical : </span><em>" + radical
                    + "</em> | <span>Variante : </span><em>" + variante + "</em>";
        } else if (radical != null) {
            message += "<span>Radical : </span><em>" + radical + "</em>";
        } else if (variante != null) {
            message += "<span>Variante : </span><em>" + variante + "</em>";
        }
        message += "<p>" + traduction + "</p>";
        return message;
    }

    /**
     *
     */
    @Override
    protected void done() {
        try {
            pane.setText(get());
            int n = liste.getSelectedIndex();
            liste.ensureIndexIsVisible(n);
        } catch (InterruptedException | ExecutionException ex) {            
            afficheException(ex.getMessage());
        }
    }
}
