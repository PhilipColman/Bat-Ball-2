/**
 * BatBall2
 * Created by Philip on 09/09/2016 at 20:23.
 */
package gameEngine.graphics.menuItems;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuLabel extends MenuItem {

    public MenuLabel(int x, int y, int width, int height, String text) {
        super(x, y, width, height, text);
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font("arial", Font.PLAIN, 50));
        g.setColor(Color.white);
        g.drawString(text, x - g.getFontMetrics().stringWidth(text) / 2 + (width / 2), y);
    }
}
