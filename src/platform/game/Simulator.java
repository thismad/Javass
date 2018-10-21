package platform.game;

import platform.game.level.Level;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import platform.util.Box;
import platform.util.BufferedLoader;
import platform.util.DefaultLoader;
import platform.util.FileLoader;
import platform.util.SortedCollection;
import platform.util.Input;
import platform.util.Loader;
import platform.util.Output;
import platform.util.Vector;
import platform.util.View;
import platform.util.Sprite;
import platform.game.Block;
import platform.game.Fireball;

/**
 * Basic implementation of world, managing a complete collection of actors.
 */
public class Simulator implements World{

    private Loader loader;
    private Vector currentCenter;
    private double currentRadius;
    private Vector expectedCenter = Vector.ZERO;
    private double expectedRadius = 10.0;
    private SortedCollection<Actor> actors;
    private List<Actor> registered, unregistered;
    private Level next;
    private boolean transition = false;
    private Level currentLevel;
   
    
    /**
     * Create a new simulator.
     * @param loader associated loader, not null
     * @param args level arguments, not null
     */
	public Simulator(Loader loader, String[] args) {
        if (loader == null)
            throw new NullPointerException();
        this.loader = loader;
        currentCenter=Vector.ZERO;
        currentRadius=10.0;
        registered=new ArrayList<Actor>();
        unregistered=new ArrayList<Actor>();
        actors = new SortedCollection<Actor>();
        currentLevel = Level.createDefaultLevel();
        currentLevel.register(this);
	}
	
   // Simulate before the physical actions from actors
	public void preUpdate(Input input, Output output) {
		for (Actor a : actors) {
			a.preUpdate(input);
		}
	}
	//Simulate after the drawings
	public void postUpdate(Input input,Output output) {
		for (Actor a : actors) {
			a.postUpdate(input,output);
		}
	}
	/**
     * Simulate a single step of the simulation.
     * @param input input object to use, not null
     * @param output output object to use, not null
     */
	public void update(Input input, Output output) {

		View view = new View (input, output);
		view.setTarget(expectedCenter, currentRadius);
		preUpdate(input,output);
		
		for(Actor actor : actors) {
			for (Actor other : actors) {
				if (actor.getPriority()>other.getPriority()) actor.interact(other);
			}
		}
	//	 Add registered actors
		for (int i =0; i<registered.size();i++) {
			Actor actor = registered.get(i);
			actor.register(this);
			if (!actors.contains(actor)) {
				actors.add(actor);
			}
		}
		registered.clear();


		// Apply update
		for( Actor a : actors) a.update(view);
		
		// Draw everything
		for(Actor a : actors.descending()) a.draw(view,view);
		
		postUpdate(input,output); 
		
		// Remove unregistered actors
		for (int i=0; i<unregistered.size();i++) {
			Actor actor = unregistered.get(i);
			actors.remove(actor);
		//	retrait de son attribut world
			actor.unregister(this);
		}
		unregistered.clear();
		
		if(transition) {
		    if (next==null) {
		        next=Level.createDefaultLevel();
		    }
		    Level level = next;
		    transition=false;
		    next=null;
		    actors.clear();
		    registered.clear();
		    unregistered.clear();
		    register(level);
		}
	}
	
	/** @Override*/
	
	public void setView(Vector center, double radius) {
	    if (center==null) {
	        throw new NullPointerException();
	    }
	    if (radius<=0) {
	        throw new IllegalArgumentException("radius must be positive!");
	    }
	    expectedCenter=center;
	    expectedRadius=radius;
	    
	}
	 

    @Override
    public Loader getLoader() {
        return loader;
    }
    
    @Override 
    public void register(Actor actor) {
    actor.register(this);
    	registered.add(actor);
    }
    
    @Override 
    public void unregister(Actor actor) {
    	unregistered.add(actor);
    }
    
    public void setNextLevel(Level level) {
        this.next=level;
    }
    
    public void nextLevel() {
        transition=true;
    }

    
}
