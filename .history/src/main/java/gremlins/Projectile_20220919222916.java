package gremlins;

/**
 * Projectile
 */

public abstract class Projectile extends Mobile{
    protected PImage 
    public Projectile(int x, int y) {
        super(x, y, 4, 0, 0);
       
    }

    // Block collision (handeled in Mob) 

    // Mob collision (not handeled in mob)

    public boolean mobCollision(int x, int y, App app){

        return false;
    }
    

    
}