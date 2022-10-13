package gremlins;

import java.util.HashMap;

/**
 * Abstract Mobile class of objects  
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
     * Sides of the mobile which are available to move
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
     * setter method for whether the mobile should be displayed on screen or 
     * @param b
     */
    public void setAlive(boolean b){
        this.alive = b;
    }

    /**
     * 
     * @return
     */
    public int getSpeed(){
        return this.speed;
    }

    /**
     * 
     * @return
     */
    public int getDirection(){
        return this.direction; 
    }

    /**
     * 
     * @param dir
     */
    public void setDirection(int dir){
        this.direction = dir;
    }

    /**
     * 
     * @return
     */
    public int getXVel(){
        return this.xVel;
    }

    /**
     * 
     * @return
     */
    public int getYVel(){
        return this.yVel;
    }

    /**
     * 
     * @return
     */
    public boolean[] getSideAV(){
        boolean [] temp = new boolean[4];
        for(int i = 0; i < temp.length; i++){
            temp[i] = this.sideAV.get(i);
        }
        return temp;
    }

    // Custom movement instructions 

    public abstract void up();

    public abstract void right();

    public abstract void down(); 

    public abstract void left();

    public abstract int draw(App app);
    
    public abstract int tick(App app);

    public int getFrameCounter(){
        return this.frameCounter;
    }
    
    public void incFrameCounter(){
        this.frameCounter += 1;
    }

    public void setFrameCounter(int x){
        this.frameCounter = x;
    }

    /* Consider how to modify this, so that if the mobile is not on a multiple of 20
     * it will continue moving until it stops on a multiple of 20. 
     */ 

    // ! FIX STOP MECHANICS
     
    public void xStop(){
        this.xVel = 0;
    }
    public void yStop(){
        this.yVel = 0;
    }

    // ! Tester function
    public void stop(){
            //System.out.println("Stop loop entered");
            //System.out.println("x: " + this.x);
            //System.out.println("y: "+ this.y);
            //System.out.println("direction: " + this.direction);
            //System.out.println("xdir: " + this.xDir);
            //ystem.out.println("ydir: " + this.yDir);
            this.xVel = 0;
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
                //System.out.println("up 0");
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
            //System.out.println("Up side free");
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
                //System.out.println("right 1");
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
            //System.out.println("Right side free");
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
                //System.out.println("left 3");
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
            //System.out.println("Left side free");
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
                //System.out.println("bottom 2");
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
            //System.out.println("Bottom side free");
        }
        return toReturn;
    }

    public int collision(App app) { // mob to block collision 
        // Wizard collisions 
        // Fireball collisions
        //upCollision(app);
        //rightCollision(app);
        //downCollision(app);
        //leftCollision(app);
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
        
        //for (int i = 0; i < arr.length; i++) {
        //    System.out.println(arr[i]);
        //}
        //System.out.println();
        return 0;

       
    }

    public boolean collides(int xCoord, int yCoord, App app) {
        for (Immobile i : app.getMap()) { // i = block
            // ! Implement 
            if(i != null && i.getViable()){
                if(((xCoord >= i.getX() && xCoord <= i.getX() + i.WIDTH)
                &&
                (yCoord >= i.getY() && yCoord <= i.getY() + i.HEIGHT))){
                    //System.out.println(upAV + " " + rightAV + " " + downAV + " " + leftAV);
                this.collisionBlock = i; // finds the object the mobile collided with. 
                //System.out.println(this.collisionBlock);
                    // !!PUT POWERUP DETECTION HERE
                return true;
                }
            }
            this.collisionBlock = null;
        }
        // ! POWERUP ERRORING
            if(app.getPowerUpAvailable() ){
                //System.out.println(app.getPowerUp());
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
            //System.out.println("Current entity: " + this);
            //System.out.println("Entity examined: " + i);
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
                //System.out.println(i.getClass().getName());
                //System.out.println("mobile entity: " + this);
                //System.out.println("current object: " + i);
                if (((xCoord >= i.getX() && xCoord <= i.getX() + i.WIDTH)
                &&
                (yCoord >= i.getY() && yCoord <= i.getY() + i.HEIGHT))){
                    
                    this.collisionEntity = i;
                    //System.out.println("Here");
                    //System.out.println("Collision entity: " + this.collisionEntity);
                    return true;
                }
            }
            
        
        }   
        
        return false;
    }

    public boolean mobCollision(int x, int y, App app){
        return false;
    }



}