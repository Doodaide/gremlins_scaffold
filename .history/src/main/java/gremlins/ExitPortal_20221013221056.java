package gremlins;
import processing.core.PImage;

/**
 * ExitPortal class: The immobile entity that allows the player 
 * to progress to the next stage of the game. 
 */
public class ExitPortal extends Immobile{

    /**
     * Constructor for exitportal 
     * @param x
     * @param y
     * @param texture
     */
    public ExitPortal(int x, int y, PImage texture) {
        super(x, y);
        this.texture = texture;  
    }

    @Override
    public void draw(App app) {
        app.image(this.texture, this.x, this.y);
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