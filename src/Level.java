import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

import java.util.ArrayList;

/** Abstract class encapsulting the common game logic for level zero and level one.
 */
public abstract class Level{
    protected static final Image DEFUALT_BACKGROUND = new Image("res/level-0/background.png");
    protected static final String START_LEVEL_INSTRUCTION = "PRESS SPACCE TO START";
    protected static final String LOSS_MESSAGE = "GAME OVER";
    public static final Point MESSAGE_LOCATION = new Point(ShadowFlap.SCREEN_WIDTH/2.0, ShadowFlap.SCREEN_HEIGHT/2.0 + ShadowFlap.FONT_SIZE/2.0);

    protected Bird bird;
    protected ArrayList<PipeSet> pipes;
    protected final Score score;
    protected GameState currentGameState;
    protected HealthBar healthBar;

    protected int frameCounter;
    // adjusted slightly from specfication to make game alittle more playable.
    protected static final int PIPE_FREQUENCEY = 120;


    public Level() {
        frameCounter = 0;
        score = new Score();
    }

    protected abstract void drawBackground();
    protected abstract boolean isLevelUp(Score score);
    protected abstract void drawWinScreen();
    protected abstract PipeSet generatePipe();
    protected abstract Bird generateBird();

    /** The main update loop for the levels, updates the position and instance of all objects in the game as well as
     * drawing them on the screen.
     * @param input User input to control the bird.
     */
    public void runLevel(Input input) {
        //Draws background
        drawBackground();

        //Updates and draws the relevant objects based on the game state
        switch (currentGameState) {
            case START_SCREEN:
                drawStartScreen();
                break;
            case GAME_RUNNING:
                bird.updateBird(input);
                score.updateScore(pipes);
                healthBar.drawHealthBar();

                if (isOutOfBounds(bird)) {
                    healthBar.decrementLife();
                    bird = generateBird();
                }

                PipeSet pipeToRemove = null;
                for (PipeSet pipe : pipes) {
                    pipe.updatePipes();
                    if (pipe.hasColsionOccured(bird) || pipe.collisoinWithFlame(bird)) {
                        healthBar.decrementLife();
                        pipeToRemove = pipe;
                    }
                }
                if (pipeToRemove != null) {
                    pipes.remove(pipeToRemove);
                }

                if (frameCounter == PIPE_FREQUENCEY) {
                    pipes.add(generatePipe());
                    if (pipes.size() > 4) {
                        System.out.println(pipes.toArray().length);
                        pipes.remove(0);
                    }
                    frameCounter = 0;
                }
                frameCounter++;
                score.drawScore();
                break;
            case GAME_LOST:
                drawLossMessage();
                score.drawFinalScore();
        }
        updateGameState(input, bird, pipes);
    }
    protected void updateGameState(Input input, Bird bird, ArrayList<PipeSet> pipes){
        switch (currentGameState) {
            case START_SCREEN:
                if (input.isDown(Keys.SPACE)) {
                    currentGameState = GameState.GAME_RUNNING;
                }
                break;
            case GAME_RUNNING:
                for (PipeSet pipe : pipes) {
                    if (healthBar.getHealthRemaining() == 0) {
                        currentGameState = GameState.GAME_LOST;
                        break;
                    }
                }
                if (isLevelUp(score)) {
                    frameCounter = 0;
                    currentGameState = GameState.GAME_WON;
                }
            case GAME_LOST:
                if (input.isDown(Keys.ESCAPE)){
                    System.exit(0);
                }
        }
    }

    protected boolean isOutOfBounds(Bird bird){
        // Out of bounds collision
        double birdHeight = bird.getBirdPossionVector().y;
        return (birdHeight < 0 || birdHeight >= ShadowFlap.SCREEN_HEIGHT);
    }

    private void drawLossMessage(){
        Font messageFont = ShadowFlap.getInstance().MESSAGE_FONT;
        messageFont.drawString(LOSS_MESSAGE, MESSAGE_LOCATION.x - messageFont.getWidth(LOSS_MESSAGE)/2.0,
                MESSAGE_LOCATION.y);
    }
    protected void drawStartScreen() {
        Font currentFont = ShadowFlap.getInstance().MESSAGE_FONT;
        currentFont.drawString(START_LEVEL_INSTRUCTION, MESSAGE_LOCATION.x - currentFont.getWidth(START_LEVEL_INSTRUCTION)/2.0,
                MESSAGE_LOCATION.y);
    }


}


