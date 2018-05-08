/**
 * BatBall2
 * Created by Philip on 07/09/2016 at 18:53.
 */
package gameEngine.logic;

import gameEngine.GameEngine;
import gameEngine.gameStates.IGameStateProvider;
import gameEngine.graphics.Display;

public class GameLoop implements Runnable {

    private final Display display;
    private final IGameStateProvider gameStateProvider;
    private int ups = 0, fps = 0;
    private boolean running = false;
    private int updates = 0, frames = 0;
    private Thread thread;

    public GameLoop(Display display, IGameStateProvider gameStateProvider) {
        this.display = display;
        this.gameStateProvider = gameStateProvider;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfUpdates = 60.0;
        double ns = 1000000000 / amountOfUpdates;
        double delta = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if (!GameEngine.getInstance().isPaused())
                    gameStateProvider.getGameState().update();
                delta--;
                updates++;
            }
            if (running) {
                display.render();
                frames++;
            }
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = frames;
                frames = 0;
                ups = updates;
                updates = 0;
            }
        }
        stop();
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "Game Thread");
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getFps() {
        return fps;
    }

    public int getUps() {
        return ups;
    }

    public boolean isRunning() {
        return running;
    }
}
