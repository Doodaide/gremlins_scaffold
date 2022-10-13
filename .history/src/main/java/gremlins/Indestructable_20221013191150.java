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
    
    public void draw(App app){
        if(this.texture != null){
            app.image(this.texture, this.x, this.y);
        }
        
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
        this.viable = true;
        
    }
}