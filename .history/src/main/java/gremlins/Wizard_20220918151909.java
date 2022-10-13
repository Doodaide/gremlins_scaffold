package gremlins;

import processing.core.PImage;

/**
 * Wizard
 */
public class Wizard extends Mobile{

    /*
     * x - x coord 
     * y - y coord
     * 
     */
    private PImage imgUp, imgRight, imgDown, imgLeft, imgProjectile;
    public Wizard(int x, int y, int speed, int xVel, int yVel, PImage imgUp, 
            PImage imgRight, PImage imgDown, 
            PImage imgLeft, PImage projectile) {
                
        super(x, y, 2, xVel, yVel);
        this.imgLeft = imgLeft; 
        this.imgRight = imgRight; 
        this.imgDown = imgDown; 
        this.imgUp = imgUp; 
        this.imgProjectile = projectile;
        
    }

    @Override
    public void up() {
        
        
    }

    @Override
    public void right() {
        
        
    }

    @Override
    public void down() {
        
        
    }

    @Override
    public void left() {
        
        
    }

    
}