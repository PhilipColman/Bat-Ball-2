/**
 * BatBall2
 * Created by Philip on 09/09/2016 at 21:44.
 */
package batball.gameStates;

import gameEngine.gameStates.BaseGameState;

import java.awt.Graphics;

public class Exiting extends BaseGameState {

    @Override
    public void update() {
        //TODO Stop Thread
        System.exit(0);
    }

    @Override
    public void render(Graphics g) {
    }
}
