import bagel.*;
import bagel.Image;
import bagel.util.Vector2;

import bagel.util.Rectangle;

/** Abstract class to represent the bird in level zero and level one.
 */
public abstract class Bird implements Colidable {
    /** The inital X coordinate of the bird
     * This attribute is used for scoring.
     */
    public static final int START_X = 200;
    private static final int START_Y = 350;



    // Movement of the Bird
    private static final double GRAVITY = 0.4;
    private static final double FLAP_SPEED = 6;
    private static final double MAX_VELOCITY = -10;
    private double verticalVelocity;

    private boolean flapActionDone;
    private boolean spaceDown;

    // Keeps track of birds position
    protected Vector2 birdPossionVector;

    // Sprites alternated to give flapping animation
    // counts from 0 so frequencey of 9 means wings down drawn every 10 upates
    protected static final int FLAP_ANIMATION_FREQUENCEY = 9;

    protected int animationCounter;

    /** Creats a new instance of the bird class at initial spawning location.
     */
    public Bird() {
        birdPossionVector = new Vector2(START_X, START_Y);
        verticalVelocity = 0;
        // initialised to -1 rather than 0 to prevent out by one error on first cycle.
        // this quirk is due to the bird being drawn once before the first update
        animationCounter = -1;
        spaceDown = false;
        flapActionDone = false;
    }

    /** Updates the velocity of the bird, and draws the bird.
     * @param input User input, used to make the birds wings flap when SpaceBar is pressed
     */
    public void updateBird(Input input) {


        // This means a 'flap' action only happens 1 time for every time space bar is pressed, at the start of the
        // space key being pushed down
        spaceDown = input.wasPressed(Keys.SPACE);
        if (input.wasReleased(Keys.SPACE)) {
            flapActionDone = false;
        }

        if (spaceDown && !flapActionDone) {
            verticalVelocity = FLAP_SPEED;
            flapActionDone = true;
        } else {
            // Velocity changes due to gravity
            // Ensure the bird does not exceed the maximum falling velocity.
            if (verticalVelocity > MAX_VELOCITY) {
                verticalVelocity -= GRAVITY;
            } else {
                verticalVelocity = MAX_VELOCITY;
            }
        }
        //updates the bird position based on the velocity.
        birdPossionVector = birdPossionVector.add(Vector2.up.mul(verticalVelocity));

        //keeps track of the sprite that should be drawn for the bird
        if (animationCounter == FLAP_ANIMATION_FREQUENCEY) {
            animationCounter = 0;
        } else {
            animationCounter++;
        }
        // bird drawn on screen at current position
        drawBird();
    }

    /** Gets the sprite used to draw the bird on the screen at current frame.
     *  Every 10 frames the alternative sprite is used to give the flapping animation.
     */
    public abstract Image getCurrentBirdSprite();

    /** draws bird on screen at its current position
     */
    private void drawBird() {
        getCurrentBirdSprite().draw(birdPossionVector.x, birdPossionVector.y);
    }

    /** Gets the current position of the bird.
     * @return Vector2 the current position of the bird.
     */
    public Vector2 getBirdPossionVector() {
        return birdPossionVector;
    }

    /** Checks whether the bird is colliding with a given target.
     * @param target Checks whether the bird sprite is overlaping with the sprite(s) of the target.
     * @return boolean Whether or not a coloison has occured with the bird of the target
     */
    @Override
    public boolean hasColsionOccured(Colidable target) {
        for(Rectangle currentRect : target.getBoundingBoxs()){
            if(getCurrentBirdSprite().getBoundingBoxAt(birdPossionVector.asPoint()).intersects(currentRect)){
                return true;
            }
        }
        return false;
    }

    /** Gets the bounding Box of the bird sprite at its current possitoin.
     * @return Rectangle[] an array containing one element, the birds current possition.
     */
    @Override
    public Rectangle[] getBoundingBoxs() {
        return new Rectangle[]{getCurrentBirdSprite().getBoundingBoxAt(birdPossionVector.asPoint())};
    }
}
