import bagel.Font;
import bagel.Image;
import bagel.Input;

import java.util.ArrayList;

public class LevelZero extends Level {
    private static final Image BACKGROUND = new Image("res/level-0/background.png");
    private static final String WIN_MESSAGE = "LEVEL UP!";
    private static final int LIVES = 3;
    private static final int SCORE_TO_WIN = 1;
    private static final int WIN_SCREEN_FRAMES = 20;

    /** Creates a new instance of Level Zero initalised at the starting screen.
     */
    public LevelZero() {
      super();
        bird = new LevelZeroBird();
        pipes = new ArrayList<PipeSet>();
        pipes.add(new PlasticPipeSet(Levels.LEVEL_ZERO));
        currentGameState = GameState.START_SCREEN;
        healthBar = new HealthBar(LIVES);
    }

    /** Main update loop which draws and updates possition of game objects
     * @param input User input to control the bird.
     */
    @Override
    public void runLevel(Input input){
        super.runLevel(input);
        if (currentGameState == GameState.GAME_WON) {
            drawWinScreen();
        }
    }

    // updates the state of the game,  e.g. game won/game lost.
    @Override
    protected void updateGameState(Input input){
        super.updateGameState(input);
        switch (currentGameState){
            case GAME_WON:
                if (frameCounter == WIN_SCREEN_FRAMES){
                    ShadowFlap.getInstance().levelUp();
                }
                frameCounter++;
                System.out.println(frameCounter);
                break;
        }
    }

    // generates relevant pipe sets, here only plasitic pipes are generated using the level zero spawning locations
    protected PipeSet generatePipe(){
        return new PlasticPipeSet(Levels.LEVEL_ZERO);
    }

    // draws the level relevant background.
    @Override
    protected void drawBackground() {
        BACKGROUND.draw(ShadowFlap.SCREEN_WIDTH / 2.0, ShadowFlap.SCREEN_HEIGHT / 2.0);;
    }

    //check if the level has been won.
    @Override
    protected boolean isLevelUp(Score score) {
        return score.getCurrent_score() >= SCORE_TO_WIN;
    }

    // draws the relevant text for when the level has been won
    @Override
    protected void drawWinScreen() {
        // message drawn in the middle of the screen
        Font currentFont =  ShadowFlap.getInstance().MESSAGE_FONT;
        currentFont.drawString(WIN_MESSAGE, MESSAGE_LOCATION.x - currentFont.getWidth(WIN_MESSAGE)/2.0,
                MESSAGE_LOCATION.y);
    }

    public Bird generateBird(){
        return new LevelZeroBird();
    }
}
