import bagel.util.Rectangle;

/** Objects that extend this interface can collide with eachother, meaning there sprite overlap at their current
 * position
 */
public interface Colidable {
    /** checks if a colision has occured between a Colidable object and Colidable target
     * @param target Colisoin check with this target
     * @return boolean Whether a colision has occured
     */
    boolean hasColsionOccured(Colidable target);

    /** Get the bounding boxes of a colidable object
     * @return Rectangle[] An array of Recatangle objects, repressenting the all bounding boxes of an object.
     * Array returned as some objects may have more than one bounding box e.g. PipeSets
     */
    Rectangle[] getBoundingBoxs();
}
