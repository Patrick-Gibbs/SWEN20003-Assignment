import bagel.DrawOptions;
import bagel.Image;
import bagel.util.Rectangle;
import bagel.util.Vector2;

public class Flame implements Colidable{
    private static final DrawOptions FLAME_ROTATION = new DrawOptions().setRotation(Math.PI);
    private static final Image FLAME_SPRITE = new Image("res/level-1/flame.png");
    private static final int FLAME_DISTANCE_ABOVE_PIPE = 10;
    private Vector2 topFlameLocation;
    private Vector2 bottomFlameLocation;

    /** Create a new set of flames initlised above/below a given pipe set
     * @param pipeSet Set of pipes flames will be initialised above/below.
     */
    Flame(PipeSet pipeSet){
        topFlameLocation = new Vector2(pipeSet.topPipeVector.x, pipeSet.getBoundingBoxs()[0].bottom() +
                FLAME_DISTANCE_ABOVE_PIPE);
        bottomFlameLocation = new Vector2(pipeSet.bottomPipeVector.x, pipeSet.getBoundingBoxs()[1].top() -
                FLAME_DISTANCE_ABOVE_PIPE);
    }

    /** Updates the location of the set of flames
     */
    public void updateFlames(){
        drawFlames();
        topFlameLocation = topFlameLocation.add(Vector2.left.mul(ShadowFlap.RIGHT_TO_LEFT_SPEED *
                ShadowFlap.getInstance().getGameSpeed()));
        bottomFlameLocation = bottomFlameLocation.add(Vector2.left.mul(ShadowFlap.RIGHT_TO_LEFT_SPEED *
                ShadowFlap.getInstance().getGameSpeed()));
    }

    /** Draw the set of flames on the screen at their current location.
     */
    public void drawFlames(){
        FLAME_SPRITE.draw(topFlameLocation.x, topFlameLocation.y);
        FLAME_SPRITE.draw(bottomFlameLocation.x ,bottomFlameLocation.y, FLAME_ROTATION);
    }

    /** Checks if either flame has colided with a specfied object
     * @param target Colisoin checked with this target
     * @return boolean Whther a colison has occured with this target.
     */
    @Override
    public boolean hasColsionOccured(Colidable target) {
        for(Rectangle targetRect : target.getBoundingBoxs()){
            for(Rectangle thisRect : this.getBoundingBoxs()){
                if (targetRect.intersects(thisRect)){
                    return true;
                }
            }
        }
        return false;
    }

    /** Gets the bounding boxes of both flames.
     * @return Recatangle[] an array representing the bounding boxes of the upper and lower flame.
     */
    @Override
    public Rectangle[] getBoundingBoxs() {
        return new Rectangle[]{FLAME_SPRITE.getBoundingBoxAt(topFlameLocation.asPoint()),FLAME_SPRITE
                .getBoundingBoxAt(bottomFlameLocation.asPoint())};
    }
}
