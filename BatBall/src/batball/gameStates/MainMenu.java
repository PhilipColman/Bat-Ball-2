/**
 * BatBall2
 * Created by Philip on 09/09/2016 at 20:14.
 */
package batball.gameStates;

import batball.BatBall;
import gameEngine.gameStates.BaseGameState;
import gameEngine.graphics.menuItems.MenuButton;
import gameEngine.graphics.menuItems.MenuItem;
import gameEngine.graphics.menuItems.MenuLabel;
import gameEngine.lang.Lang;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends BaseGameState {

    private final List<MenuItem> items = new ArrayList<>();

    public MainMenu(BatBall batBall, int WIDTH, int HEIGHT) {
        int buttons = 3;
        int width = 400;
        int height = 64;
        int x = WIDTH / 2 - (width >> 1);
        int y = HEIGHT / 2 - ((buttons - 1) * (height + 20) >> 1);

        items.add(new MenuLabel(x, y - 50, width, height, Lang.get("APP_NAME")));

        items.add(new MenuButton(x, y, width, height, Lang.get("BUTTON_PLAY"), batBall.getLevels(), batBall::setGameState));
        y += height + 20;
        items.add(new MenuButton(x, y, width, height, Lang.get("BUTTON_OPTIONS"), null, null));
        y += height + 20;
        items.add(new MenuButton(x, y, width, height, Lang.get("BUTTON_EXIT"), batBall.getExiting(), batBall::setGameState));
    }

    @Override
    public void update() {
        items.forEach(MenuItem::update);
    }

    @Override
    public void render(Graphics g) {
        for (MenuItem item : items)
            item.render(g);
    }

    @Override
    public boolean isMainMenu() {
        return true;
    }
}
