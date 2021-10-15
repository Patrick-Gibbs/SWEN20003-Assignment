import bagel.Image;

import java.awt.*;

/** Represents the healthbar in game which shows the player how many lives remain. if the player runs out of
 * lives they lose the game.
 */
public class HealthBar{
    private static final String FULL_LIFE_PATH = "res/level/fullLife.png";
    private static final String EMPTY_LIFE_PATH = "res/Level/noLife.png";
    private static final Image FULL_LIFE = new Image(FULL_LIFE_PATH);
    private static final Image EMPTY_LIFE = new Image(EMPTY_LIFE_PATH);
    private static final Point FIRST_LIFE_LOCATION = new Point(100,15);
    private static final int LIFE_SPACING = 50;

    private int healthRemaining;
    private final int maxHealth;

    /** Creates a full health bar with a specified number of lives
     * @param lives The inital number of lives in the healthbar.
     */
    HealthBar(int lives){
        healthRemaining = lives;
        maxHealth = lives;
    }

    /** Draws the healthbar on the screen indicating how many lives remain.
     */
    public void drawHealthBar(){
        // This loop spaces out multiple lives to make a healthbar
        for(int i = 0 ; i < maxHealth ; i++){
            drawLife(FIRST_LIFE_LOCATION.x + (FULL_LIFE.getWidth() + LIFE_SPACING)*i, FIRST_LIFE_LOCATION.y,
                    i < healthRemaining);
        }
    }

    /** Draw a life at a specified location, the life can be full or empty
     * @param x The X location at which the life is drawn.
     * @param y The Y location at which the life is drawn.
     * @param full boolean indicating whether or not to draw a full life.
     */
    private void drawLife(double x, double y, boolean full){
        if (full){
            FULL_LIFE.draw(x + FULL_LIFE.getWidth()/2.0,y + FULL_LIFE.getWidth()/2.0);
        }
        else{
            EMPTY_LIFE.draw(x + EMPTY_LIFE.getWidth()/2.0,y + EMPTY_LIFE.getWidth()/2.0);
        }
    }

    /** Decrments one life from the health bar meaning a full life becomes an empty life.
     */
    public void decrementLife(){
        healthRemaining--;
    }

    /** Gets the number of lives remaining in the healthbar
     * @return int The number of lives remaining.
     */
    public int getHealthRemaining() {
        return healthRemaining;
    }
}
