package platform.game;

import platform.game.level.Level;
import java.util.ArrayList;
import java.util.List;
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
        actors.add(new Block(new Vector(-4,-1),new Vector(4,0)));
	}
	
    //Simulate before the physical actions from actors
	public void preUpdate(Input input, Output output) {
		
	}
	//Simulate after the drawings
	public void postUpdate(Input input, Output output) {
	}
	/**
     * Simulate a single step of the simulation.
     * @param input input object to use, not null
     * @param output output object to use, not null
     */
	public void update(Input input, Output output) {
		double factor = 0.001;
		currentCenter = currentCenter.mul(1.0 - factor).add(expectedCenter.mul(factor));
		currentRadius = currentRadius * (1.0 - factor) + expectedRadius * factor;
		View view = new View (input, output);
		
		preUpdate(input,output);
		
		for(Actor actor : actors) {
			for (Actor other : actors) {
				if (actor.getPriority()>other.getPriority()) actor.interact(other);
			}
		}
		
		// Apply update
		for( Actor a : actors) a.update(view);
		
		// Draw everything
		for(Actor a : actors.descending()) a.draw(view,view);
		
		postUpdate(input,output); 
		
		// Add registered actors
		for (int i =0; i<registered.size();i++) {
			Actor actor = registered.get(i);
			if (!actors.contains(actor)) {
				actors.add(actor);
			}
		}
		registered.clear();
		
		// Remove unregistered actors
		for (int i=0; i<unregistered.size();i++) {
			Actor actor = unregistered.get(i);
			actors.remove(actor);
		}
		unregistered.clear();
		
		
		
		view.setTarget(currentCenter, currentRadius);
		/*Block b1=new Block(new Vector(-4,-1),new Vector(4,0));
		Block b2=new Block(new Vector(-2,0),new Vector(-1,1));
		b1.draw(view,view);
		b2.draw(view,view);
		*/

		if (view.getMouseButton(1).isPressed()) setView(view.getMouseLocation(), 10.0);
        
      
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
    	registered.add(actor);
    }
    
    @Override 
    public void unregister(Actor actor) {
    	unregistered.add(actor);
    }

    
}
