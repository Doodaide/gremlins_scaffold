package gremlins;

import processing.core.PImage;

/**
 * Indestructable
 */
public class Indestructable extends Immobile{
    
    public Indestructable(int x, int y) {
        super(x, y, loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " ")););
        
    }

    

    
}