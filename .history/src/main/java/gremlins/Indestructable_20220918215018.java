package gremlins;

import processing.core.PImage;


/**
 * Indestructable
 */
public class Indestructable extends Immobile{
    public Indestructable(int x, int y, PImage texture) {
        super(x, y);   
        this.texture = texture;
    }
    
    public int draw(App app){
        app.image(this.texture, 0, 0);
        return 0; //for collisions later
    }
}