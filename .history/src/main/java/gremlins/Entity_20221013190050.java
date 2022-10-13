package gremlins;

/*
 * Base abstract Entity class 
 */

public abstract class Entity {
    
    /*
     * x and y coordinates of the entity
     * Width and height of the sprite (all are 20) 
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

    /
    public int getX(){
        return this.x; 
    }

    public int getY(){
        return this.y;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

}