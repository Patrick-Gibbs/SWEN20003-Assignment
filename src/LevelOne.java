import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.util.Point;

import java.util.ArrayList;

/** Encapsulates the game logic for level one.
 */
public class LevelOne extends Level{
    private static final Image BACKGROUND = new Image("res/level-1/background.png");
    private static final String SHOOT_INSTRUCTIONS = "PRESS 's' TO SHOOT";
    private static final String WIN_MESSAGE = "CONGRATULATIONS!";
    private static final int SHOOT_INSTRUCTIONS_GAP = 68;
    private static final int LIVES = 10; //SHOIUDL BE 6 LIVES
    private static final int SCORE_TO_WIN = 30;
    private static final double WEAPON_SPAWN_RATE = 0.4;

    private final ArrayList<Weapon> weapons;


    /** Creates level initialed at the start screen
     */
    public LevelOne(){
        bird = new LevelOneBird();
        pipes = new ArrayList<PipeSet>();
        pipes.add(generatePipe());
        currentGameState = GameState.START_SCREEN;
        healthBar = new HealthBar(LIVES);
        weapons = new ArrayList<Weapon>();

    }

    /** The main update loop for the level, updates the position and instance of all objects in the game as well as
     * drawing them on the screen.
     * @param input User input to control the bird.
     */
    @Override
    public void runLevel(Input input){
        super.runLevel(input);
        generateWeapon();

        if(currentGameState == GameState.GAME_RUNNING) {
            ArrayList<Weapon> weaponsToRemove = new ArrayList<Weapon>();
            ArrayList<PipeSet> pipeSetToRemove = new ArrayList<PipeSet>();

            for (Weapon weapon : weapons) {
                // updates the possitoin and state of weapon
                weapon.updateWeapon((LevelOneBird) bird, input);
                // manage collision with bird, and weapon that can be picked up by bird.
                if (!(weapon.isShot || weapon.isHeldByBird)) {
                    if (weapon.hasColsionOccured(bird)) {
                        //Bird will not pick up weapon if it has a bomb
                        if (Weapon.getWeaponHeldByBird() instanceof Bomb) {
                            // As bird already has bomb, the weapon it colides with is removed
                            weaponsToRemove.add(weapon);
                            // if Bird has rock, it will always pick a weapon up deleting its current weapon
                        } else if (Weapon.getWeaponHeldByBird() instanceof Rock) {
                            weapon.isHeldByBird = true;
                            weaponsToRemove.add(Weapon.getWeaponHeldByBird());
                            Weapon.setWeaponHeldByBird(weapon);
                            // if Bird has no weapon it will pick up rock or bomb
                        } else {
                            weapon.isHeldByBird = true;
                            Weapon.setWeaponHeldByBird(weapon);
                        }
                    }
                }

                // If a weapon is shot and travels beyond its range, of if it exits the screen it is removed from the game.
                if ((weapon.isShot && weapon.hasWeaponPastMaxRange()) || weapon.position.x < 0 - weapon.getCurrentSprite().getWidth() / 2) {
                    weaponsToRemove.add(weapon);
                    // If weapon colides with a pipe.
                } else {
                    for (PipeSet pipe : pipes) {
                        if (weapon.isShot && weapon.hasColsionOccured(pipe)) {
                            // Weapon destroys pipe and scores unless pipe is metal and weapon is rock.
                            if (!(pipe instanceof MetalPipeSet && weapon instanceof Rock)) {
                                System.out.println('C');
                                pipeSetToRemove.add(pipe);
                                score.incrementScore();
                            }
                            // Weapon always removed when colides with pipe
                            weaponsToRemove.add(weapon);
                        }
                    }
                }
            }

            // Removes pipes and weapons that should no longer be in the game
            for (PipeSet pipe : pipeSetToRemove) {
                pipes.remove(pipe);
            }
            for (Weapon weaponToRemove : weaponsToRemove) {
                weapons.remove(weaponToRemove);
            }

        } else if (currentGameState == GameState.GAME_WON){
            drawWinScreen();
        }
    }
    @Override
    protected void updateGameState(Input input, Bird bird, ArrayList<PipeSet> pipes){
        super.updateGameState(input, bird, pipes);
        if (currentGameState == GameState.GAME_WON) {
            if (input.isDown(Keys.ESCAPE)) {
                System.exit(0);
            }
        }
    }

    @Override
    protected void drawBackground() {
        // Draws city background unless game is lost, in which case we draw the default background.
        if(currentGameState == GameState.GAME_LOST){
            DEFUALT_BACKGROUND.draw(ShadowFlap.SCREEN_WIDTH / 2.0, ShadowFlap.SCREEN_HEIGHT / 2.0);
        } else {
            BACKGROUND.draw(ShadowFlap.SCREEN_WIDTH / 2.0, ShadowFlap.SCREEN_HEIGHT / 2.0);

        }
    }

    @Override
    protected void drawStartScreen() {
        super.drawStartScreen();
        Point shootMessageLocation = new Point(ShadowFlap.MESSAGE_LOCATION.x -
                ShadowFlap.getInstance().MESSAGE_FONT.getWidth(SHOOT_INSTRUCTIONS)/2,
                ShadowFlap.MESSAGE_LOCATION.y + SHOOT_INSTRUCTIONS_GAP);
        ShadowFlap.getInstance().MESSAGE_FONT.drawString(SHOOT_INSTRUCTIONS, shootMessageLocation.x, shootMessageLocation.y);
    }

    @Override
    protected boolean isLevelUp(Score score) {
        return score.getCurrent_score() >= SCORE_TO_WIN;
    }

    @Override
    protected void drawWinScreen() {
        ShadowFlap.getInstance().MESSAGE_FONT.drawString(WIN_MESSAGE, MESSAGE_LOCATION.x - ShadowFlap.getInstance().MESSAGE_FONT.getWidth(WIN_MESSAGE)/2.0,
                MESSAGE_LOCATION.y);
        score.drawFinalScore();
    }

    protected void generateWeapon(){
        if (PIPE_FREQUENCEY/2 == frameCounter && Math.random() > 1 - WEAPON_SPAWN_RATE){
            System.out.println(weapons.toArray().length);
            if (Math.random() > 0.5){
                weapons.add(new Bomb(ShadowFlap.SCREEN_WIDTH + PipeSet.PIPE_SPRIT_WIDTH/2));
            } else {
                weapons.add(new Rock(ShadowFlap.SCREEN_WIDTH + PipeSet.PIPE_SPRIT_WIDTH/2));
            }
        }
    }

    protected PipeSet generatePipe(){
        if (Math.random() > 0.5) {
            return new PlasticPipeSet(Levels.LEVEL_ONE);
        } else {
            return new MetalPipeSet();
        }
    }

    protected Bird generateBird(){
        return new LevelOneBird();
    }
}
