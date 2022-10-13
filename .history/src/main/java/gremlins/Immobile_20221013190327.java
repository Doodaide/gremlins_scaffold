package gremlins;

import processing.core.PImage;

/**
 * Abstract Immobile class of objects. 
 * All imovable objects on screen (that do not have)
 */
public abstract class Immobile extends Entity{
    protected PImage texture;
    protected boolean viable;
    public Immobile(int x, int y) {
        super(x, y, 20, 20);  
        this.viable = true;
    }

    public abstract int draw(App app);
    public abstract int update();
    public abstract void destroy();
    public abstract boolean getViable();
    public abstract void setViable(boolean b);
}