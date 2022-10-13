package gremlins;

import processing.core.PImage;


/**
 * Indestructable
 */
public abstract class Indestructable extends Immobile{
    public Indestructable(int x, int y, PImage texture) {
        super(x, y);   
        this.texture = texture;
    }
    
    public int draw(App app){
        app.image(this.texture, this.x, this.y);
        return 0; //for collisions later
    }
}