/*
 * Author: Mathis Dehez
 * Date: 18 oct. 2018
*/

package platform.game;

import platform.util.*;
import java.awt.event.KeyEvent;


public class Player extends Actor{
	private Vector position;
	private Vector velocity;
	private final double SIZE = 0.7;
	private boolean colliding;
	private final double maxHealth = 5;
	private double health;
	
	public Player (double a, double b, double c, double d) {
		Vector pos = new Vector(a,b);
		Vector vel = new Vector(c,d);
		position=pos;
		velocity=vel;
		health=maxHealth;
		
	}
	
	public int getPriority() {
		return 50;
	}

	public boolean isSolid() {
		return true; 
	}

	public Box getBox() {
		return new Box(position, SIZE, SIZE);
	}
	
	public void setHealth(double h) {
	    this.health=health + h;
	}
	
	public void preUpdate(Input input) {
		super.preUpdate(input);
		colliding = false;
	}
	
	public void postUpdate(Input input, Output output) {
		super.postUpdate(input, output);
		getWorld().setView(position, 8);
	}

	
//********************************************************************** DEBUT UPDATE
	public void update(Input input) {
		super.update(input);
		double maxSpeed = 4.0;
		
		if (colliding) {
			double scale = Math.pow(0.001,  input.getDeltaTime());
			velocity = velocity.mul(scale);
		}
		
		if (input.getKeyboardButton(KeyEvent.VK_RIGHT).isDown()) {
			if(velocity.getX() < maxSpeed) {
				double increase = 60.0*input.getDeltaTime();
				double speed = velocity.getX() + increase;
				if (speed > maxSpeed) {
					speed = maxSpeed;
				}
				velocity = new Vector(speed, velocity.getY());			
			}
		}
		if (input.getKeyboardButton(KeyEvent.VK_LEFT).isDown()) {
			if (velocity.getX() <= maxSpeed) {
				double increase =  60.0*input.getDeltaTime();
				double speed = velocity.getX() - increase;
				if (speed < - maxSpeed) {
					speed = - maxSpeed;
				}

				velocity = new Vector(speed, velocity.getY());
			}
		}
		
		if (input.getKeyboardButton(KeyEvent.VK_UP).isPressed() && colliding)
			velocity = new Vector(velocity.getX(), 7.0);
		
		double delta = input.getDeltaTime();
		velocity = velocity.add(getWorld().getGravity().mul(delta));
		position = position.add(velocity.mul(delta));
		
		if(input.getKeyboardButton(KeyEvent.VK_SPACE).isPressed()) {
		    Vector v = velocity.add(velocity.resized(2));
		    Fireball f = new Fireball(position,v,this);
		    getWorld().register(f);;
		}
	}
 //********************************************************************** FIN UPDATE

	public void draw(Input input,Output output) {
		super.draw(input, output);
		output.drawSprite(getWorld().getLoader().getSprite("blocker.happy"), getBox());
	}
	
	public boolean hurt(Actor instigator, Damage type, double amount, Vector location) {
	    switch(type){
	    case FIRE : setHealth(-amount);
	    case AIR : velocity = getPosition().sub(position).resized(amount);
	    return true;
	    default : return false;
	    }
	}

	public void interact(Actor other) {
		super.interact(other);
		if(other.isSolid()) {
			Vector delta= other.getBox().getCollision(getBox());
			if(delta != null) {
				colliding=true;
				position = position.add(delta);
				if  (delta.getX() != 0.0)
					velocity=new Vector (0.0, velocity.getY());
				if (delta.getY() != 0.0)
					velocity = new Vector (velocity.getX(), 0.0);
			}
		}
	}
}
