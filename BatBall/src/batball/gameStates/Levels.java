/**
 * BatBall2
 * Created by Philip on 11/09/2016 at 19:23.
 */
package batball.gameStates;

import batball.BatBall;
import batball.gameStates.menuItems.LevelButton;
import batball.logic.Level;
import gameEngine.gameStates.BaseGameState;

import java.awt.Graphics;

public class Levels extends BaseGameState {

    private LevelButton[] levelButtons;

    public Levels(Play play) {
        levelButtons = new LevelButton[Level.getLevels()];

        int width = (BatBall.getWidth() - (11 * 20)) / 10;
        final int xStart = 20;
        int x = xStart;
        int y = (BatBall.getHeight() >> 1) - ((4 * (width + 20)) >> 1);

        for (int i = 0; i < levelButtons.length; i++) {
            levelButtons[i] = new LevelButton(x, y, width, width, (i + 1), play);
            x += (width + 20);
            if ((i + 1) % 10 == 0) {
                y += (width + 20);
                x = xStart;
            }
        }
    }

    @Override
    public void update() {
        for (LevelButton levelButton : levelButtons) {
            levelButton.update();
        }
    }

    @Override
    public void render(Graphics g) {
        for (LevelButton levelButton : levelButtons) {
            levelButton.render(g);
        }
    }

}
