/**
 * BatBall2
 * Created by Philip on 09/09/2016 at 20:08.
 */
package gameEngine.gameStates;

import java.awt.Graphics;

public abstract class BaseGameState {

    public abstract void update();

    public abstract void render(Graphics g);

    public boolean isMainMenu() {
        return false;
    }

    public boolean canBePaused() {
        return false;
    }
}