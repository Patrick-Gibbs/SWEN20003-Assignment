import bagel.*;
import bagel.util.Point;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 2, 2021
 * Shaddow Flap game where bird must move through pipes to score points.
 * Please filling your name below
 * @author: Patrick Gibbs 1083438
 */

public class ShadowFlap extends AbstractGame {
    private static final String GAME_TITLE = "ShadowFlap";

    // public as utilised by other classes
    /** The width of the game screen */
    public static final int SCREEN_WIDTH = 1024;
    /** The height of the game screen */
    public static final int SCREEN_HEIGHT = 768;
    /** The font size used through out the game */
    public static final int FONT_SIZE = 48;
    /** The location at which messages are displayed to player e.g. GAMEOVER */
    public static final Point MESSAGE_LOCATION = new Point(SCREEN_WIDTH/2.0, SCREEN_HEIGHT/2.0 + FONT_SIZE/2.0);
    /** Path to font used throughout game, this is required public becouse the Font class is glitchy and can bug out
     * as a static variable.
     */
    public static final String FONT_PATH = "res/font/slkscr.ttf";
    /** Font used throughout the game. */
    public final Font MESSAGE_FONT = new Font(ShadowFlap.FONT_PATH, ShadowFlap.FONT_SIZE);

    // for time scale controls
    private static final int MAX_GAME_SPEED = 4;
    private boolean lKeyWasDown = false;
    private boolean kKeyWasDown = false;
    private double timeScale = 0;
    private static final double GAME_SPEED_MULTIPLYER = 1.5;
    private double gameSpeed;
    /** The speed at which pipes weapon and flames move right to left by defult */
    public static final double RIGHT_TO_LEFT_SPEED = 3;

    // Keeps track of the level the game is running
    private Level currentLevelInstance;
    private Levels currentLevel = Levels.LEVEL_ZERO;
    private static ShadowFlap currentInstance;


    private ShadowFlap() {
        super(SCREEN_WIDTH, SCREEN_HEIGHT, GAME_TITLE);
        currentLevelInstance = new LevelZero();
        gameSpeed = calculateGameSpeed();
    }

    /** Gets the current instance of the shaddow flap game, there can only be one instance at any time. This utilised
     * the singleton design pattern
     * @return ShadowFlap the current instance of the game.
     */
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
        currentLevelInstance.runLevel(input);
    }

    /** changes the game from level zero to level one. */
    public void levelUp(){
        assert currentLevel == Levels.LEVEL_ZERO && currentLevelInstance instanceof LevelZero;
        currentLevelInstance = new LevelOne();
        currentLevel = Levels.LEVEL_ONE;
    }

    /* calculates mutiplayer for timescale controls
     * @return the current multiplayer for time scale control.
     */
    private double calculateGameSpeed() {
        return Math.pow(GAME_SPEED_MULTIPLYER, timeScale);
    }

    /** increases the game speed by one increment */
    public void incrmentTimeScale() {
        // game speed increase is capped
        if (timeScale < MAX_GAME_SPEED){
            timeScale++;
            gameSpeed = calculateGameSpeed();
        }
    }

    /** decreases the game speed by one increment */
    public void decremenTimeScale() {
        // game speed cant be reduced below intial level.
        if (timeScale > 0){
            timeScale--;
            gameSpeed = calculateGameSpeed();
        }
    }

    /** sets the current time scale speed based on user input.
     * @param input user input to contorl time scale
     */
    private void setTimescale(Input input) {
        // if K key is pressent decrement time scale
        if (input.isDown(Keys.K) && !kKeyWasDown) {
            kKeyWasDown = true;
            ShadowFlap.getInstance().decremenTimeScale();
        }
        if (input.isUp(Keys.K)) {
            kKeyWasDown = false;
        }
        // if L key is pressed increment time scale
        if (input.isDown(Keys.L) && !lKeyWasDown) {
            lKeyWasDown = true;
            ShadowFlap.getInstance().incrmentTimeScale();
        }
        if (input.isUp(Keys.L)) {
            lKeyWasDown = false;
        }
    }

    /** gets the current game speed.
     * @return double the current game speed levle.
     */
    public double getGameSpeed() {
        return gameSpeed;
    }
}