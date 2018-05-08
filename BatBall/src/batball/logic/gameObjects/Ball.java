/**
 * BatBall2
 * Created by Philip on 09/09/2016 at 00:34.
 */
package batball.logic.gameObjects;

import batball.BatBall;
import batball.logic.Level;
import gameEngine.io.input.KeyInput;
import gameEngine.logic.gameObjects.Collision;
import gameEngine.logic.gameObjects.GameObject;
import gameEngine.logic.gameObjects.ObjectHandler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Ball extends GameObject {

    private boolean docked;
    private final Bat bat;
    private final Level level;

    public Ball(ObjectHandler objectHandler, Bat bat, Level level) {
        super("ball", bat.getX() + (bat.getWidth() >> 1) - 16, bat.getY() - 32, 32, 32, 1, 1, 1, Color.WHITE, true, objectHandler);
        this.bat = bat;
        speedX = 0;
        speedY =0;
        docked = true;
        this.level = level;
        //baseSpeed = random.nextInt(2)+1;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillOval(x, y, width, height);
    }

    @Override
    public void update() {
        if(docked) {
            tryUndock();
        }
        if(docked){
            x = bat.getX() + (bat.getWidth() >> 1) - 16;
        }
        move();
        hitBat();
        hitBrick();
        walls();
        remove();
    }

    private void hitBat() {
        Collision collision = Collision.getCollision(this, bat);
        if(collision.equals(Collision.TOP)||collision.equals(Collision.BOTTOM)/*||collision.equals(Collision.INSIDE)*/) {
            speedY *= -1;
        }
        else if(collision.equals(Collision.LEFT)||collision.equals(Collision.RIGHT)){
            speedX *= -1;
        }
    }

    private void hitBrick() {
        for (int i = 0; i < objectHandler.getSize(); i++) {
            GameObject temp = objectHandler.getObject(i);
                if(temp.getName().equals("brick")) {
                    Collision collision = collision(temp);
                    if(collision != Collision.NONE) {
                        Brick brick = (Brick) objectHandler.getObject(i);
                        if(brick.isHard()) {
                            brick.setHard(false);
                        } else {
                            level.setBricksLeft(level.getBricksLeft() - 1);
                            level.setScore(level.getScore() + 10);
                            objectHandler.removeObject(brick);
                        }
                        if(collision.equals(Collision.TOP)||collision.equals(Collision.BOTTOM)||collision.equals(Collision.INSIDE))
                            speedY *= -1;
                        else if(collision.equals(Collision.LEFT)||collision.equals(Collision.RIGHT))
                            speedX *= -1;
                    }
                }
            }
    }

    private void tryUndock() {
        docked = !KeyInput.isKeyDown(KeyEvent.VK_SPACE);
        if (!docked) {
            speedX = random.nextBoolean() ? 1 : -1 * baseSpeed;
            speedY =  -1 * baseSpeed;
        }
    }

    @Override
    protected void walls() {
        if (y <= 0)
            speedY *= -1;

        if (x <= 0 || x >= BatBall.getWidth() - 32)
            speedX *= -1;
    }

    @Override
    protected void remove() {
        if(y > BatBall.getHeight()) {
            if (level.getBalls() == 1 && level.getLifes() > 0) {
                level.setLifes(level.getLifes() - 1);
                level.setScore(level.getScore() > 50 ? (level.getScore() - 50) : 0);
                objectHandler.addObject(new Ball(objectHandler, bat, level));
            } else if (level.getBalls() > 1)
                level.setBalls(level.getBalls() - 1);
            else {
                level.loss();
            }
            objectHandler.removeObject(this);
        }
    }
}
