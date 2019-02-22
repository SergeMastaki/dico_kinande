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

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import serge.mastaki.vue.HtmlPane;

/**
 *
 * @author Serge
 */
public class Util {

    /**
     * Convertit une chaîne en une ligne en une chaine multiligne
     *
     * @param str
     * @param n
     * @return String
     */
    public static String toMultiLine(String str, int n) {
        n = n < 10 ? 10 : n;
        String[] ch;
        ch = str.replaceAll("(\\s)+", " ").split(" ");
        StringBuffer strbuf = new StringBuffer();
        int i = 0;
        for (String ch1 : ch) {
            i += ch1.length();
            if (i < n) {
                strbuf = strbuf.append(ch1);
                strbuf = strbuf.append(" ");
                i++;
            } else {
                strbuf = strbuf.append('\n');
                while (ch1.length() > n) {
                    String ch2 = ch1.substring(0, n);
                    ch1 = ch1.substring(n);
                    strbuf.append(ch2);
                    strbuf.append('\n');
                }
                strbuf = strbuf.append(ch1);
                i = ch1.length();
                strbuf = strbuf.append(" ");
                i++;
            }
        }
        return strbuf.toString();
    }

    /**
     *
     * @param texte
     * @return
     */
    public static String enleverAccents(String texte) {
        return texte == null ? null : Normalizer.normalize(texte, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public static String lire(String fichier) throws IOException {
        BufferedReader inputStream = null;
        StringBuilder builder = new StringBuilder();
        try {
            inputStream = new BufferedReader(new FileReader(fichier));
            String line;
            while ((line = inputStream.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return builder.toString();
    }
    
    public static void afficheException(String message){
        message = "<html><body>" + message + "</body></html>";
        HtmlPane htmlPane = new HtmlPane();
        htmlPane.setText(message);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setViewportView(htmlPane);
        scrollPane.setPreferredSize(new Dimension(295, 185));
        JOptionPane.showMessageDialog(null, scrollPane, "Exception levée!",
                JOptionPane.ERROR_MESSAGE);
    }
}
