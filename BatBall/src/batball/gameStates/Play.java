/**
 * BatBall2
 * Created by Philip on 10/09/2016 at 00:04.
 */
package batball.gameStates;

import batball.BatBall;
import batball.logic.Level;
import gameEngine.GameEngine;
import gameEngine.gameStates.BaseGameState;
import gameEngine.io.input.KeyInput;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class Play extends BaseGameState {

    private Level level;

    public Play(){
        level = new Level(1, this);
    }

    @Override
    public void update() {
        level.update();

        if(GameEngine.isDebugEnabled()){
            if(KeyInput.isKeyDown(KeyEvent.VK_C)) {
                BatBall.getInstance().setEnd(new End(true, level.getLevel(), level.getScore(), level.getHud(), this));
                if(BatBall.getProgress() <= level.getLevel()){
                    BatBall.setProgress(level.getLevel() + 1);
                    BatBall.serialize();
                }
                BatBall.getInstance().setGameState(BatBall.getInstance().getEnd());
            }
        }

    }

    @Override
    public void render(Graphics g) {
        level.render(g);
    }

    @Override
    public boolean canBePaused() {
        return true;
    }

    public void setLevel(int level) {
        this.level = new Level(level, this);
    }
}
