import bagel.util.Point;
import bagel.Font;

import java.util.ArrayList;

/** Reppresents the current score for a level.
 */
public class Score {
    private static final int MESSAGE_SCORE_GAP = 75;
    private static final Point SCORE_LOCATION = new Point(100, 100);

    private final Font SCORE_FONT = ShadowFlap.getInstance().MESSAGE_FONT;

    private int current_score = 0;

    /**
     * Increments the current score by 1.
     */
    public void incrementScore() {
        current_score++;
    }

    /**
     * Draws the score in the top right when the game is running.
     */
    public void drawScore() {
        SCORE_FONT.drawString(getScoreString(), SCORE_LOCATION.x, SCORE_LOCATION.y);
    }

    /**
     * Draws the final score after the game has ended, this is drawn under the final message. center of screen.
     */
    public void drawFinalScore(){
        // Score is rendered in the middle of the screen, below the final message
        Point finalScoreLocation = new Point(ShadowFlap.MESSAGE_LOCATION.x -
                SCORE_FONT.getWidth(getFinalScoreString())/2, ShadowFlap.MESSAGE_LOCATION.y + MESSAGE_SCORE_GAP);
        SCORE_FONT.drawString(getFinalScoreString(), finalScoreLocation.x, finalScoreLocation.y);

    }
    /** Counts score if bird passes a set of pipes.
     * @param pipes an ArrayList of PipeSets.
    */
    public void updateScore(ArrayList<PipeSet> pipes){
        for (PipeSet pipe : pipes){
            // Takes the rightmost most part of one of the two pipe bounding boxes provided in an array
            // Pipes should only have there score counted once
            if (Bird.START_X > pipe.getBoundingBoxs()[0].right() && !pipe.isPointsCounted()){
                current_score++;
                pipe.setPointsCounted(true);
            }

            }
    }

    /** Gets the string to be displayed at the end of the game (GameOver, GameWin).
     * @return String to be displayed at the end of the game.
     */
    private String getFinalScoreString(){
        return "FINAL SCORE: " + current_score;
    }

    /** Gets the string to be displayed in the top right, the rununing score during the game.
     * @return String giving the text communicate the current score.
     */
    private String getScoreString(){
        return "Score: " + current_score;
    }

    /** Gets the current score
     * @return int The current score
     */
    public int getCurrent_score() {
        return current_score;
    }
}

