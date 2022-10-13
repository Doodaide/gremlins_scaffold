package gremlins; 
import processing.core.PImage;
import processing.core.*; 

public abstract class Destructable extends Immobile{
    private PImage[] decayTextures; 
    public Destructable(int x, int y, PImage defaultTexture) {
        super(x, y);
        this.texture = defaultTexture;
        this.decayTextures = 
    }

}