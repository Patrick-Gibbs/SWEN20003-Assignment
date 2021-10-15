import bagel.Image;

public class Rock extends Weapon{
    private static final Image ROCK_SPRITE = new Image("res/level-1/rock.png");
    private static final int SHOOT_RANGE = 25;

    public Rock(double shotStartXPossition){
        super(shotStartXPossition);
    }

    @Override
    protected Image getCurrentSprite(){
        return ROCK_SPRITE;
    }


    @Override
    public boolean hasWeaponPastMaxRange() {
        return frameCounter > SHOOT_RANGE;
    }

}
