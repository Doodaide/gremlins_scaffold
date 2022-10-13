package gremlins;

/*
 * 
 */

public abstract class Entity {
    
    protected int x, y; 
    protected final int HEIGHT, WIDTH; // sprite dimensions 
    //protected PImage currentState; 
    
    public Entity(int x, int y, int HEIGHT, int WIDTH){
        this.x = x;
        this.y = y; 
        this.HEIGHT = HEIGHT; 
        this.WIDTH = WIDTH; 
        //this.currentState = img; 
    }

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