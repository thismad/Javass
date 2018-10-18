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
public class Block extends Actor{
   private Box box;
  
   
   
   public Block(double a, double b, double c, double d) {
	   super("box.empty");
	   this.box=new Box(new Vector(a,b),new Vector(c,d));  
   }
   
   public int getPriority() {
	   return 0;
   }
   
   public Box getBox() {
	   return box;
   }

   public void interact(Actor actor) {}

   @Override
   public void draw(Input input, Output output) {
	   super.draw(input, output);
	   output.drawSprite(getSprite(), getBox());
   }

   public boolean isSolid() {
	   return true;
   }
}
