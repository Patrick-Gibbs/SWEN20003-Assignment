import bagel.*;
import bagel.Image;
import bagel.util.Vector2;
import bagel.util.Rectangle;

public abstract class PipeSet implements Colidable {
    protected static final double PIPE_GAP = 160;
    private static final Image DEFULT_SPRITE = new Image("res/level/plasticPipe.png");



    protected static final double PIPE_SPRIT_HEIGHT = DEFULT_SPRITE.getHeight();
    public static final double PIPE_SPRIT_WIDTH = DEFULT_SPRITE.getWidth();

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

    public PipeSet(){
        pointsCounted = false;
        topOverhang = HIGH_PIPE_OVERHANG +  Math.random() * (LOW_PIPE_OVERHANG - HIGH_PIPE_OVERHANG);
    }

    //updates position of pipes and also draws them.
    public void updatePipes(){
        pipeSpeed = ShadowFlap.RIGHT_TO_LEFT_SPEED * ShadowFlap.getInstance().getGameSpeed();
        topPipeVector = topPipeVector.add(Vector2.left.mul(pipeSpeed));
        bottomPipeVector = bottomPipeVector.add(Vector2.left.mul(pipeSpeed));

    }

    protected void setPipeVectors(double topOverhang){
        double startingTopPipeY = ShadowFlap.SCREEN_HEIGHT + PIPE_SPRIT_HEIGHT/2.0 - topOverhang;
        double startingBottomPipeY = 0 - PIPE_SPRIT_HEIGHT/2.0  + (600 - topOverhang);
        bottomPipeVector = new Vector2(STARTING_BOTTOM_PIPE_X, startingBottomPipeY);
        topPipeVector = new Vector2(STARTING_TOP_PIPE_X, startingTopPipeY);
    }

    public abstract boolean collisoinWithFlame(Colidable target);

    public Vector2 getBottomPipeVector() {
        return bottomPipeVector;
    }

    public Vector2 getTopPipeVector() {
        return topPipeVector;
    }

    public boolean isPointsCounted() {
        return pointsCounted;
    }

    public void setPointsCounted(boolean pointsCounted) {
        this.pointsCounted = pointsCounted;
    }

    @Override
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

    @Override
    public Rectangle[] getBoundingBoxs() {
        return new Rectangle[]{DEFULT_SPRITE.getBoundingBoxAt(bottomPipeVector.asPoint()), DEFULT_SPRITE.getBoundingBoxAt(topPipeVector.asPoint())};
    }
}


