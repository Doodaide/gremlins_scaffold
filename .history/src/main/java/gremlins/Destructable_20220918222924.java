package gremlins; 
import processing.core.PImage;
import processing.core.*; 

public abstract class Destructable extends Immobile{
    private PImage[] decayTextures; 
    public Destructable(int x, int y, ) {
        super(x, y);
        
    }

}