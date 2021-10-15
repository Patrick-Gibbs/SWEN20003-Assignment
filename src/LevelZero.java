import bagel.Image;
import bagel.Input;

import java.util.ArrayList;

public class LevelZero extends Level {
    private static final Image BACKGROUND = new Image("res/level-0/background.png");
    private static final String WIN_MESSAGE = "LEVEL UP!";
    private static final int LIVES = 3;
    private static final int SCORE_TO_WIN = 1;
    private static final int WIN_SCREEN_FRAMES = 20;

    public LevelZero() {
      super();
        bird = new LevelZeroBird();
        pipes = new ArrayList<PipeSet>();
        pipes.add(new PlasticPipeSet(Levels.LEVEL_ZERO));
        currentGameState = GameState.START_SCREEN;
        healthBar = new HealthBar(LIVES);
    }

    @Override
    public void runLevel(Input input){
        super.runLevel(input);
        if (currentGameState == GameState.GAME_WON) {
            drawWinScreen();
        }
    }

    @Override
    public void updateGameState(Input input, Bird bird, ArrayList<PipeSet> pipes){
        super.updateGameState(input, bird, pipes);
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

    protected PipeSet generatePipe(){
        return new PlasticPipeSet(Levels.LEVEL_ZERO);
    }

    @Override
    protected void drawBackground() {
        BACKGROUND.draw(ShadowFlap.SCREEN_WIDTH / 2.0, ShadowFlap.SCREEN_HEIGHT / 2.0);;
    }

    @Override
    protected boolean isLevelUp(Score score) {
        return score.getCurrent_score() >= SCORE_TO_WIN;
    }

    @Override
    protected void drawWinScreen() {
        ShadowFlap.getInstance().MESSAGE_FONT.drawString(WIN_MESSAGE, MESSAGE_LOCATION.x - ShadowFlap.getInstance().MESSAGE_FONT.getWidth(WIN_MESSAGE)/2.0,
                MESSAGE_LOCATION.y);
    }

    public Bird generateBird(){
        return new LevelZeroBird();
    }
}
