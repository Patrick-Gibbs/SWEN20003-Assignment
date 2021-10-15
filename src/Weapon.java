import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Rectangle;
import bagel.util.Vector2;

import java.util.ArrayList;

public abstract class Weapon implements Colidable {
    protected Vector2 position;
    protected boolean isShot = false;
    protected boolean isHeldByBird = false;
    protected int frameCounter = 0;

    private static Weapon WeaponHeldByBird;

    public static void setWeaponHeldByBird(Weapon weaponHeldByBird) {
        WeaponHeldByBird = weaponHeldByBird;
    }

    public static Weapon getWeaponHeldByBird() {
        return WeaponHeldByBird;
    }

    private static final double WEAPON_Y_UPPER_BOUND = 500;
    private static final double WEAPON_Y_LOWER_BOUND = 100;

    private static final double SHOOT_SPEED = 5;

    public Weapon(double shotStartXPossition){
         double startYPossition = (WEAPON_Y_LOWER_BOUND + Math.random() * (WEAPON_Y_UPPER_BOUND -
                 WEAPON_Y_LOWER_BOUND));
         position = new Vector2(shotStartXPossition, startYPossition);
    }


    public abstract boolean hasWeaponPastMaxRange();
    protected abstract Image getCurrentSprite();

    public void updateWeapon(LevelOneBird bird, Input input) {
        if (!(isShot || isHeldByBird)) {
            position = position.add(Vector2.left.mul(ShadowFlap.RIGHT_TO_LEFT_SPEED *
                    ShadowFlap.getInstance().getGameSpeed()));

        } else if (isHeldByBird){
            if (input.isDown(Keys.S)){
                WeaponHeldByBird = null;
                isShot = true;
                isHeldByBird = false;
            }
            // places weapon at birds beak which is the center right of the birds possition.
            position = bird.getBirdPossionVector();
            position = position.add(Vector2.right.mul(bird.getBoundingBoxs()[0].right() - position.asPoint().x));
        } else if (isShot){
            position = position.add(Vector2.right.mul(SHOOT_SPEED * ShadowFlap.getInstance().getGameSpeed()));
            frameCounter++;
        }
        drawWeapon();
    }


    @Override
    public boolean hasColsionOccured(Colidable target) {
        for(Rectangle currentRect : target.getBoundingBoxs()){
            if(this.getCurrentSprite().getBoundingBoxAt(position.asPoint()).intersects(currentRect)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Rectangle[] getBoundingBoxs() {
        return new Rectangle[]{this.getCurrentSprite().getBoundingBoxAt(position.asPoint())};
    }

    public void drawWeapon() {
        this.getCurrentSprite().draw(position.x, position.y);
    }

}