/**
 * BatBall2
 * Created by Philip on 11/09/2016 at 15:13.
 */
package gameEngine.logic.gameObjects;

import java.awt.Rectangle;

public enum Collision {
    TOP,
    BOTTOM,
    LEFT,
    RIGHT,
    INSIDE,
    NONE;

    public static Collision getCollision(GameObject object1, GameObject object2){

            Rectangle a = new Rectangle(object1.getX(), object1.getY(), object1.getWidth(), 1);
            Rectangle b = new Rectangle(object2.getX(), object2.getY() + object2.getHeight(), object2.getWidth(), 1);
            if (a.intersects(b)) {
                return TOP;
            }
            a = new Rectangle(object1.getX(), object1.getY() + object1.getHeight(), object1.getWidth(), 1);
            b = new Rectangle(object2.getX(), object2.getY(), object2.getWidth(), 1);
            if (a.intersects(b)) {
                return BOTTOM;
            }
            a = new Rectangle(object1.getX(), object1.getY(), 1, object1.getHeight());
            b = new Rectangle(object2.getX() + object2.getWidth(), object2.getY(), 1, object2.getHeight());
            if (a.intersects(b)) {
                return LEFT;
            }
            b = new Rectangle(object1.getX() + object1.getWidth(), object1.getY(), 1, object1.getHeight());
            a = new Rectangle(object2.getX(), object2.getY(), 1, object2.getHeight());
            if (a.intersects(b)) {
                return RIGHT;
            }
            if(new Rectangle(object1.getX(), object1.getY(), object1.getWidth(), object1.getHeight()).intersects(new Rectangle(object2.getX(), object2.getY(), object2.getWidth(), object2.getHeight())))
            return INSIDE;

        return NONE;
    }

}
