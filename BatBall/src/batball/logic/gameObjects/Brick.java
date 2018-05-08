/**
 * BatBall2
 * Created by Philip on 11/09/2016 at 02:55.
 */
package batball.logic.gameObjects;

import gameEngine.logic.gameObjects.GameObject;
import gameEngine.logic.gameObjects.ObjectHandler;

import java.awt.Color;
import java.awt.Graphics;

public class Brick extends GameObject{

    private boolean hard;

    public Brick(int x, int y, int width, int height, Color color, boolean hard, ObjectHandler objectHandler) {
        super("brick", x, y, width, height, 0, 0, 0, color, true, objectHandler);
        this.hard = hard;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.black);
        g.drawRect(x, y, width, height);
    }

    @Override
    public void update() {

    }

    public Color getColor(){
        return color;
    }

    public boolean isHard() {
        return hard;
    }

    public void setHard(boolean hard) {
        this.hard = hard;
    }
}
