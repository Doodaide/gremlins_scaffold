package gremlins;
import java.util.HashMap;

/**
 * TOFINISH
 */
public abstract class Mobile extends Entity{

    /**
     * travel speed for each entity
     */
    protected int speed;

    /**
     * Direction entity is facing: 0 up, 1 right, 2 down, 3 left 
     */
    protected int direction; 

    /**
     * specific directional variables 
     */
    protected int xDir, yDir; 

    /**
     * whether the mobile should be displayed on the next frame. 
     */
    protected boolean alive; 

    /**
     * Allows the mobile to change its coordinates at a certain rate. 
     */
    protected int xVel, yVel; 

    /**
     * block the mobile has collided with. Null if no directional collision
     */
    protected Immobile collisionBlock; 

    /**
     * A hashmap that denotes which sides of the mobile are available to move
     */
    protected HashMap<Integer, Boolean> sideAV = new HashMap<Integer, Boolean>();
    
    /**
     * mobile entity the mobile has collided with 
     */
    protected Mobile collisionEntity; 

    /**
     * Attribute used for specific mobile entities that have a certain timeframe they must act upon
     */
    protected int frameCounter;

    /**
     * Constructor for mobile 
     * @param x x coordinate 
     * @param y y coordinate 
     * @param speed size of the steps the mobile entities make per tick 
     * @param xVel rate of change of x position 
     * @param yVel rate of change of y posiiton 
     */
    public Mobile(int x, int y, int speed, int xVel, int yVel) {
        super(x, y, 20, 20);
        this.speed = speed; 
        this.alive = true;
        this.direction = 3; 
        this.xVel = xVel;
        this.yVel = yVel;

        this.collisionBlock = null;
        this.collisionEntity = null;

        this.sideAV.put(0, true);
        this.sideAV.put(1, true);
        this.sideAV.put(2, true);
        this.sideAV.put(3, true);
        this.frameCounter = 0;
    }

    /**
     * getter method for whether the mobile should be displayed on screen or considered for collisions 
     * @return boolean whether the mobile is alive or not
     */
    public boolean getAlive(){
        return this.alive;
    }

    /**
     * setter method for whether the mobile should be displayed on screen or considered for collisions
     * @param b boolean whether the mobile is alive or not 
     */
    public void setAlive(boolean b){
        this.alive = b;
    }

    /**
     * getter method for getting each mobile entity's base speed 
     * @return speed integer that corresponds to the number of pixels each entity moves per tick. 
     */
    public int getSpeed(){
        return this.speed;
    }

    /**
     * getter method for the direction the mobile is facing. 
     * @return integer that corresponds with the direction the mobile is facing (0, up; 1, right; 2 down; 3, left)
     */
    public int getDirection(){
        return this.direction; 
    }

    /**
     * setter method for the direction the mobile is facing 
     * @param dir integer that corresponds with the direction the mobile is facing (0, up; 1, right; 2 down; 3, left)
     */
    public void setDirection(int dir){
        this.direction = dir;
    }

    /**
     * getter method for the mobile's x velocity 
     * @return integer that corresponds to +/- mobile speed 
     */
    public int getXVel(){
        return this.xVel;
    }

    /**
     * getter method for the mobile's y velocity 
     * @return integer that correspods to +/- mobile speed 
     */
    public int getYVel(){
        return this.yVel;
    }

    /**
     * getter method for the side availability. 
     * @return an array of booleans that corresponds with which sides are available [up, right, down, left]. 
     */
    public boolean[] getSideAV(){
        boolean [] temp = new boolean[4];
        for(int i = 0; i < temp.length; i++){
            temp[i] = this.sideAV.get(i);
        }
        return temp;
    }

    /**
     * Abstract up method 
     */
    public abstract void up();

    /**
     * Abstract right method 
     */
    public abstract void right();

    /**
     * Abstract down method 
     */
    public abstract void down(); 

    /**
     * Abstract left method 
     */
    public abstract void left();

    /**
     * Abstract draw method 
     * @param app takes in the App class object to use the methods it has extended from PApplet such as app.image() 
     * @return an integer that determines how that object may interact with other objects. A 0 is typically returned, but in the case of
     * a mobile like the wizard, the draw method returns the direction it was last drawn in. 
     */
    public abstract int draw(App app);
    
    /**
     * Abastract tick method 
     * @param app takes in the App class object to use the methods such as the getters for the map, mobile, and immobile objects. 
     * This allows interactions to take place 
     * @return an integer is returned depending on how/what kinds of interactions are expected after a certain set of logic is ececuted. 
     */
    public abstract int tick(App app);

    /**
     * Getter method for the number of frames elapsed 
     * @return an integer that corresponds to the number of frames elapsed 
     */
    public int getFrameCounter(){
        return this.frameCounter;
    }

    /**
     * Setter method for the framecounter (number of frames elapsed)
     * @param x an integer that corresponds to a frame number 
     */
    public void setFrameCounter(int x){
        this.frameCounter = x;
    }
     
    /**
     * 
     */
    public void xStop(){
        this.xVel = 0;
    }
    public void yStop(){
        this.yVel = 0;
    }

    
    
    public int upCollision(App app){
        int toReturn = 0; // 0 = no collision. 1 = collision
        // ! Upper side collision
        if(collides(this.getX() + 1, this.getY(), app) || // top left corner, hit up
            collides(this.getX()+this.WIDTH - 1, this.getY(), app)){ // top right cornder hit up 
            if(this.direction == 0){
                toReturn = 1;
                this.sideAV.replace(0, true, false);
            }
            else{
                //this.upAV = true;
                this.sideAV.replace(0, false, true);
                toReturn = 0;
            }
            
        }
         
        else if(!collides(this.getX() + 1, this.getY(), app) && 
            !collides(this.getX()+this.WIDTH - 1, this.getY(), app)){ // top left corner, hit up
            this.sideAV.replace(0, false, true);
            toReturn = 0;
        }
        
        return toReturn;
    }

    public int rightCollision(App app){
        int toReturn = 0;
         // ! Right side collision 
        if(collides(this.getX()+this.WIDTH, this.getY() + 1, app) || // Top right corner, hit right 
            collides(this.getX() + this.WIDTH, this.getY() + this.HEIGHT - 1, app)){
            if(this.direction == 1){
                this.sideAV.replace(1, true, false);
                toReturn = 1;
            }
            else{
                this.sideAV.replace(1, false, true);
                toReturn = 0;
            }
            
        }
 
        
        else if(!collides(this.getX() +this.WIDTH, this.getY() + 1, app) && // Top right corner, hit right 
            !collides(this.getX() + this.WIDTH, this.getY() + this.HEIGHT - 1, app)){
            this.sideAV.replace(1, false, true);
            toReturn = 0;
        }
        
        return toReturn;
    }

    public int leftCollision(App app){
        int toReturn = 0; // 0 = no collision. 1 = collision
        // ! Left side collision    
        if(collides(this.getX(), this.getY() + 1, app) || // top left corner
            collides(this.getX(), this.getY() + this.HEIGHT - 1, app) ){ // bottom left corner, hit left 
            if (this.direction == 3) {
                this.sideAV.replace(3, true, false);
                toReturn = 1;
            }
            else{
                this.sideAV.replace(3, false, true);
                toReturn = 0;
            }
            
        }
            
        else if(!collides(this.getX(), this.getY() + 1, app) &&
            !collides(this.getX(), this.getY() + this.HEIGHT - 1, app)){ // top left corner, hit left 
            this.sideAV.replace(3, false, true);
            toReturn = 0;
        }
        return toReturn;
    }

    public int downCollision(App app){
        int toReturn = 0; // 0 = no collision. 1 = collision
        // ! Lower side collision 
        if(collides(this.getX() + 1, this.getY() + this.HEIGHT, app) || // Bottom left corner, hit down
            collides(this.getX() + this.WIDTH - 1, this.getY() + this.HEIGHT, app)){ // Bottom right corner, hit down
            if(this.direction == 2){
                this.sideAV.replace(2, true, false);
                toReturn = 1;
            }
            else{
                this.sideAV.replace(2, false, true);
                toReturn = 0;
            }        
        } 
        

        else if(!collides(this.getX() + 1, this.getY() + this.HEIGHT, app) && 
            !collides(this.getX() + this.WIDTH - 1, this.getY() + this.HEIGHT, app)){// Bottom left corner, hit down
            this.sideAV.replace(2, false, true);
            toReturn = 0;
        }
        return toReturn;
    }

    public int collision(App app) { // mob to block collision 
        if(this.direction == 0){
            return upCollision(app);
        }
        if(this.direction == 1){
            return rightCollision(app);
        }
        if(this.direction == 2){
            return downCollision(app);
        }
        if(this.direction == 3){
            
            return leftCollision(app);
        }
        return 0;

       
    }

    public boolean collides(int xCoord, int yCoord, App app) {
        for (Immobile i : app.getMap()) { // i = block
            // ! Implement 
            if(i != null && i.getViable()){
                if(((xCoord >= i.getX() && xCoord <= i.getX() + i.WIDTH)
                &&
                (yCoord >= i.getY() && yCoord <= i.getY() + i.HEIGHT))){
                this.collisionBlock = i; // finds the object the mobile collided with. 
                    // !!PUT POWERUP DETECTION HERE
                return true;
                }
            }
            this.collisionBlock = null;
        }
        // ! POWERUP ERRORING
            if(app.getPowerUpAvailable() ){
                if(this.getClass().getName().equals("gremlins.Wizard")
                &&
                ((xCoord >= app.getPowerUp().getX() && xCoord <= app.getPowerUp().getX() + app.getPowerUp().WIDTH) // ! error here 
                    &&
                    (yCoord >= app.getPowerUp().getY() && yCoord <= app.getPowerUp().getY() + app.getPowerUp().HEIGHT))){
                        this.collisionBlock = app.getPowerUp();
                        app.setFreezeActive(true);
                        return true;
                }   
                this.collisionBlock = null;
            }
        

        for(Mobile i : app.getMobiles()){
            if(this != i && i != null
            && !(this.getClass().getName().equals("gremlins.Wizard") && i.getClass().getName().equals("gremlins.Fireball")) 
            && !(i.getClass().getName().equals("gremlins.Wizard") && this.getClass().getName().equals("gremlins.Fireball")) 
            && !(this.getClass().getName().equals("gremlins.Wizard") && i.getClass().getName().equals("gremlins.Fireball")) 
            && !(i.getClass().getName().equals("gremlins.Gremlin") && this.getClass().getName().equals("gremlins.SlimeBall"))
            && !(i.getClass().getName().equals("gremlins.SlimeBall") && this.getClass().getName().equals("gremlins.Gremlin"))
            && !(i.getClass().getName().equals("gremlins.Fireball") && this.getClass().getName().equals("gremlins.Fireball"))
            && !(i.getClass().getName().equals("gremlins.SlimeBall") && this.getClass().getName().equals("gremlins.SlimeBall"))
            && !(i.getClass().getName().equals("gremlins.Gremlin") && this.getClass().getName().equals("gremlins.Gremlin"))
            ){

                if (((xCoord >= i.getX() && xCoord <= i.getX() + i.WIDTH)
                &&
                (yCoord >= i.getY() && yCoord <= i.getY() + i.HEIGHT))){
                    
                    this.collisionEntity = i;

                    return true;
                }
            }
            
        
        }   
        
        return false;
    }
}