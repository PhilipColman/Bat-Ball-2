/**
 * BatBall2
 * Created by Philip on 11/09/2016 at 02:39.
 */
package batball.logic;

import batball.BatBall;
import batball.gameStates.End;
import batball.gameStates.Play;
import batball.logic.gameObjects.Ball;
import batball.logic.gameObjects.Bat;
import batball.logic.gameObjects.Brick;
import batball.logic.gameObjects.ObjectManager;
import gameEngine.io.serialization.types.DatabaseFile;
import gameEngine.io.serialization.types.PrimitiveArray;
import gameEngine.io.serialization.types.PrimitiveType;
import gameEngine.io.serialization.types.TypeObject;
import gameEngine.lang.Lang;
import gameEngine.logic.gameObjects.ObjectHandler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import static batball.BatBall.log;

public class Level {

    private final int level;
    private int bricksLeft;

    private int score = 0;
    private long time = 0;
    protected String hud = "";
    private int lifes = 3;
    private int balls = 1;

    private Bat bat;
    private Brick[]bricks;
    private ObjectHandler objectHandler;
    private final Play play;
    //todo upgrades private UpgradeManager upgradeManager = new UpgradeManager();


    public Level(int level, Play play){
        this.level = level;
        this.objectHandler = new ObjectManager();
        this.play = play;
        bat = new Bat(objectHandler);

        try {
            deserialize(level);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(Brick brick1: bricks)
            objectHandler.addObject(brick1);

        bricksLeft = this.bricks.length;

        objectHandler.addObject(bat);
        objectHandler.addObject(new Ball(objectHandler, bat, this));
    }

    public void update(){
        time++;
        long clock = time / 60;
        String Time = String.format("%d:%s", clock / 60, clock % 60 < 10 ? ("0" + clock % 60) : clock % 60);
        hud = String.format("%s:  %d         %s:  %d       %s:  %s", Lang.get("LIFES"), lifes, Lang.get("SCORE"), score, Lang.get("TIME"), Time);
        objectHandler.update();
        if (bricksLeft == 0) {
            BatBall.getInstance().setEnd(new End(true, level, score, hud.substring(hud.length()-4), play));
            if(BatBall.getProgress() <= level){
                BatBall.setProgress(level + 1);
                BatBall.serialize();
            }
            BatBall.getInstance().setGameState(BatBall.getInstance().getEnd());
        }
    }

    public void render(Graphics g){
        objectHandler.render(g);
        g.setFont(new Font("arial", Font.PLAIN, 30));
        g.setColor(Color.WHITE);
        g.drawString(hud, BatBall.getWidth() - 10 - g.getFontMetrics().stringWidth(hud), 35);
    }

    public void loss(){
        BatBall.getInstance().setEnd(new End(false, level, score, hud.substring(hud.length()-4), play));
        BatBall.getInstance().setGameState(BatBall.getInstance().getEnd());
    }

    public void serialize() {
        DatabaseFile dataFile = new DatabaseFile("Level");
        TypeObject object = new TypeObject(String.format("Level %d", level));
        object.addField(PrimitiveType.Integer("level", this.level));
        object.addField(PrimitiveType.Integer("brickCount", bricks.length));
        int[] x = new int[bricks.length];
        int[] y = new int[bricks.length];
        int[] color = new int[bricks.length];
        boolean[] hard = new boolean[bricks.length];

        for(int i = 0; i < bricks.length; i++){
            x[i] = bricks[i].getX();
            y[i] = bricks[i].getY();
            color[i] = bricks[i].getColor().getRGB();
            hard[i] = bricks[i].isHard();
        }
        object.addArray(PrimitiveArray.Integer("x", x));
        object.addArray(PrimitiveArray.Integer("y", y));
        object.addArray(PrimitiveArray.Integer("color", color));
        object.addArray(PrimitiveArray.Boolean("hard", hard));

        dataFile.addObject(object);
        File path = new File("data/levels/");
        if(!path.exists()){
            path.mkdir();
        }
        File file = new File(String.format("data/levels/level%d.level", this.level));
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dataFile.serializeToFile(file.getPath());
        byte[] data = new byte[dataFile.getSize()];
        dataFile.getBytes(data, 0);
        log.info(data);
    }

    public void deserialize(int level) throws Exception {
        DatabaseFile databaseFile = DatabaseFile.DeserializeFromFile(String.format("data/levels/level%d.level", level));
        TypeObject object = databaseFile.findObject("Level " + level);
        int level1 = object.findField("level").getInt();
        if(this.level != level1){
            log.error("Wrong level");
            throw new Exception("Level mismatch");
        }
        int brickCount = object.findField("brickCount").getInt();
        bricks = new Brick[brickCount];

        int[] x = object.findArray("x").getIntData();
        int[] y = object.findArray("y").getIntData();
        int[] color = object.findArray("color").getIntData();
        boolean[] hard = object.findArray("hard").getBooleanData();

        bricks = new Brick[brickCount];

        for(int i = 0; i< brickCount; i++){
            bricks[i] = new Brick(x[i], y[i], BatBall.getWidth() / 10, 40, new Color(color[i]), hard[i], objectHandler);
        }
        bricksLeft = bricks.length;
    }

    public static boolean levelExists(int level){
        return new File(String.format("levels/level%d.level", level)).exists();
    }

    public static int getLevels(){
        return 200;
    }

    public int getBricksLeft() {
        return bricksLeft;
    }

    public void setBricksLeft(int bricksLeft) {
        this.bricksLeft = bricksLeft;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLifes() {
        return lifes;
    }

    public void setLifes(int lifes) {
        this.lifes = lifes;
    }

    public int getBalls() {
        return balls;
    }

    public void setBalls(int balls) {
        this.balls = balls;
    }

    public int getLevel() {
        return level;
    }

    public String getHud() {
        return hud;
    }
}
