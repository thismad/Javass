package platform.game;

import platform.util.Box;
import platform.util.BufferedLoader;
import platform.util.DefaultLoader;
import platform.util.Input;
import platform.util.Loader;
import platform.util.Sprite;
import platform.util.Output;
import platform.util.Vector;
import platform.util.FileLoader;
import platform.util.View;
/**
 * Simple solid actor that does nothing.
 */
public class Block extends Actor implements Comparable<Actor>{
   private Box box;
   private Sprite sprite;
   
   
   public Block(Vector vector, Vector vector2) {
	   super();
	   this.box=new Box(vector,vector2);
	   this.sprite=getLoader().getSprite("box.empty");
   }
   
   public int getPriority() {
	   return 0;
   }
   
   public Box getBox() {
	   return box;
   }
   
   public Sprite getSprite() {
	   return sprite;
   }
   
   public void interact(Actor actor) {}
   
   public void draw(Input input, Output output) {
	   View view = new View (input, output);
	   super.draw(input,output);
	   view.drawSprite(sprite, box);
   }
}
