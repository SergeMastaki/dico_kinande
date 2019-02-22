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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.IOException;
import java.text.MessageFormat;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import static serge.mastaki.utilitaire.Util.afficheException;
import static serge.mastaki.utilitaire.Util.lire;
import serge.mastaki.vue.Fenetre;
import serge.mastaki.vue.HtmlPane;

/**
 * Cette classe gère les événements de la barre des menus
 *
 * @author Serge
 */
public class MenuActionListener implements ActionListener {

    private final Fenetre fen;

    /**
     *
     * @param fen
     */
    public MenuActionListener(Fenetre fen) {
        this.fen = fen;
    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "print":
                imprimer();
                break;
            case "quit":
                System.exit(0);
                break;
            case "copy":
                copier();
                break;
            case "selectAll":
                selectTout();
                break;
            case "softwareLicense":
                ouvrirSoftLicDialog();
                break;
            case "dictionaryLicense":
                ouvrirDicLicDialog();
                break;
            case "welcome":
                fen.afficherPageAccueil();
                break;
            case "about":
                ouvrirInfoDialog();
                break;
        }
    }

    private void copier() {
        fen.getHtmlPane().copy();
    }

    private void selectTout() {
        fen.getHtmlPane().requestFocusInWindow();
        fen.getHtmlPane().selectAll();
    }

    private void imprimer() {
        MessageFormat headerFormat = new MessageFormat(
                "Dictionnaire kinande-français");
        MessageFormat footerFormat = new MessageFormat(
                "<html><body style=\"text-align:center\">"
                + "Copyright © 2016 Serge Mastaki<br>Musée royal de l&#39;Afrique"
                + " centrale, Tervuren (Belgique) 2012</body></html>");
        try {
            fen.getHtmlPane().print(headerFormat, footerFormat);
        } catch (PrinterException ex) {            
            afficheException(ex.getMessage());
        }
    }

    private void ouvrirInfoDialog() {
        String info;
        info = "<html><body>"
                + "<b>Dictionnaire kinande-français version 1.0</b><br><br>"
                + "Ce logiciel a pour but de promouvoir la culture Nande "
                + "au travers de sa langue. Il est basé sur l'ouvrage des "
                + "Professeurs <font color=blue>Kambale Kavutirwaki</font>"
                + " et <font color=blue>Ngessimo M. Mutaka</font>.<br>"
                + "<br><i>Pour soutenir cette œuvre, apporter des suggestions"
                + " ou signaler un dysfonctionnement,contacter:</i>"
                + "<br><a href=\"serge.mastaki@gmail.com\">"
                + "serge.mastaki@gmail.com</a></body></html>";
        HtmlPane htmlPane = new HtmlPane();
        htmlPane.setText(info);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        scrollPane.setViewportView(htmlPane);
        scrollPane.setPreferredSize(new Dimension(295, 185));
        JOptionPane.showMessageDialog(fen, scrollPane, "A propos",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void ouvrirSoftLicDialog() {
        try {
            String licence_en = lire("licence_en.txt");
            String licence_fr = lire("licence_fr.txt");
            JTextArea zTexte_en = new JTextArea(licence_en);
            JTextArea zTexte_fr = new JTextArea(licence_fr);
            zTexte_en.setEditable(false);
            zTexte_fr.setEditable(false);
            zTexte_en.setLineWrap(true);
            zTexte_fr.setLineWrap(true);
            zTexte_en.setWrapStyleWord(true);
            zTexte_fr.setWrapStyleWord(true);
            JScrollPane scrollPane_en = new JScrollPane();
            JScrollPane scrollPane_fr = new JScrollPane();
            scrollPane_en.setViewportView(zTexte_en);
            scrollPane_fr.setViewportView(zTexte_fr);
            scrollPane_en.setPreferredSize(new Dimension(450, 450));
            scrollPane_fr.setPreferredSize(new Dimension(450, 450));
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.add("Anglais", scrollPane_en);
            tabbedPane.add("Français", scrollPane_fr);
            tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
            HtmlPane htmlPane = new HtmlPane();
            String info = "<html><body>Pour plus d’informations à ce sujet, et "
                    + "comment appliquer la GPL GNU, consultez:<br>"
                    + "<a href=\"http://www.gnu.org/licenses/\">"
                    + "http://www.gnu.org/licenses/</a></body></html>";
            htmlPane.setText(info);
            JScrollPane scrollPane = new JScrollPane();
            scrollPane.setBorder(null);
            scrollPane.setViewportView(htmlPane);
            JPanel pane = new JPanel(new BorderLayout());
            pane.add(tabbedPane, BorderLayout.CENTER);
            pane.add(htmlPane, BorderLayout.PAGE_END);
            JOptionPane.showMessageDialog(fen, pane, "Licence du logiciel",
                    JOptionPane.PLAIN_MESSAGE);
        } catch (IOException ex) {            
            afficheException(ex.getMessage());
        }
    }

    private void ouvrirDicLicDialog() {
        String licence = "© Musée royal de l'Afrique centrale, Tervuren (Belgique)"
                + " 2012 www.africamuseum.be \n\nToute citation doit être référencée."
                + " \nToute reproduction de cette publication à fin autre que privée"
                + " ou éducative, que ce soit par impression, photocopie ou tout "
                + "autre moyen est interdite sans l'autorisation écrite préalable"
                + " du Service des Publications du MRAC, Leuvensesteenweg 13, 3080"
                + " Tervuren, Belgique.";
        JTextArea zTexte = new JTextArea(licence);
        zTexte.setEditable(false);
        zTexte.setLineWrap(true);
        zTexte.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 128, 255), 1, true));
        scrollPane.setViewportView(zTexte);
        scrollPane.setPreferredSize(new Dimension(300, 160));
        JOptionPane.showMessageDialog(fen, scrollPane, "Licence du dictionnaire",
                JOptionPane.PLAIN_MESSAGE);
    }

}
