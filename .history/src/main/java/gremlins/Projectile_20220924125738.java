package gremlins;

import processing.core.PImage;

/**
 * Projectile
 */

public abstract class Projectile extends Mobile{
    protected PImage sprite;
    public Projectile(int x, int y, PImage sprite) {
        super(x, y, 4, 0, 0);
        this.sprite = sprite;
       
    }

    // Block collision (handeled in Mob) 

    // Mob collision (not handeled in mob)
    public boolean mobCollision(int x, int y, App app){
        for(Mobile i : app.getMobiles()){
            if (((xCoord >= i.getX() && xCoord <= i.getX() + i.WIDTH)
                &&
                (yCoord >= i.getY() && yCoord <= i.getY() + i.HEIGHT))
                ){
        }
        return false;
    }
    

    
}