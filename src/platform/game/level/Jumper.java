package platform.game.level;
import platform.util.*;
import platform.game.*;
public class Jumper extends Actor{
   
    private Vector position;
    private double cooldown;
    Sprite sprite;
    
    public Jumper(Vector position) {
        this.position=position;
        sprite=getSprite("jumper.normal");
    }
    
    public void draw(Input input,Output output) {
        super.draw(input, output);
        output.drawSprite(sprite, new Box(position,2,2));
    }
    
    public void update(Input input) {
        super.update(input);
        cooldown -= input.getDeltaTime();
        if(cooldown>0) {
            sprite = getSprite("jumper.normal");
        }
    }
    
    public void interact (Actor other) {
        super.interact(other);
        if (cooldown<=0 && getBox().isColliding(other.getBox())) {
            Vector below = new Vector(position.getX(), position.getY() - 1.0);
            if(other.hurt(this, Damage.AIR, 10.0, below))
                cooldown = 0.5;
            sprite = getSprite("jumper.extended");
        }
    }
    
    public int getPriority() {
        return 100;
    }
}
