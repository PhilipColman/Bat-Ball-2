/**
 * BatBall2
 * Created by Philip on 07/09/2016 at 18:23.
 */
package gameEngine.graphics;

import gameEngine.GameEngine;
import gameEngine.gameStates.IGameStateProvider;
import gameEngine.io.input.KeyInput;
import gameEngine.io.input.MouseInput;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends Canvas {

    private final int width, height;

    private static Color debugTextColor = Color.WHITE;

    private final IGameStateProvider gameStateProvider;

    public Display(int width, int height, IGameStateProvider gameStateProvider) {
        this.gameStateProvider = gameStateProvider;
        this.width = width;
        this.height = height;
        addKeyListener(new KeyInput());
        MouseInput mouseInput = new MouseInput();
        addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
        //addMouseWheelListener(mouseInput);
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        gameStateProvider.getGameState().render(g);

        if (GameEngine.isDebugEnabled()) {
            String[] debugInfo = new String[]{
                    String.format("FPS: %d", GameEngine.getInstance().getLoop().getFps()),
                    String.format("UPS: %d", GameEngine.getInstance().getLoop().getUps()),
                    //String.format("Mouse x: %d", MouseInput.getX()),
                    //String.format("Mouse y: %d", MouseInput.getY())
            };
            g.setColor(debugTextColor);
            g.setFont(new Font("arial", Font.PLAIN, 12));
            int i = 20;
            for (String text : debugInfo)
                g.drawString(text, 10, i += 15);
        }

        if (GameEngine.getInstance().isPaused()) {
            Graphics2D g2D = ((Graphics2D) g);
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
            g2D.setColor(Color.BLACK);
            g2D.fillRect(0, 0, GameEngine.getInstance().getWidth(), GameEngine.getInstance().getHeight());
            g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            g.setFont(new Font("impact", Font.PLAIN, 150));
            int x = (width >> 1);
            int y = GameEngine.getInstance().getHeight() >> 1;
            String text = "PAUSED";
            g.setColor(Color.WHITE);
            g.drawString(text, x - (g.getFontMetrics().stringWidth(text) >> 1), y + 75);
        }

        g.dispose();
        bs.show();
    }
}
