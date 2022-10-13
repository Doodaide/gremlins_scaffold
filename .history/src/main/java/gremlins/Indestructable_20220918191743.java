package gremlins;

import processing.core.PImage;

/**
 * Indestructable
 */
public class Indestructable extends Immobile{
    private PImage texture = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " ")));
    public Indestructable(int x, int y) {
        super(x, y, );
        
    }

    

    
}