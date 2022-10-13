package gremlins;

import processing.core.PImage;

/**
 * Abstract Immobile class of objects. 
 * All imovable objects on screen that do not actively move or change locations 
 * are extended from this class. 
 */
public abstract class Immobile extends Entity{

    /**
     * @ the Imobile entity's sprite 
     */
    protected PImage texture;
    protected boolean viable;

    /**
     * Constructor for an immobile object 
     * @param x x coordinate for the immobile object 
     * @param y y coordinate for the immobile object 
     */
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