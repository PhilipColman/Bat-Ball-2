/**
 * BatBall2
 * Created by Philip on 11/09/2016 at 17:33.
 */
package batball.gameStates;

import batball.BatBall;
import batball.gameStates.menuItems.LevelButton;
import batball.logic.Level;
import gameEngine.gameStates.BaseGameState;
import gameEngine.graphics.menuItems.MenuButton;
import gameEngine.graphics.menuItems.MenuItem;
import gameEngine.graphics.menuItems.MenuLabel;
import gameEngine.lang.Lang;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class End extends BaseGameState {

    private boolean win;

    private List<MenuItem> items = new ArrayList<>();

    public End(boolean win, int level, int score, String time, Play play) {
        this.win = win;
        int width = 400;
        int height = 64;
        int x = BatBall.getWidth() / 2 - (width / 2);

        items.add(new MenuLabel(x, 60, width, height, win ? Lang.get("LEVEL_COMPLETE") : Lang.get("GAME_OVER")));

        int y = BatBall.getHeight() / 2 - 100;

        if (win) {
            items.add(new MenuLabel(x, y, width, height, String.format("%s: %d", Lang.get("YOUR_SCORE"), score)));
            items.add(new MenuLabel(x, y + 100, width, height, String.format("%s: %s", Lang.get("YOUR_TIME"), time)));

            items.add(new LevelButton(Level.levelExists(level + 1) ? x - width / 2 - 50 : x, y + 200, width, height, level, Lang.get("PLAY_AGAIN")));
            if (Level.levelExists(level + 1))
                items.add(new LevelButton(x + width / 2 + 50, y + 200, width, height, level + 1, Lang.get("NEXT_LEVEL")));
        } else {
            items.add(new LevelButton(x, y + 200, width, height, level, Lang.get("TRY_AGAIN")));
        }
        items.add(new MenuButton(x - width / 2 - 50, y + 300, width, height, Lang.get("MAIN_MENU"), BatBall.getInstance().getMainMenu(), BatBall.getInstance()::setGameState));
        items.add(new MenuButton(x + width / 2 + 50, y + 300, width, height, Lang.get("SELECT_LEVEL"), BatBall.getInstance().getLevels(), BatBall.getInstance()::setGameState));
    }

    @Override
    public void update() {
        items.forEach(MenuItem::update);
    }

    @Override
    public void render(Graphics g) {
        for (MenuItem item : items) {
            item.render(g);
        }
    }
}
