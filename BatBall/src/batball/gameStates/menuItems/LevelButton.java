/**
 * BatBall2
 * Created by Philip on 11/09/2016 at 18:05.
 */
package batball.gameStates.menuItems;

import batball.BatBall;
import batball.gameStates.Play;
import gameEngine.graphics.menuItems.MenuButton;
import gameEngine.io.input.MouseInput;

import java.awt.Graphics;
import java.awt.Rectangle;

public class LevelButton extends MenuButton {

    private final int level;
    private boolean unlocked;

    public LevelButton(int x, int y, int width, int height, int level, Play play) {
        super(x, y, width, height, String.valueOf(level), play, null);
        this.level = level;
        this.unlocked = BatBall.getProgress() >= level;
    }

    public LevelButton(int x, int y, int width, int height, int level, String text) {
        super(x, y, width, height, text, BatBall.getInstance().getPlay(), null);
        this.level = level;
        this.unlocked = true;
    }

    @Override
    public void update() {
        if(unlocked) {
            mouseOver = new Rectangle(x, y, width, height).intersects(new Rectangle(MouseInput.getX(), MouseInput.getY(), 1, 1));
            if (mouseOver && MouseInput.LMBIsDown()) {
                MouseInput.setLMB(false);
                ((Play) state).setLevel(level);
                BatBall.getInstance().setGameState(state);
            }
        }
        else {
            unlocked = BatBall.getProgress() >= level;
        }
    }

    @Override
    public void render(Graphics g) {
        if(unlocked)
            super.render(g);
    }

}
