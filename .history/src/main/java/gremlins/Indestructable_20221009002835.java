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
        app.image(this.texture, this.x, this.y);
        return 0; //for collisions later
    }

    @Override
    public void destroy() {
        return;
    }

    @Override
    public int update() {
        return 0;
        
    }

    @Override
    public boolean getViable() {
        return true;
    }

    @Override
    public void setViable(boolean b) {
        ;
        
    }
}