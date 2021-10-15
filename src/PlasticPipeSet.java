import bagel.Image;

/** Represents plastic pipes the bird must pass through to score points, used in both levels, in level one can
 * be destroyed by Rock or Bomb.
 */
public class PlasticPipeSet extends PipeSet {
    //Defines the distance end of a pipe from the top/bottem of the screen
    private static final double MID_PIPE_OVERHANG = 300;
    private final Image PIPE_SPRIT = new Image("res/level/plasticPipe.png");

    /** Creates a set of pipes. inital y locations relveant to the current level, and thus required spawning pattern.
     * @param currentLevel The current level the game is on, informing the spawning pattern of the pipes.
     */
    public PlasticPipeSet(Levels currentLevel) {
        super();
        if (currentLevel == Levels.LEVEL_ZERO){
            double randDouble = Math.random();
            // 1/3 change of pipes spawining at the three distinct posstitions.
            if (randDouble < 1/3.0){
                topOverhang = HIGH_PIPE_OVERHANG;
            }
            else if (randDouble < 2/3.0){
                topOverhang = MID_PIPE_OVERHANG;
            }
            else{
                topOverhang = LOW_PIPE_OVERHANG;
            }
        }
        setPipeVectors(topOverhang);
    }

    //updates position of pipes and also draws them.
    public void updatePipes(){
        PIPE_SPRIT.draw(bottomPipeVector.x, bottomPipeVector.y);
        PIPE_SPRIT.draw(topPipeVector.x, topPipeVector.y, BOTTOM_PIPE_ROTATION);
        super.updatePipes();
    }

    /** Checks if a collision has occured between a target with the flames, as these are plastic pipes they are no
     * flames thus this method will always return false.
     * @param target object they may be colliding with flame.
     * @return boolean if a colloision has occured with the flame assoitated with this set of pipes.
     */
    @Override
    public boolean collisoinWithFlame(Colidable target) { return false; }
}
