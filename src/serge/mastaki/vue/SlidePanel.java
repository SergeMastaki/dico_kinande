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

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeSupport;
import javax.swing.*;

/**
 *
 * @author Serge Mastaki
 */
public class SlidePanel extends JPanel {

    private Component composant;
    private Timer timer;
    private boolean running = false;
    private final PropertyChangeSupport pcs;
    public static final int GAUCHE = -1;
    public static final int DROITE = 1;
    

    SlidePanel() {
        setLayout(null);
        pcs = new PropertyChangeSupport(this);
        timer = new Timer(0, null);
        addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                composant.setBounds(composant.getX(), composant.getY(), getWidth(), getHeight());
            }
        });
    }

    public void slide(final int dir, int delai) throws IllegalArgumentException {
        if (dir != GAUCHE && dir != DROITE) {
            throw new IllegalArgumentException("Direction invalide : "+ dir);
        }
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                composant.setLocation(composant.getX() + dir, composant.getY());
                if (composant.getX() == (dir - 1) / 2 * getWidth()) {
                    ((Timer) e.getSource()).stop();
                    running = false;
                    pcs.firePropertyChange("running", true, false);
                }

            }
        };
        timer = new Timer(delai, actionListener);
        timer.start();
        running = true;
        pcs.firePropertyChange("running", false, true);
    }

    public Timer getTimer() {
        return timer;
    }

    @Override
    public void add(Component comp, Object constraints) {
        composant = comp;
        composant.setBounds(-getWidth(), 0, getWidth(), getHeight());
        super.add(composant, constraints);
        validate();
    }

    public boolean isRunning() {
        return running;
    }

    public PropertyChangeSupport getPcs() {
        return pcs;
    }

    public Component getComposant() {
        return composant;
    }

}
