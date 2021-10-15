import bagel.*;
import bagel.util.Point;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2021
 *
 * Please filling your name below
 * @author: Patrick Gibbs 1083438
 */

public class ShadowFlap extends AbstractGame {
    // Static attributes
    // public as utilised by other classes
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 768;
    public static final int FONT_SIZE = 48;
    public static final Point MESSAGE_LOCATION = new Point(SCREEN_WIDTH/2.0, SCREEN_HEIGHT/2.0 + FONT_SIZE/2.0);
    public static final String FONT_PATH = "res/font/slkscr.ttf";


    private boolean lKeyWasDown = false;
    private boolean kKeyWasDown = false;

    public static final double RIGHT_TO_LEFT_SPEED = 3;
    private double timeScale = 0;

    public double getGameSpeed() {
        return gameSpeed;
    }

    private static final double GAME_SPEED_MULTIPLYER = 1.5;
    private double gameSpeed;

    private static final String GAME_TITLE = "ShadowFlap";

    // Non-Static attributes
    public final Font MESSAGE_FONT = new Font(ShadowFlap.FONT_PATH, ShadowFlap.FONT_SIZE);

    private Level currentLevelInstance;
    private Levels currentLevel = Levels.LEVEL_ZERO;
    private static ShadowFlap currentInstance;

    private ShadowFlap() {
        super(SCREEN_WIDTH, SCREEN_HEIGHT, GAME_TITLE);
        currentLevelInstance = new LevelZero();
        gameSpeed = calculateGameSpeed();
    }
    public static ShadowFlap getInstance(){
        if (currentInstance == null){
            currentInstance = new ShadowFlap();
        }
        return currentInstance;
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowFlap game = getInstance();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input){
        setTimescale(input);
        if(currentLevelInstance instanceof LevelZero && currentLevel == Levels.LEVEL_ONE) {
            System.exit(0);
        }
        //level one
        currentLevelInstance.runLevel(input);
    }

    public void levelUp(){
        assert currentLevel == Levels.LEVEL_ZERO && currentLevelInstance instanceof LevelZero;
        currentLevelInstance = new LevelOne();
        currentLevel = Levels.LEVEL_ONE;
    }

    public double calculateGameSpeed() {

        return Math.pow(GAME_SPEED_MULTIPLYER, timeScale);
    }

    public void incrmentTimeScale() {
        if (timeScale < 4){
            timeScale++;
            gameSpeed = calculateGameSpeed();
        }
    }

    public void decremenTimeScale() {
        if (timeScale > 0){
            timeScale--;
            gameSpeed = calculateGameSpeed();
        }
    }

    public Levels getCurrentLevel() {
        return currentLevel;
    }

    private void setTimescale(Input input) {
        if (input.isDown(Keys.K) && !kKeyWasDown) {
            kKeyWasDown = true;
            ShadowFlap.getInstance().decremenTimeScale();
            System.out.println(ShadowFlap.getInstance().getGameSpeed());
        }
        if (input.isUp(Keys.K)) {
            kKeyWasDown = false;
        }

        if (input.isDown(Keys.L) && !lKeyWasDown) {
            lKeyWasDown = true;
            ShadowFlap.getInstance().incrmentTimeScale();
            System.out.println(ShadowFlap.getInstance().getGameSpeed());

        }
        if (input.isUp(Keys.L)) {
            lKeyWasDown = false;
        }
    }
}