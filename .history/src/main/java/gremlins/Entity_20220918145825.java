package gremlins;

import processing.core.*;

/*
 * Entity methods
 * getX coordinate
 * getY coordinate 
 * 
 * 
 * Entity attributes 
 * 
 * x-coordinate
 * y-coordinate
 * current displayed state (currentState)
 * width 
 * height 
 * 
 */


public abstract class Entity {
    
    protected int x, y;
    protected final int HEIGHT, WIDTH;
    protected PImage currentState; 
    
    public Entity(int x, int y, int HEIGHT, int WIDTH, PImage img){
        this.x = x;
        this.y = y; 
        this.HEIGHT = HEIGHT; 
        this.WIDTH = WIDTH; 
        this.currentState = img; 
    }

    

}