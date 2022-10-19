package gremlins;
import processing.core.PImage;


/**
 * Indestructable class: blocks that extend from immobile entities that cannot be deleted or destroyed by any means
 */
public class Indestructable extends Immobile{

    /**
     * Constructor for indestructable 
     * @param x x coordinate 
     * @param y y coordinate 
     * @param texture Indestructable stone block sprite
     */
    public Indestructable(int x, int y, PImage texture) {
        super(x, y);   
        this.texture = texture;
    }
    
    /**
     * Draw method draws the Indestructable block onto the screen. 
     * @param app inherits from PApplet's image() method. 
     */
    public void draw(App app){
        if(this.texture != null){
            app.image(this.texture, this.x, this.y);
        }
    }

    /**
     * @return 0 as the indestructable will not interact with 
     */
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