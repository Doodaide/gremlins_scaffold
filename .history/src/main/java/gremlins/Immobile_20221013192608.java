package gremlins;

import processing.core.PImage;

/**
 * Abstract Immobile class of objects. 
 * All imovable objects on screen that do not actively move or change locations 
 * are extended from this class. 
 */
public abstract class Immobile extends Entity{

    /**
     * the Imobile entity's sprite 
     */
    protected PImage texture;

    /**
     * whether the imobile entity can engage in collisions
     */
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

    /**
     * Abstract Draw method updates what the immobile object should look like on the screen 
     * @param app the PApplet, which it inherits methods from 
     */
    public abstract void draw(App app);

    /**
     * Abstract Update method, performs various update tasks on blocks depending on their type and interactions with 
     * Mobile entities. 
     * @return integer 1 or 0. Only destructable blocks (bricks) return 1 for fireball interactions 
     */
    public abstract int update();
    
    
    /**
     * Abstract destroy method, different uses for differeing block types. 
     */
    public abstract void destroy();

    /**
     * Abstract getViable method returns the interactivity of the immobile object
     * @return true or false, depending on whether the wizard, gremlins, projectil
     */
    public abstract boolean getViable();

    /**
     * 
     * @param b
     */
    public abstract void setViable(boolean b);
}