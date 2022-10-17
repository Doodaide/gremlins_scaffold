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
     * @param texture 
     */
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