import bagel.Image;

/** Represtents Metal Pipe sets in the game. Metal pipe are only features of level one. they can be destroyed by
 * Bomb weapons. Metal Pipes shoot flames out thier ends peroidically.
 */
public class MetalPipeSet extends PipeSet {
    private final Image PIPE_SPRIT = new Image("res/level-1/steelPipe.png");
    double bottomOverhang = Math.random() * (ShadowFlap.SCREEN_HEIGHT - PIPE_GAP);
    private int flameCounter;
    private Flame flames;
    private static final int FRAMES_FLAME_SHOT = 30;
    private static final int FRAMES_FLAME_NOT_SHOT = 20;

    /** Creates a new set of metal pipes on the far right hand side of the screen
     * with the flames shooting, with a randomised amount of time remain for their current cycle.
     */
    public MetalPipeSet(){
        super();
        setPipeVectors(topOverhang);
        // Flames start with at a random point in their cycle, meaning the player needs to predict if the flame
        // will be able to hit the bird or not. It is not clearly stated in the specification initial flame state
        // so this method was choosen to make the game mechanics functional and interesting.
        flameCounter = (int) (Math.random()*FRAMES_FLAME_SHOT);
        flames = new Flame(this);
    }

    /** updates position of pipes and also draws them. flame location and drawing is also mangaged here.
     */
    public void updatePipes() {
        // Draw top and Bottom Pipes
        PIPE_SPRIT.draw(bottomPipeVector.x, bottomPipeVector.y);
        PIPE_SPRIT.draw(topPipeVector.x, topPipeVector.y, BOTTOM_PIPE_ROTATION);
        // Based on flame counter the flames are updated within the game or they are removed from the game.
        // This creates a cycle when they are shooting and not shooting out of the metal pipes.
        if (flameCounter == 0) {
            if (flames == null) {
                flames = new Flame(this);
                flameCounter = FRAMES_FLAME_SHOT;
            } else {
                flames = null;
                flameCounter = FRAMES_FLAME_NOT_SHOT;
            }
        } else {
            flameCounter--;
        }
        super.updatePipes();
        // updates the possitoin of the flames if they are currently getting shot out of the pipe
        if (flames != null) {
            flames.updateFlames();
        }
    }

    /** Used to check if an object has colided witht the flame
     * @param target object that may have colided with the flame.
     * @return boolean whether a collision with the flame as occured.
     */
    public boolean collisoinWithFlame(Colidable target){
        if (flames == null){return false;}
        return flames.hasColsionOccured(target);
    }
}
