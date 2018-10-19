package platform.game;

import platform.util.Box;
import platform.util.BufferedLoader;
import platform.util.DefaultLoader;
import platform.util.FileLoader;
import platform.util.Input;
import platform.util.Loader;
import platform.util.Vector;
import platform.util.Output;
import platform.util.Sprite;
import platform.util.View;
import platform.util.Input;
import platform.util.Output;

/**
 * Base class of all simulated actors, attached to a world.
 */
public abstract class Actor implements Comparable<Actor> {
	private World world;
	private Sprite sprite;
	
	private Loader loader = new BufferedLoader(new FileLoader("res/", DefaultLoader.INSTANCE));
	
	public Actor(String nomSprite) {
		this.sprite=loader.getSprite(nomSprite);
	}
  //pour évoluer au cours du temps : 
	public void update(Input input) {}
	
	public void postUpdate(Input input, Output output) {}
	
  //pour être dessiné
	public void draw(Input input, Output output) {}
	
	public void preUpdate(Input input) {}
	/**
	 * @Override
	 * @param other
	 * @return int Priority
	 */
	public int compareTo(Actor other) {
		int actorP=this.getPriority();
		int actorP2=other.getPriority();
		if (actorP>actorP2)return -1;
		else if(actorP==actorP2) return 0;
		else return 1;
	}
	
	abstract public int getPriority();  
	
	public Loader getLoader() {
		return loader;
	}
	//STATE
	public boolean isSolid() {
		return false;
	}
	//By default
	public Box getBox() {
		return null;
	}
	
	protected Sprite getSprite() {
		return sprite;
	}
	/**
	 * Quand l'acteur s'enregistre dans le monde on lui définit son World
	 * @param world
	 */
	public void register(World world) {
		this.world = world;
	}
	
	public void unregister(World world) {
		world = null;
	}
	
	//return position of the Actor
	public Vector getPosition() {
		Box box = getBox();
		if (box==null) return null;
		return box.getCenter();
	}
	
	protected World getWorld() {
		return world;
	}
	public void interact(Actor other) {}
	

	
}