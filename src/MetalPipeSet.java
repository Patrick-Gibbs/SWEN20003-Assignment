import bagel.Image;
public class MetalPipeSet extends PipeSet {
    private final Image PIPE_SPRIT = new Image("res/level-1/steelPipe.png");
    double bottomOverhang = Math.random() * (ShadowFlap.SCREEN_HEIGHT - PIPE_GAP);
    private int flameCounter;
    private Flame flames;
    private final int FLAME_CYCLE = 20;

    MetalPipeSet(){
        super();
        setPipeVectors(topOverhang);
        flameCounter = (int) (Math.random()*FLAME_CYCLE);
        flames = new Flame(this);
    }

    //updates position of pipes and also draws them.
    public void updatePipes() {
        PIPE_SPRIT.draw(bottomPipeVector.x, bottomPipeVector.y);
        PIPE_SPRIT.draw(topPipeVector.x, topPipeVector.y, BOTTOM_PIPE_ROTATION);
        if (flameCounter == FLAME_CYCLE) {
            flameCounter = 0;
            if (flames == null) {
                flames = new Flame(this);
            } else {
                flames = null;
            }
        } else {
            flameCounter++;
        }
        super.updatePipes();
        if (flames != null) {
            flames.updateFlames();
        }
    }

    public boolean collisoinWithFlame(Colidable target){
        if (flames == null){return false;}
        return flames.hasColsionOccured(target);
    }


}
