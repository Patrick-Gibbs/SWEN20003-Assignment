import bagel.Image;
import bagel.util.Rectangle;

/** Represents the Bomb weapon which is used in Level two.
 *  Bombs can be picked up by the bird and fired to destroy all types of pipes.
 *  Once shot Bombs will be fired ahead of the bird for 50 frames before disappearing meaning they have limited range.
 */
public class Bomb extends Weapon{
    private static final Image BOMB_SPRITE = new Image("res/level-1/bomb.png");
    private static final int SHOOT_RANGE = 50;

    /** Creats a Bomb at a specified X starting possition, Y possitoin is generated randomly.
     * @param shotStartXPossition The X starting possitoin of the Bomb.
     */
    public Bomb(double shotStartXPossition){
        super(shotStartXPossition);
    }

    /** Gets the sprite used for Bomb
     * @return Image The Sprite used for the Bomb
     */
    @Override
    protected Image getCurrentSprite(){
        return BOMB_SPRITE;
    }

    /** Checks whether the Bomb has shot past it maxiumum range
     * @return boolean The Bombs maximum range.
     */
    @Override
    public boolean hasWeaponPastMaxRange() {
        return frameCounter > SHOOT_RANGE;
    }
}
