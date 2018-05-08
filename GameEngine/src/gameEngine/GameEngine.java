package gameEngine;

import gameEngine.gameStates.IGameStateProvider;
import gameEngine.graphics.Display;
import gameEngine.logging.Log;
import gameEngine.logic.GameLoop;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.event.KeyEvent;

/**
 * BatBall2
 * Created by Philip on 07/09/2016 at 18:13.
 */

public class GameEngine {

    private int width, height;
    private static boolean debug = false;
    private boolean paused = false;
    private int PAUSED_KEY = KeyEvent.VK_P;
    private int EXIT_KEY = KeyEvent.VK_ESCAPE;

    private final JFrame window;
    private final Display display;
    private final GameLoop loop;
    private final IGameStateProvider gameStateProvider;
    public static final Log log = new Log("Game Engine");

    private static GameEngine instance;

    private GameEngine(String title, int width, int height, IGameStateProvider gameStateProvider, boolean resizable, boolean undecorated, boolean startFullscreen) {
        this.width = width;
        this.height = height;
        this.gameStateProvider = gameStateProvider;
        display = new Display(this.width, this.height, this.gameStateProvider);
        window = new JFrame(title);
        window.setSize(width, height);
        window.setResizable(resizable);
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setUndecorated(undecorated);
        window.setLocationRelativeTo(null);
        if (startFullscreen) {
            window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        window.add(display);
        window.setVisible(true);
        display.requestFocus();
        loop = new GameLoop(this.display, this.gameStateProvider);
        loop.start();
    }

    public static void init(String title, int width, int height, IGameStateProvider gameStateProvider, boolean resizable, boolean undecorated, boolean startFullscreen) {
        instance = new GameEngine(title, width, height, gameStateProvider, resizable, undecorated, startFullscreen);
    }

    public static void init(String title, int width, int height, IGameStateProvider gameStateProvider) {
        instance = new GameEngine(title, width, height, gameStateProvider, false, true, true);
    }


    public static void stopSafely() {
        Log.closeLog();
        instance.loop.stop();
    }

    public static boolean isDebugEnabled() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        GameEngine.debug = debug;
    }

    public static GameEngine getInstance() {
        return instance;
    }

    public GameLoop getLoop() {
        return loop;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isPaused() {
        return paused;
    }

    public void togglePaused() {
        paused = !paused;
    }

    public int getEXIT_KEY() {
        return EXIT_KEY;
    }

    public int getPAUSED_KEY() {
        return PAUSED_KEY;
    }

    public IGameStateProvider getGameStateProvider() {
        return gameStateProvider;
    }
}