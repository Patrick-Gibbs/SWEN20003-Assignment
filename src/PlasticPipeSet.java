import bagel.Image;

public class PlasticPipeSet extends PipeSet {
    //Defines the distance end of a pipe from the top/bottem of the screen
    private static final double MID_PIPE_OVERHANG = 300;


    private final Image PIPE_SPRIT = new Image("res/level/plasticPipe.png");

    public PlasticPipeSet(Levels currentLevel) {
        super();
        if (currentLevel == Levels.LEVEL_ZERO){
            double randDouble = Math.random();
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

    public Image getPIPE_SPRIT() {
        return PIPE_SPRIT;
    }

    @Override
    public boolean collisoinWithFlame(Colidable target) { return false; }
}
