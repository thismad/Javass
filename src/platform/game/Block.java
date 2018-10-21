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
   private Sprite sprite;

  
   
   public Block(Box box, Sprite sprite) {
       this.box=box;
       this.sprite=sprite;
   }
   
   public Block(Sprite sprite,double a, double b, double c, double d) {
	   this.box=new Box(new Vector(a,b),new Vector(c,d));
	   this.sprite=sprite;
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
	   output.drawSprite(sprite,box);
   }

   public boolean isSolid() {
	   return true;
   }
}
