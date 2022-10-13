package gremlins;

import processing.core.PImage;

/**
 * Immobile
 */
public abstract class Immobile extends Entity{
    protected PImage texture;


    public Immobile(int x, int y, ) {
        super(x, y, 20, 20);  
    }

    public void setTexture(PImage img){
        this.texture = img; 
    }

    
}