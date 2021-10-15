import bagel.Image;
/** Rock is a weapon in the game which has limited range and can be used to destroy plastic pipes by the bird. */
public class Rock extends Weapon{
    private static final Image ROCK_SPRITE = new Image("res/level-1/rock.png");
    // Shooting range between rock and bomb is polymorphic.
    private static final int SHOOT_RANGE = 25;

    /** Creates a new rock at a specified x posstion, y posstion if generated randomly on the screen.
     * @param startXPossition specifed X posstion to create rock.
     */
    public Rock(double startXPossition){
        super(startXPossition);
    }

    @Override
    protected Image getCurrentSprite(){
        return ROCK_SPRITE;
    }

    /** Checks if the rock has passed it maxium shooting rnage
     * @return boolean if the rock has pasted its maximum shooting range.
     */
    @Override
    public boolean hasWeaponPastMaxRange() {
        return frameCounter > SHOOT_RANGE;
    }

}
