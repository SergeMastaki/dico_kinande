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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.SplashScreen;

/**
 *
 * @author Serge
 */
public class Splash {

    private static SplashScreen splash;

    static void dessinerSplash(Graphics2D g, int frame) {
        final String[] comps = {"", ".", "..", "..."};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(275, 90, 200, 50);
        g.setPaintMode();        
        Color c1 = new Color(0,0,255);
        Color c2 = new Color(0,0,255,128);
        Color c3 = new Color(0,0,255,64);
        g.setColor(c1);
        Font font = new Font("Dialog", Font.PLAIN, 16);
        g.setFont(font);
        g.drawString("Chargement en cours" + comps[(frame / 4) % 4], 280, 120);
        g.drawRect(278, 124, 163, 13);
        g.fillRect(280 + (frame * 10) % 160, 126, 10, 10);
        g.setColor(c2);
        if ((frame - 1) % 20 >= 0) {
            g.fillRect(280 + (10 * (frame - 1)) % 160, 126, 10, 10);
        }
        g.setColor(c3);
        if ((frame - 2) % 20 >= 0) {
            g.fillRect(280 + (10 * (frame - 2)) % 160, 126, 10, 10);
        }
    }

    /**
     *
     */
    public static void init() {
        splash = SplashScreen.getSplashScreen();
        if (splash == null) {
            return;
        }
        Graphics2D g = (Graphics2D) splash.createGraphics();
        if (g == null) {
            return;
        }
        int i = 0;
        while (splash.isVisible()) {
            dessinerSplash(g, i);
            splash.update();
            i++;
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     *
     * @return
     */
    public static SplashScreen getSplash() {
        return splash;
    }

}
