/**
 * BatBall2
 * Created by Philip on 08/09/2016 at 23:08.
 */
package gameEngine.logic.gameObjects;

import java.awt.Graphics;
import java.util.*;

import static gameEngine.GameEngine.log;

public abstract class ObjectHandler {

    protected ArrayList<GameObject> objects = new ArrayList<>();

    public void addObject(GameObject object) {
        objects.add(object);
        log.info("Object Added: "+object.getName());
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
        log.info("Object Removed: "+object.getName());
    }

    public boolean contains(GameObject object) {
        return objects.contains(object);

    }

    //TODO Make this better
    public void update() {
        if (!objects.isEmpty()) {

            Iterator<GameObject> gameObjectIterator = ((ArrayList<GameObject>)objects.clone()).iterator();
            while (gameObjectIterator.hasNext()){
                gameObjectIterator.next().update();
            }

            for (int i = 0; i < objects.size(); i++) {
                objects.get(i).update();
            }
        }
    }

    public void render(Graphics g) {
        if (!objects.isEmpty()) {
            for (GameObject object : objects) {
                object.render(g);
            }
        }
    }

    public int getSize() {
        return objects.size();
    }

    public GameObject getObject(int i) {
        return objects.get(i);
    }
}
