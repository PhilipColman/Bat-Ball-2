/**
 * BatBall2
 * Created by Philip on 08/09/2016 at 21:52.
 */
package gameEngine.io.input;

import gameEngine.GameEngine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    private static final boolean[] keysDown = new boolean[600];

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysDown[e.getKeyCode()] = true;
        if (e.getKeyCode() == GameEngine.getInstance().getPAUSED_KEY() && GameEngine.getInstance().getGameStateProvider().getGameState().canBePaused()) {
            GameEngine.getInstance().togglePaused();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysDown[e.getKeyCode()] = false;
    }

    public static boolean isKeyDown(int keyCode) {
        return keysDown[keyCode];
    }
}
