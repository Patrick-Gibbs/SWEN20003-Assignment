import bagel.Image;

public class LevelOneBird extends Bird {
    private static final String BIRD_WING_UP_PATH = "res/level-1/birdWingUp.png";
    private static final String BIRD_WING_DOWN_PATH = "res/level-1/birdWingDown.png";
    private static final Image BIRD_WING_UP_SPRITE = new Image(BIRD_WING_UP_PATH);
    private static final Image BIRD_WING_DOWN_SPRITE = new Image(BIRD_WING_DOWN_PATH);

    private Weapon currentWeapon;

    LevelOneBird(){
        super();
    }
    /* Draws the bird at the on the screen at its current position, every 10 times the alternative sprite is used
   to give the flapping animation */
    @Override
    public Image getCurrentBirdSprite() {
        if (animationCounter == FLAP_ANIMATION_FREQUENCEY - 1) {
            return BIRD_WING_UP_SPRITE;
        } else {
            return BIRD_WING_DOWN_SPRITE;
        }
    }
    public Weapon getCurrentWeapon(){
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon weapon){
        currentWeapon = weapon;
    }

}
