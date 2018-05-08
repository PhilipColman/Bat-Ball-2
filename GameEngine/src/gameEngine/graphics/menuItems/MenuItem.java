/**
 * BatBall2
 * Created by Philip on 09/09/2016 at 20:23.
 */
package gameEngine.graphics.menuItems;

import java.awt.Graphics;

public abstract class MenuItem {

    protected final int x, y, width, height;
    protected final String text;

    MenuItem(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public abstract void update();

    public abstract void render(Graphics g);

}
