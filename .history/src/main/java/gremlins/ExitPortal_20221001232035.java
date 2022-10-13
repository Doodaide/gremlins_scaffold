package gremlins;
import processing.core.PImage;

/**
 * ExitPortal
 */
public class ExitPortal extends Immobile{

    public ExitPortal(int x, int y, PImage texture) {
        super(x, y);
        this.texture = texture;
    }

    @Override
    public int draw(App app) {
        app.image(this.texture, this.x, this.y);
        return 0;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public void destroy(int x) {
        ;
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