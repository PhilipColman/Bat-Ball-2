/**
 * BatBall2
 * Created by Philip on 07/09/2016 at 19:18.
 */
package batball;

import batball.gameStates.*;
import gameEngine.GameEngine;
import gameEngine.gameStates.BaseGameState;
import gameEngine.gameStates.IGameStateProvider;
import gameEngine.io.serialization.types.DatabaseFile;
import gameEngine.io.serialization.types.PrimitiveType;
import gameEngine.io.serialization.types.TypeObject;
import gameEngine.lang.Lang;
import gameEngine.logging.Log;

import java.io.File;
import java.io.IOException;

public class BatBall implements IGameStateProvider {

    private static BatBall instance;

    private static final int WIDTH = 1366, HEIGHT = 768;

    private static int progress = 1;

    private BaseGameState gameState;
    private final BaseGameState mainMenu;
    private final BaseGameState levels;
    private final BaseGameState exiting;
    private final BaseGameState play;
    private BaseGameState end;

    public static Log log = new Log("Bat and Ball 2");


    private BatBall() {
        Lang.init("EN_GB");
        //GameEngine.setDebug(true);
        loadProgressFromFile();
        exiting = new Exiting();
        play = new Play();
        levels = new Levels(((Play) play));
        mainMenu = new MainMenu(this, WIDTH, HEIGHT);

        gameState = mainMenu;
        GameEngine.init(Lang.get("APP_NAME"), WIDTH, HEIGHT, this, false, true, false);

    }

    private void loadProgressFromFile() {
        File path = new File("data/");
        if(!path.exists()){
            path.mkdir();
        }
        File data = new File("data/gameProgress.data");
        if(!data.exists()) {
            try {
                data.createNewFile();
                serialize();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        deserialize();
    }

    private static void deserialize(){
        DatabaseFile dataFile = DatabaseFile.DeserializeFromFile("data/gameProgress.data");
        TypeObject object = dataFile.findObject("progress");
        PrimitiveType flied = object.findField("progress");
        progress = flied.getInt();
    }

    public static void serialize(){
        File data = new File("data/gameProgress.data");
        DatabaseFile dataFile = new DatabaseFile("gameProgress");
        TypeObject object = new TypeObject("progress");
        object.addField(PrimitiveType.Integer("progress", progress));
        dataFile.addObject(object);
        dataFile.serializeToFile(data.getPath());
    }

    public static void main(String... args) {
        instance = new BatBall();
    }

    public static BatBall getInstance() {
        return instance;
    }

    public static int getWidth() {
        return WIDTH;
    }

    public static int getHeight() {
        return HEIGHT;
    }

    public static void setProgress(int progress) {
        BatBall.progress = progress;
    }

    public static int getProgress() {
        return progress;
    }

    public BaseGameState getGameState() {
        return gameState;
    }

    public void setGameState(BaseGameState gameState) {
        this.gameState = gameState;
        log.trace("Setting state to: " + gameState.getClass());
    }

    public BaseGameState getMainMenu() {
        return mainMenu;
    }

    public BaseGameState getLevels() {
        return levels;
    }

    public BaseGameState getExiting() {
        return exiting;
    }

    public BaseGameState getPlay() {
        return play;
    }


    public BaseGameState getEnd() {
        return end;
    }

    public void setEnd(End end) {
        this.end = end;
    }
}