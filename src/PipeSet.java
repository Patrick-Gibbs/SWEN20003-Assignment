import bagel.*;
import bagel.Image;
import bagel.util.Vector2;
import bagel.util.Rectangle;

/** Represents sets of pipes. Pipes are the obstacle of ShaddowFlap game which the bird must fly between to gather
 * points.
 */
public abstract class PipeSet implements Colidable {
    protected static final double PIPE_GAP = 168;
    private static final Image DEFULT_SPRITE = new Image("res/level/plasticPipe.png");


    protected static final double PIPE_SPRIT_HEIGHT = DEFULT_SPRITE.getHeight();
    /** The width of the pipe sprites */
    protected static final double PIPE_SPRIT_WIDTH = DEFULT_SPRITE.getWidth();

    //calculation required as sprite is overly long, and drawn from the middle
    //initially place on the far right of screen
    protected static final double STARTING_BOTTOM_PIPE_X = ShadowFlap.SCREEN_WIDTH + PIPE_SPRIT_WIDTH/2.0;
    protected static final double STARTING_TOP_PIPE_X = ShadowFlap.SCREEN_WIDTH + PIPE_SPRIT_WIDTH/2.0;

    //Speed pipes move across the screen right to left
    private static double pipeSpeed = ShadowFlap.RIGHT_TO_LEFT_SPEED;
    protected static final double HIGH_PIPE_OVERHANG = 100;
    protected static final double LOW_PIPE_OVERHANG = 500;


    // keeps track of pipes location
    protected Vector2 bottomPipeVector;
    protected Vector2 topPipeVector;

    protected boolean pointsCounted;
    protected double topOverhang;


    //Only top pipe sprite is provides, bottom sprite is rotation of top sprite
    protected final DrawOptions BOTTOM_PIPE_ROTATION = new DrawOptions().setRotation(Math.PI);

    /** Generates a new set of pipes on the right side of the screen, by defult they are randomly generated
     * at a set distance apart (168 pixels) with the gap between them starting at a randomized y location
     * within set bounds (100-500).
     */
    public PipeSet(){
        pointsCounted = false;
        topOverhang = HIGH_PIPE_OVERHANG +  Math.random() * (LOW_PIPE_OVERHANG - HIGH_PIPE_OVERHANG);
    }

    /**updates position of pipes and also draws them.
     */
    public void updatePipes(){
        pipeSpeed = ShadowFlap.RIGHT_TO_LEFT_SPEED * ShadowFlap.getInstance().getGameSpeed();
        topPipeVector = topPipeVector.add(Vector2.left.mul(pipeSpeed));
        bottomPipeVector = bottomPipeVector.add(Vector2.left.mul(pipeSpeed));

    }

    // Sets the pipe locatoin based on topOverhang, the distance from the end of the top pipe to the top of the screen.
    protected void setPipeVectors(double topOverhang){
        double startingTopPipeY1 = ShadowFlap.SCREEN_HEIGHT + PIPE_SPRIT_HEIGHT/2.0 - topOverhang;
        double startingBottomPipeY = ShadowFlap.SCREEN_HEIGHT - PIPE_SPRIT_HEIGHT/2.0 - PIPE_GAP - topOverhang;
        bottomPipeVector = new Vector2(STARTING_BOTTOM_PIPE_X, startingBottomPipeY);
        topPipeVector = new Vector2(STARTING_TOP_PIPE_X, startingTopPipeY1);
    }

    /** Checks if there has been a collision with a target and a flame assoitaed with the pipe (if there is a flame
     * associated)
     * @param target
     * @return boolean, whether a collision has occurred between the target and the flame.
     */
    public abstract boolean collisoinWithFlame(Colidable target);

    /** checks if the points have been counted from the bird passing the set of pipes.
     * @return boolean, If the points have been counted for the set of pipes.
     */
    public boolean isPointsCounted() {
        return pointsCounted;
    }

    /** sets if the points have been counted for a set of pipes
     * @param pointsCounted if the points care counted or not.
     */
    public void setPointsCounted(boolean pointsCounted) {
        this.pointsCounted = pointsCounted;
    }

    @Override
    /** checks if a collision has occured between the pipes and a target object
     * @param Colidable The object that a colison may have occured with.
     * @return boolean If a collsion has occured
     */
    public boolean hasColsionOccured(Colidable target) {
        Rectangle topPipeRectangle = DEFULT_SPRITE.getBoundingBoxAt(bottomPipeVector.asPoint());
        Rectangle bottomPipeRectangle = DEFULT_SPRITE.getBoundingBoxAt(topPipeVector.asPoint());
        // Collision Either pipe
        for(Rectangle currentRect : target.getBoundingBoxs()){
            if(currentRect.intersects(topPipeRectangle) || currentRect.intersects(bottomPipeRectangle)){
                return true;
            }
        }
        return false;
    }

    /** Gets the bounding box for both pipes
     * @return Rectangle[] an array containg the upper and lower bounding box for the pipes.
     */
    @Override
    public Rectangle[] getBoundingBoxs() {
        return new Rectangle[]{DEFULT_SPRITE.getBoundingBoxAt(bottomPipeVector.asPoint()), DEFULT_SPRITE.getBoundingBoxAt(topPipeVector.asPoint())};
    }
}


