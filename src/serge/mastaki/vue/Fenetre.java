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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.EventHandler;
import java.io.IOException;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.Document;
import net.miginfocom.swing.MigLayout;
import serge.mastaki.controle.BtnListener;
import serge.mastaki.controle.ListeListener;
import serge.mastaki.controle.MenuActionListener;
import serge.mastaki.controle.ZoneDeRechercheListener;
import serge.mastaki.modele.ListeModele;
import static serge.mastaki.utilitaire.Util.afficheException;

/**
 *
 * @author Serge Mastaki
 */
public class Fenetre extends JFrame {

    private boolean modeAvance = false;
    private BtnListener btnListener;
    private HtmlPane htmlPane;
    private JButton btnRech;
    private JButton btnRechAv;
    private JButton btnRetour;
    private JLabel labelRechAv;
    private JListRollover listeFr;
    private JListRollover listeKin;
    private JListRollover listeRechAv;
    private JMenu menuAide;
    private JMenu menuEdition;
    private JMenu menuFichier;
    private JMenuBar barreDeMenu;
    private JMenuItem smPgAccueil;
    private JMenuItem smAPropos;
    private JMenuItem smCopier;
    private JMenuItem smImprimer;
    private JMenuItem smLicenceDico;
    private JMenuItem smLicenceLogiciel;
    private JMenuItem smQuitter;
    private JMenuItem smSelectTout;
    private JPanel panRechAv;
    private JPopupMenu menuChoix;
    private JScrollPane panDerFr;
    private JScrollPane panDerKin;
    private JScrollPane panDerRechAv;
    private JScrollPane scrollPane;
    private JTabbedPane tabbedPane;
    private JTextField zoneDeRecherche;
    private ListeListener listeListener;
    private MenuActionListener menuActionListener;
    private SlidePanel slidePane;
    private ZoneDeRechercheListener zoneDeRechercheListener;

    public Fenetre() {
        initialiser();
        // Création de l'icone
        setIconImage(getIcone());
        setLookAndFeel();
        pack();
    }

    private void initialiser() {
        // Création des composants
        zoneDeRecherche = new JTextField();
        btnRech = new JButton();
        btnRechAv = new JButton();
        btnRetour = new JButton();
        tabbedPane = new JTabbedPane();
        slidePane = new SlidePanel();
        panDerKin = new JScrollPane();
        listeKin = new JListRollover();
        panDerFr = new JScrollPane();
        listeFr = new JListRollover();
        panDerRechAv = new JScrollPane();
        listeRechAv = new JListRollover();
        panRechAv = new JPanel();
        labelRechAv = new JLabel();
        scrollPane = new JScrollPane();
        htmlPane = new HtmlPane();
        barreDeMenu = new JMenuBar();
        menuFichier = new JMenu();
        smImprimer = new JMenuItem();
        smQuitter = new JMenuItem();
        menuEdition = new JMenu();
        smCopier = new JMenuItem();
        smSelectTout = new JMenuItem();
        menuAide = new JMenu();
        smLicenceLogiciel = new JMenuItem();
        smLicenceDico = new JMenuItem();
        smPgAccueil = new JMenuItem();
        smAPropos = new JMenuItem();
        menuChoix = new JPopupMenu();

        // Création des écouteurs
        menuActionListener = new MenuActionListener(this);
        listeListener = new ListeListener(this);
        btnListener = new BtnListener(this);
        zoneDeRechercheListener = new ZoneDeRechercheListener(this);

        /* Initialise le menu surgissant permettant le choix
         du mode de recherche avancé */
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("ET");
        rbMenuItem.setSelected(true);
        rbMenuItem.setToolTipText("Recherche la correspondance avec tous les mots entrés");
        group.add(rbMenuItem);
        menuChoix.add(rbMenuItem);
        menuChoix.addSeparator();
        rbMenuItem = new JRadioButtonMenuItem("OU");
        rbMenuItem.setToolTipText("Recherche la correspondance avec l'un des mots entrés");
        group.add(rbMenuItem);
        menuChoix.add(rbMenuItem);

        btnRech.setText("Recherche");
        btnRech.setActionCommand("search");
        btnRechAv.setText("Recherche avancée");
        btnRechAv.setActionCommand("advancedSearch");
        btnRetour.setText("Retour");
        btnRetour.setActionCommand("back");

        btnRech.setVisible(!modeAvance);
        btnRetour.setVisible(modeAvance);

        listeKin.setModel(new ListeModele());
        listeKin.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listeKin.setDragEnabled(true);
        panDerKin.setViewportView(listeKin);

        tabbedPane.addTab("kinande", panDerKin);

        listeFr.setModel(new ListeModele());
        listeFr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listeFr.setDragEnabled(true);
        panDerFr.setViewportView(listeFr);

        tabbedPane.addTab("français", panDerFr);
        slidePane.add(tabbedPane, null);

        labelRechAv.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        listeRechAv.setModel(new ListeModele());
        listeRechAv.setDragEnabled(true);
        panDerRechAv.setViewportView(listeRechAv);
        Border bordureLigne = BorderFactory.createLineBorder(new Color(0, 128, 255), 1, true);
        Border bordureVide = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        CompoundBorder bordureCompose = BorderFactory.createCompoundBorder(bordureLigne, bordureVide);
        panRechAv.setBorder(BorderFactory.createTitledBorder(
                bordureCompose,
                "Recherche avancée",
                TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION,
                UIManager.getFont("Label.font"),
                new Color(0, 128, 255)));

        htmlPane.setDragEnabled(true);
        scrollPane.setViewportView(htmlPane);

        menuFichier.setMnemonic('F');
        menuFichier.setText("Fichier");

        smImprimer.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        smImprimer.setMnemonic('I');
        smImprimer.setText("Imprimer ...");
        smImprimer.setActionCommand("print");
        menuFichier.add(smImprimer);

        smQuitter.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
        smQuitter.setMnemonic('Q');
        smQuitter.setText("Quitter");
        smQuitter.setActionCommand("quit");
        menuFichier.add(smQuitter);

        barreDeMenu.add(menuFichier);

        menuEdition.setMnemonic('E');
        menuEdition.setText("Edition");

        smCopier.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
        smCopier.setMnemonic('C');
        smCopier.setText("Copier");
        smCopier.setActionCommand("copy");
        menuEdition.add(smCopier);

        smSelectTout.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
        smSelectTout.setMnemonic('S');
        smSelectTout.setText("Sélectionner tout");
        smSelectTout.setActionCommand("selectAll");
        menuEdition.add(smSelectTout);

        barreDeMenu.add(menuEdition);

        menuAide.setMnemonic('A');
        menuAide.setText("Aide");

        smLicenceLogiciel.setText("Licence (logiciel)");
        smLicenceLogiciel.setActionCommand("softwareLicense");
        menuAide.add(smLicenceLogiciel);

        smLicenceDico.setText("Licence (dictionnaire)");
        smLicenceDico.setActionCommand("dictionaryLicense");
        menuAide.add(smLicenceDico);
        menuAide.addSeparator();

        smPgAccueil.setMnemonic('P');
        smPgAccueil.setText("Page d'accueil");
        smPgAccueil.setActionCommand("welcome");
        menuAide.add(smPgAccueil);

        smAPropos.setMnemonic('P');
        smAPropos.setText("A propos");
        smAPropos.setActionCommand("about");
        menuAide.add(smAPropos);

        barreDeMenu.add(menuAide);

        setJMenuBar(barreDeMenu);

        // Définit le gestionnaire de disposition et ajoute les composants à la fenetre
        Container contentPane = getContentPane();
        contentPane.setLayout(new MigLayout());
        contentPane.add(zoneDeRecherche, "width 150, height 30, pos 7 7, id zText");
        contentPane.add(btnRech, "width 90, height 30, pos (zText.w + 30) zText.y, id btnRech");
        contentPane.add(btnRetour, "width 60, height 30, pos (zText.w + 30) zText.y");
        contentPane.add(btnRechAv, "width 140, height 30, pos (btnRech.x2 + zText.x) btnRech.y");
        contentPane.add(slidePane, "width 165!, pos zText.x editor.y n editor.y2");
        contentPane.add(scrollPane, "pos btnRech.x (btnRech.y2+2*zText.y) (container.x2-zText.x) (container.y2-zText.y), id editor");

        //  Ajoute les composants au panneau de recherche avancé
        panRechAv.setLayout(new BorderLayout());
        panRechAv.add(labelRechAv, BorderLayout.PAGE_START);
        panRechAv.add(panDerRechAv, BorderLayout.CENTER);

        // Définit les dimensions de la fenetre
        setMinimumSize(new Dimension(450, 300));
        setPreferredSize(new Dimension(600, 400));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Dictionnaire kinande-français");

        // Enregistre les différents écouteurs des composants
        zoneDeRecherche.addKeyListener(zoneDeRechercheListener);
        listeKin.addMouseListener(listeListener);
        listeFr.addMouseListener(listeListener);
        listeRechAv.addMouseListener(listeListener);
        smImprimer.addActionListener(menuActionListener);
        smQuitter.addActionListener(menuActionListener);
        smCopier.addActionListener(menuActionListener);
        smSelectTout.addActionListener(menuActionListener);
        smLicenceLogiciel.addActionListener(menuActionListener);
        smLicenceDico.addActionListener(menuActionListener);
        smPgAccueil.addActionListener(menuActionListener);
        smAPropos.addActionListener(menuActionListener);
        slidePane.getPcs().addPropertyChangeListener("running", btnListener);
        btnRech.addActionListener(btnListener);
        btnRechAv.addActionListener(btnListener);
        btnRetour.addActionListener(btnListener);
        btnRechAv.addActionListener(EventHandler.create(ActionListener.class, this, "modeAvance"));
        btnRetour.addActionListener(EventHandler.create(ActionListener.class, this, "modeSimple"));
    }

    public void modeAvance() {
        if (!modeAvance) {
            modeAvance = !modeAvance;
            btnRech.setVisible(!modeAvance);
            btnRetour.setVisible(modeAvance);
        }
    }

    public void modeSimple() {
        if (modeAvance) {
            modeAvance = !modeAvance;
            btnRech.setVisible(!modeAvance);
            btnRetour.setVisible(modeAvance);
        }
    }

    private Image getIcone() {
        URL imgURL = ClassLoader.getSystemResource("images/icone.png");
        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            return null;
        }
    }

    /**
     * Définit "nimbus" comme look and feel de la fenêtre s'il est disponible.
     * Dans le cas contraire, le look and feel du système d'exploitation est
     * attribué
     */
    private void setLookAndFeel() {
        boolean nimbusDispo = false;
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    nimbusDispo = true;
                    break;
                }
            }
            if (!nimbusDispo) {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            afficheException(ex.getMessage());
        }
        /* Ajuste la taille min de l'ascenseur de la barre de défilement en vue
         d'éviter sa disparition (cas de Nimbus) */
        UIManager.getLookAndFeelDefaults().put("ScrollBar.minimumThumbSize", new Dimension(30, 30));
        // Mise à jour des différents groupes de composants graphiques
        SwingUtilities.updateComponentTreeUI(this);
        SwingUtilities.updateComponentTreeUI(menuChoix);
        SwingUtilities.updateComponentTreeUI(panRechAv);
    }

    public JButton getBtnRech() {
        return btnRech;
    }

    public JButton getBtnRechAv() {
        return btnRechAv;
    }

    public JListRollover getListeFr() {
        return listeFr;
    }

    public JListRollover getListeKin() {
        return listeKin;
    }

    public HtmlPane getHtmlPane() {
        return htmlPane;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public JTextField getZoneDeRecherche() {
        return zoneDeRecherche;
    }

    public boolean isModeAvance() {
        return modeAvance;
    }

    public SlidePanel getSlidePane() {
        return slidePane;
    }

    public JPanel getPanRechAv() {
        return panRechAv;
    }

    public JListRollover getListeRechAv() {
        return listeRechAv;
    }

    public JLabel getLabelRechAv() {
        return labelRechAv;
    }

    /**
     *
     * @return
     */
    public JListRollover getListeVisible() {
        if (slidePane.getComposant() == tabbedPane) {
            int indexTab = tabbedPane.getSelectedIndex();
            if (indexTab == 0) {
                return listeKin;
            } else if (indexTab == 1) {
                return listeFr;
            }
        } else if (slidePane.getComposant() == panRechAv) {
            return listeRechAv;
        }
        return null;
    }

    public JPopupMenu getMenuChoix() {
        return menuChoix;
    }

    public JScrollPane getPanDerRechAv() {
        return panDerRechAv;
    }

    public void afficherPageAccueil() {
        URL welcomeURL = ClassLoader.getSystemResource("welcome.html");
        if (welcomeURL != null) {
            try {
                Document doc = htmlPane.getDocument();
                doc.putProperty(Document.StreamDescriptionProperty, null);
                htmlPane.setPage(welcomeURL);
            } catch (IOException ex) {
                afficheException(ex.getMessage());
            }
        } else {
            System.out.println("Impossible d'afficher la page d'accueil");
        }
    }

}
