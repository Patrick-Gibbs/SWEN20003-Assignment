import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/** Represnts wepeaon in shaddowflap game which can be picked up by the bird and used to destroy pipes. */
public abstract class Weapon implements Colidable {
    protected Vector2 position;
    // used to keep track of the state of the weapon
    protected boolean isShot = false;
    protected boolean isHeldByBird = false;
    protected int frameCounter = 0;

    // Bird can only hold one weapon at a time.
    private static Weapon WeaponHeldByBird;

    //bounds for weapon spawning
    private static final double WEAPON_Y_UPPER_BOUND = 500;
    private static final double WEAPON_Y_LOWER_BOUND = 100;

    // The speed at which the weapon travels when shot by defult.
    private static final double SHOOT_SPEED = 5;

    /** Creates a new weapon a at a spefied X possition, the y posstion is generated randomly within the bounds. */
    public Weapon(double startXPossition){
        double startYPossition = (WEAPON_Y_LOWER_BOUND + Math.random() * (WEAPON_Y_UPPER_BOUND -
                WEAPON_Y_LOWER_BOUND));
        position = new Vector2(startXPossition, startYPossition);
    }

    /** checking if a weapon has been shot bast is maxiumum range
     * @return boolean if the weapon has been shot past its max range.
     */
    public abstract boolean hasWeaponPastMaxRange();

    /** gets the sprite of the weapon
     * @return Image the sprite of the weapon.
     */
    protected abstract Image getCurrentSprite();

    /** sets the weapon held by the bird
     * @param weaponHeldByBird The weapon to be held by the bird.
     */
    public static void setWeaponHeldByBird(Weapon weaponHeldByBird) {
        WeaponHeldByBird = weaponHeldByBird;
    }

    /** gets the weapon currently held by the bird
     * @return Weapon The weapon currently held by the bird.
     */
    public static Weapon getWeaponHeldByBird() {
        return WeaponHeldByBird;
    }


    /** updates the weapon location as its state (shot, held by bird). also draws the weapon.
     * @param bird The bird in the game, used to draw at it beak if bird has weapon.
     * @param input User input to shoot the weapon.
     */
    public void updateWeapon(LevelOneBird bird, Input input) {
        // If weapon has not been picked up or shot it moves right to left.
        if (!(isShot || isHeldByBird)) {
            position = position.add(Vector2.left.mul(ShadowFlap.RIGHT_TO_LEFT_SPEED *
                    ShadowFlap.getInstance().getGameSpeed()));

        // If bird holds the weapon it can be shot and is drawn at the birds beak.
        } else if (isHeldByBird){
            if (input.isDown(Keys.S)){
                WeaponHeldByBird = null;
                isShot = true;
                isHeldByBird = false;
            }
            // places weapon at birds beak which is the center right of the birds possition.
            position = bird.getBirdPossionVector();
            position = position.add(Vector2.right.mul(bird.getBoundingBoxs()[0].right() - position.asPoint().x));
        // If the weapon is shot it moves right to left. collision handled in level class.
        } else {
            position = position.add(Vector2.right.mul(SHOOT_SPEED * ShadowFlap.getInstance().getGameSpeed()));
            frameCounter++;
        }
        drawWeapon();
    }

    /** Checks if collision has occured with Weapon and a given target
     * @param target Colisoin check with this target.
     * @return boolean whether collision has occurred.
     */
    @Override
    public boolean hasColsionOccured(Colidable target) {
        for(Rectangle currentRect : target.getBoundingBoxs()){
            if(this.getCurrentSprite().getBoundingBoxAt(position.asPoint()).intersects(currentRect)){
                return true;
            }
        }
        return false;
    }

    /** Gets the bounding box of the weapon
     * @return Rectangle[] array containing the bounding box of the weapon.
     */
    @Override
    public Rectangle[] getBoundingBoxs() {
        return new Rectangle[]{this.getCurrentSprite().getBoundingBoxAt(position.asPoint())};
    }

    private void drawWeapon() {
        this.getCurrentSprite().draw(position.x, position.y);
    }

}