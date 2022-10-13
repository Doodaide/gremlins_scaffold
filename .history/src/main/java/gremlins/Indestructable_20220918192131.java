package gremlins;

import processing.core.PImage;


/**
 * Indestructable
 */
public class Indestructable extends Immobile{
    private PImage texture;
    public Indestructable(int x, int y, PImage texture) {
        super(x, y);   
        this.texture = texture;
    }



    

    
}