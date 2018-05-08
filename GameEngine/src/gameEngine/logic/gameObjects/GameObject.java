/**
 * BatBall2
 * Created by Philip on 07/09/2016 at 19:43.
 */
package gameEngine.logic.gameObjects;

import gameEngine.GameEngine;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public abstract class GameObject {

    protected final String name;
    protected int x, y, width, height, speedX, speedY;
    protected final Random random;
    protected int baseSpeed;
    protected final float initSpeed;
    protected Color color;
    protected final ObjectHandler objectHandler;

    protected boolean shouldRender;

    public GameObject(String name, int x, int y, int width, int height, int speedX, int speedY, int baseSpeed, Color color, boolean shouldRender, ObjectHandler objectHandler) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speedX = speedX;
        this.speedY = speedY;
        this.baseSpeed = baseSpeed;
        this.initSpeed = baseSpeed;
        this.color = color;
        this.shouldRender = shouldRender;
        this.objectHandler = objectHandler;
        random = new Random();
    }

    public abstract void render(Graphics g);

    public abstract void update();

    protected void move() {
        x += speedX * baseSpeed;
        y += speedY * baseSpeed;
    }

    protected void walls() {
        if (y <= 0 || y >= GameEngine.getInstance().getHeight() - 32)
            speedY *= -1;

        if (x <= 0 || x >= GameEngine.getInstance().getWidth() - 32)
            speedX *= -1;
    }

    protected void remove() {
        if (y > GameEngine.getInstance().getHeight()) {
            objectHandler.removeObject(this);
        }
    }

    protected int clampToWindow(int var, int min, int max) {
        if (var >= max) return max;
        else if (var <= min) return min;
        else return var;
    }

    public Collision collision(GameObject object) {
        return Collision.getCollision(this, object);
    }


    public void updateSpeed() {
        if (speedX != 0 && speedY != 0) {
            speedX = (speedX / Math.abs(speedX)) * baseSpeed;
            speedY = (speedY / Math.abs(speedY)) * baseSpeed;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getBaseSpeed() {
        return baseSpeed;
    }
}