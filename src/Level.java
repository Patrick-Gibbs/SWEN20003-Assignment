import bagel.*;
import bagel.util.Point;

import java.util.ArrayList;

/** Abstract class encapsulting the common game logic for level zero and level one.
 */
public abstract class Level{
    protected static final String START_LEVEL_INSTRUCTION = "PRESS SPACE TO START";
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
                // if bird leaves the screen life is lost and bird respawned.
                if (isOutOfBounds(bird)) {
                    healthBar.decrementLife();
                    bird = generateBird();
                }
                // Checks each pipe for collision with bird.
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
                // Spawns pipes, Removes pipes as they leave the screen.
                if (frameCounter == PIPE_FREQUENCEY) {
                    pipes.add(generatePipe());
                    if (pipes.size() > 4) {
                        pipes.remove(0);
                    }
                    frameCounter = 0;
                }
                frameCounter++;
                score.drawScore();
                healthBar.drawHealthBar();
                break;
            case GAME_LOST:
                drawLossMessage();
                score.drawFinalScore();
                break;
        }
        updateGameState(input);
    }
    // Update the state of the level e.g. win, lose, game running.
    protected void updateGameState(Input input){
        switch (currentGameState) {
            //Game starts once space is pressed
            case START_SCREEN:
                if (input.isDown(Keys.SPACE)) {
                    currentGameState = GameState.GAME_RUNNING;
                }
                break;
            case GAME_RUNNING:
                // Game lost once lives run out
                if (healthBar.getHealthRemaining() == 0) {
                    currentGameState = GameState.GAME_LOST;
                    break;
                }
                // Level won once a certain score is reached.
                if (isLevelUp(score)) {
                    frameCounter = 0;
                    currentGameState = GameState.GAME_WON;
                }
            // Exit from game lost screen with ESCAPE key
            case GAME_LOST:
                if (input.isDown(Keys.ESCAPE)){
                    System.exit(0);
                }
        }
    }

    // Checks if the bird has left the screen.
    protected boolean isOutOfBounds(Bird bird){
        // Out of bounds collision
        double birdHeight = bird.getBirdPossionVector().y;
        return (birdHeight < 0 || birdHeight >= ShadowFlap.SCREEN_HEIGHT);
    }

    // Draws the message on the loss screen
    private void drawLossMessage(){
        Font messageFont = ShadowFlap.getInstance().MESSAGE_FONT;
        messageFont.drawString(LOSS_MESSAGE, MESSAGE_LOCATION.x - messageFont.getWidth(LOSS_MESSAGE)/2.0,
                MESSAGE_LOCATION.y);
    }
    // Draws the message on the start screen.
    protected void drawStartScreen() {
        Font currentFont = ShadowFlap.getInstance().MESSAGE_FONT;
        currentFont.drawString(START_LEVEL_INSTRUCTION, MESSAGE_LOCATION.x - currentFont.getWidth(START_LEVEL_INSTRUCTION)/2.0,
                MESSAGE_LOCATION.y);
    }


}


