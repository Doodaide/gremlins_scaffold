package gremlins;
import java.util.ArrayList;
import processing.core.PImage;
/**
 * Projectile class: An abstract class that extends from mobile entities 
 * The fireball and slimeball classes are extended from this class
 */

public abstract class Projectile extends Mobile{

    /**
     * The PImage that corresponds to what is displayed onscreen during the draw method. 
     */
    protected PImage sprite;

    /**
     * Constructor for Projectile object 
     * @param x x coordinate of projectile 
     * @param y y coordinate of projectile 
     * @param sprite The PImage that is drawn onscreen 
     */
    public Projectile(int x, int y, PImage sprite) {
        super(x, y, 4, 0, 0);
        this.sprite = sprite;
       
    }

    /**
     * A getter method that returns the collision entity of the specific projectile object 
     * @return A mobile that the projectile has 'collided with'
     */
    public Mobile getCollisionEntity(){
        return this.collisionEntity;
    }

    public void hit(){
        this.alive = false;
    }
    
    // Block collision (handeled in Mob) 

    // Mob collision (not handeled in mob)
    public boolean mobCollision(int x, int y, ArrayList<Mobile> a){
        for(Mobile i : a){
            if (((x >= i.getX() && x <= i.getX() + i.WIDTH)
                &&
                (y >= i.getY() && y <= i.getY() + i.HEIGHT))){
                    this.collisionEntity = i;
                    return true;
                }
        
        }
        return false;
    
    }

}