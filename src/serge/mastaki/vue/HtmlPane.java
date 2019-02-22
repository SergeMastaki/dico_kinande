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
package serge.mastaki.vue;

import java.awt.Color;
import java.awt.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import static serge.mastaki.utilitaire.Util.afficheException;

/**
 *
 * @author Serge Mastaki
 */
public class HtmlPane extends JEditorPane implements Serializable {

    /**
     *
     */
    public HtmlPane() {
        setContentType("text/html");
        setEditable(false);
        setBorder(null);
        HTMLEditorKit kit = new HTMLEditorKit();
        final HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
        StyleSheet styleSheet = doc.getStyleSheet();
        Font font = UIManager.getFont("EditorPane.font");
        Color bgColor = UIManager.getColor("EditorPane.background");
        String color = "rgb(" + bgColor.getRed() + "," + bgColor.getGreen()
                + "," + bgColor.getBlue() + ")";
        StringBuilder style = new StringBuilder();
        style.append("font-family:").append(font.getFamily()).append(";");
        style.append("font-weight:").append((font.isBold()) ? "bold;" : "normal;");
        style.append("font-size:").append(font.getSize()).append("pt;");
        style.append("background-color:").append(color).append(";");
        styleSheet.addRule("body {" + style.toString() + "}");
        setDocument(doc);
        addHyperlinkListener(new HyperlinkListener() {

            @Override
            public void hyperlinkUpdate(HyperlinkEvent evt) {
                if (evt.getEventType().equals(HyperlinkEvent.EventType.ACTIVATED)) {
                    Desktop desktop = Desktop.getDesktop();
                    String link = evt.getDescription();
                    if (link.contains("@") && desktop.isSupported(Desktop.Action.MAIL)) {
                        String mailTo = link;
                        URI uriMailTo;
                        try {
                            if (mailTo.length() > 0) {
                                uriMailTo = new URI("mailto", mailTo, null);
                                desktop.mail(uriMailTo);
                            } else {
                                desktop.mail();
                            }
                        } catch (IOException | URISyntaxException ex) {
                            afficheException(ex.getMessage());
                        }
                    } else if (desktop.isSupported(Desktop.Action.BROWSE)) {
                        try {
                            URI uri;
                            uri = new URI(link);
                            desktop.browse(uri);
                        } catch (URISyntaxException | IOException ex) {
                            afficheException(ex.getMessage());
                        }
                    }
                }
            }
        });
    }

}
