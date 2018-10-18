/*
 * Author: Mathis Dehez
 * Date: 18 oct. 2018
*/

package platform.game;

import platform.util.*;

public class Player extends Actor{
	private Vector position;
	private Vector velocity;
	private final double SIZE = 0.7;
	
	public Player (double a, double b, double c, double d) {
		super("blocker.happy");
		Vector pos = new Vector(a,b);
		Vector vel = new Vector(c,d);
		position=pos;
		velocity=vel;
	}
	
	public int getPriority() {
		return 1000;
	}
	
	public boolean isSolid() {
		return true;
	}
	
	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}
	
	
	public void update(Input input) {
		super.update(input);
		double delta = input.getDeltaTime();
		velocity = velocity.add(getWorld().getGravity().mul(delta));
		position = position.add(velocity.mul(delta));
	}
	
	public void draw(Input input,Output output) {
		super.draw(input, output);
		output.drawSprite(getSprite(), getBox());
	}
	
	public void interact(Actor other) {
		super.interact(other);
		if(other.isSolid()) {
			Vector delta= other.getBox().getCollision(getBox());
			if(delta != null) {
				position = position.add(delta);
				if  (delta.getY() != 0.0)
					velocity=new Vector (0.0, velocity.getY());
				if (delta.getX() != 0.0)
					velocity = new Vector (velocity.getX(), 0.0);
			}
		}
	}
}
