/**
 * BatBall2
 * Created by Philip on 09/09/2016 at 20:25.
 */
package gameEngine.graphics.menuItems;

import gameEngine.gameStates.BaseGameState;
import gameEngine.io.input.MouseInput;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

public class MenuButton extends MenuItem{

    protected boolean mouseOver;
    protected final BaseGameState state;
    protected final IMenuButtonClicked buttonClicked;

    public MenuButton(int x, int y, int width, int height, String text, BaseGameState state, IMenuButtonClicked buttonClicked) {
        super(x, y, width, height, text);
        this.mouseOver = false;
        this.state = state;
        this.buttonClicked = buttonClicked;
    }

    @Override
    public void update() {
        mouseOver = new Rectangle(x, y, width, height).intersects(new Rectangle(MouseInput.getX(), MouseInput.getY(), 1, 1));
        if (mouseOver && MouseInput.LMBIsDown()) {
            if (state != null) {
                MouseInput.setLMB(false);
                buttonClicked.menuButtonClicked(state);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(mouseOver ? Color.white : Color.black);
        g.fillRect(x, y, width, height);
        g.setColor(mouseOver ? Color.black : Color.white);
        g.drawRect(x, y, width, height);
        g.setFont(new Font("arial", Font.PLAIN, 30));
        g.drawString(text, x - (g.getFontMetrics().stringWidth(text) / 2) + width / 2, y + height / 2 + 10);
    }

}
