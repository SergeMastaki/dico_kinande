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
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Serge Mastaki
 */
public class JListRollover extends JList {
    protected int mouseOver;
    private static Color listBackground,listForeground, listSelectionBackground, listSelectionForeground,listMouseOver;
    static {
        listBackground = Color.WHITE;
        listForeground = Color.BLACK;
        listSelectionBackground = new Color(57, 105, 138);
        listSelectionForeground = Color.WHITE;
        listMouseOver = new Color(57, 105, 138,96);
    }

    public JListRollover() {
        mouseOver = -1;
        setCellRenderer(new JListRolloverCellRenderer());
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseOver = locationToIndex(new Point(e.getX(), e.getY()));
                repaint();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                mouseOver = -1;
                repaint();
            }
        });
    }

    public static Color getListBackground() {
        return listBackground;
    }

    public static void setListBackground(Color listBackground) {
        JListRollover.listBackground = listBackground;
    }

    public static Color getListForeground() {
        return listForeground;
    }

    public static void setListForeground(Color listForeground) {
        JListRollover.listForeground = listForeground;
    }

    public static Color getListSelectionBackground() {
        return listSelectionBackground;
    }

    public static void setListSelectionBackground(Color listSelectionBackground) {
        JListRollover.listSelectionBackground = listSelectionBackground;
    }

    public static Color getListSelectionForeground() {
        return listSelectionForeground;
    }

    public static void setListSelectionForeground(Color listSelectionForeground) {
        JListRollover.listSelectionForeground = listSelectionForeground;
    }

    public static Color getListMouseOver() {
        return listMouseOver;
    }

    public static void setListMouseOver(Color listMouseOver) {
        JListRollover.listMouseOver = listMouseOver;
    }

    class JListRolloverCellRenderer extends DefaultListCellRenderer implements
            ListCellRenderer<Object> {

        public JListRolloverCellRenderer() {
            super();
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            JLabel addHighlight = (JLabel) super.getListCellRendererComponent(list,
                    value, index, isSelected, cellHasFocus);
            if (index == mouseOver && !isSelected) {
                addHighlight.setForeground(listForeground);
                addHighlight.setBackground(listMouseOver);
            } else if (isSelected) {
                addHighlight.setForeground(listSelectionForeground);
                addHighlight.setBackground(listSelectionBackground);
            }
            else {                
                addHighlight.setForeground(listForeground);
                addHighlight.setBackground(listBackground);
            }
            return addHighlight;
        }
    }
}
