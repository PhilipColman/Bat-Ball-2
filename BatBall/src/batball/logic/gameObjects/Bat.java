/**
 * BatBall2
 * Created by Philip on 10/09/2016 at 00:19.
 */
package batball.logic.gameObjects;

import batball.BatBall;
import gameEngine.logic.gameObjects.GameObject;
import gameEngine.logic.gameObjects.ObjectHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import static gameEngine.io.input.KeyInput.isKeyDown;

public class Bat extends GameObject {

    public Bat(ObjectHandler objectHandler) {
        super("bat", 0, 0, 200, 20, 0, 0, 1, Color.gray, true, objectHandler);
        x = (BatBall.getWidth() >> 1) - (width >> 1);
        y = BatBall.getHeight() - 100 - height;
    }

    @Override
    public void update() {

        //todo make user config key
        speedX = (isKeyDown(KeyEvent.VK_A) || isKeyDown(KeyEvent.VK_LEFT)) ? (-baseSpeed) :
                ((isKeyDown(KeyEvent.VK_D) || isKeyDown(KeyEvent.VK_RIGHT) ? baseSpeed : 0));
        move();
        x = clampToWindow(x, 32, BatBall.getWidth() - width - 32);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.lightGray);
        g.drawRect(x, y, width, height);
    }
}
