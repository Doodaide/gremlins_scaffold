package gremlins;
import processing.core.PImage;

/**
 * ExitPortal class: The immobile entity that allows the player 
 * to progress to the next stage of the game. 
 */
public class ExitPortal extends Immobile{

    /**
     * Constructor for exitportal 
     * @param x x coordinate of portal 
     * @param y y coordinate of portal 
     * @param texture portal sprite 
     */
    public ExitPortal(int x, int y, PImage texture) {
        super(x, y);
        this.texture = texture;  
    }

    /**
     * Draw method that overrides the immobile draw method. 
     * @param app the PApplet, which it inherits the image() method from
     */
    @Override
    public void draw(App app) {
        app.image(this.texture, this.x, this.y);
    }
    
    /**
     * Required update method, returns a 0 for each call as it is not a breakable block
     */
    @Override
    public int update() {
        return 0;
    }

    /**
     * getter method for whether the block can interact with mobile entities 
     * @return boolean. Always can collide with the wizard and projectiles 
     */
    @Override
    public boolean getViable() {
        return true;
    }

    @Override
    public void setViable(boolean b) {
        this.viable = true;
        
    }

    
}