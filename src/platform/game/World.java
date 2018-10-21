package platform.game;

import platform.util.Box;
import platform.util.Loader;
import platform.util.Vector;
import platform.game.level.*;
/**
 * Represents an environment populated by actors.
 */
public interface World {

    /** @return associated loader, not null */
    public Loader getLoader();
    
    /*
     * Set viewport location and size
     * @param center viewport center, not null
     * @param radius viewport radius, positive
     */
    public void setView(Vector center, double radius);
    
    public void register(Actor actor);
    
    public void unregister(Actor actor);
    
    default public Vector getGravity() {
    	return new Vector (0.0,-9.81);
    }
    
    public void setNextLevel(Level level);
    
    public void nextLevel();
}
