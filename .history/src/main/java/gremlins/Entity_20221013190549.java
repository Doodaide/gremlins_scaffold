package gremlins;

/**
 * Base abstract Entity class. 
 * Everything on screen is extended from the entity class
 */

public abstract class Entity {
    
    /**
     * x and y coordinates of the entity
     */

    protected int x, y; 


    protected final int HEIGHT, WIDTH; 
    
    /**
     * Constructor for entity 
     * @param x, x coordinate 
     * @param y, y coordinate
     * @param HEIGHT, height of the sprite 
     * @param WIDTH, width of the sprite
     */
    public Entity(int x, int y, int HEIGHT, int WIDTH){
        this.x = x;
        this.y = y; 
        this.HEIGHT = HEIGHT; 
        this.WIDTH = WIDTH; 
        //this.currentState = img; 
    }

    /**
     * Getter method for x coordinate 
     * @return x coordinate of entity
     */
    public int getX(){
        return this.x; 
    }

    /**
     * Getter method for y coordinate
     * @return y coordinate of entity 
     */
    public int getY(){
        return this.y;
    }

    /**
     * Setter method for x coordinate 
     * @param x new x coordinate 
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * Setter method for y coordinate 
     * @param y new y coordinate 
     */
    public void setY(int y){
        this.y = y;
    }

}