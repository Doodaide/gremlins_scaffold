package gremlins;

import java.util.ArrayList;

import processing.core.PImage;

/**
 * Projectile
 */

public abstract class Projectile<T> extends Mobile{
    protected PImage sprite;
    public Mobile collisionEntity;
    public Projectile(int x, int y, PImage sprite) {
        super(x, y, 4, 0, 0);
        this.sprite = sprite;
       
    }

    // Block collision (handeled in Mob) 

    // Mob collision (not handeled in mob)
    public boolean mobCollision(int x, int y, ArrayList<Mobile> a){
        for(Mobile i : a){
            if (((x >= i.getX() && x <= i.getX() + i.WIDTH)
                &&
                (y >= i.getY() && y <= i.getY() + i.HEIGHT))){
                    this.collisionEntity = i;
                    System.out.println("Here");
                    return true;
                }
        
        }
        return false;
    
    }

}