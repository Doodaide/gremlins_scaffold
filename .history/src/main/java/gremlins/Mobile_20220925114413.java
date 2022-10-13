package gremlins;

import java.util.HashMap;

public abstract class Mobile extends Entity{

    protected int speed; // travel speed for each entity
    protected int direction; // 0 up, 1 right, 2 down, 3 left 
    protected int xDir, yDir; 
    protected boolean alive; // whether the mobile should be displayed on the next frame. 
    protected int xVel, yVel; // essentially allows mob to move

    public Immobile[] arr = new Immobile[4];  // blocks collided
    public Immobile collisionBlock; // block the mobile has collided with. Mainly used with projectiles 

    protected boolean upAV = true, downAV = true, rightAV = true, leftAV = true;
    protected HashMap<Integer, Boolean> sideAV = new HashMap<Integer, Boolean>();

    public Mobile(int x, int y, int speed, int xVel, int yVel) {
        super(x, y, 20, 20);
        this.speed = speed; 
        this.alive = true;
        this.direction = 0; 
        this.xVel = xVel;
        this.yVel = yVel;
        this.collisionBlock = null;
        this.sideAV.put(0, true);
        this.sideAV.put(1, true);
        this.sideAV.put(2, true);
        this.sideAV.put(3, true);
    }

    public int getSpeed(){
        return this.speed;
    }

    public int getDirection(){
        return this.direction; 
    }

    public void setDirection(int dir){
        this.direction = dir;
    }

    public int getXVel(){
        return this.xVel;
    }

    public int getYVel(){
        return this.yVel;
    }

    // Custom movement instructions 

    public abstract void up();

    public abstract void right();

    public abstract void down(); 

    public abstract void left();

    public abstract int draw(App app);

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
                //this.upAV = false;
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
            this.upAV = true;
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
                this.rightAV = false;
                this.sideAV.replace(1, true, false);
                toReturn = 1;
                //System.out.println("right 1");
            }
            else{
                this.rightAV = true;
                this.sideAV.replace(1, false, true);
                toReturn = 0;
            }
            
        }
 
        
        else if(!collides(this.getX() +this.WIDTH, this.getY() + 1, app) && // Top right corner, hit right 
            !collides(this.getX() + this.WIDTH, this.getY() + this.HEIGHT - 1, app)){
            rightAV = true;
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
                this.leftAV = false;
                this.sideAV.replace(3, true, false);
                toReturn = 1;
                //System.out.println("left 3");
            }
            else{
                this.leftAV = true;
                this.sideAV.replace(3, false, true);
                toReturn = 0;
            }
            
        }
            
        else if(!collides(this.getX(), this.getY() + 1, app) &&
            !collides(this.getX(), this.getY() + this.HEIGHT - 1, app)){ // top left corner, hit left 
            this.leftAV = true;
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
                this.downAV = false;
                this.sideAV.replace(2, true, false);
                toReturn = 1;
                //System.out.println("bottom 2");
            }
            else{
                this.downAV = true;
                this.sideAV.replace(2, false, true);
                toReturn = 0;
            }        
        } 
        

        else if(!collides(this.getX() + 1, this.getY() + this.HEIGHT, app) && 
            !collides(this.getX() + this.WIDTH - 1, this.getY() + this.HEIGHT, app)){// Bottom left corner, hit down
            this.downAV = true;
            this.sideAV.replace(2, false, true);
            toReturn = 0;
            //System.out.println("Bottom side free");
        }
        return toReturn;
    }

    public int collision(App app) { // mob to block collision 
        // Wizard collisions 
        // Fireball collisions
        upCollision(app);
        rightCollision(app);
        downCollision(app);
        leftCollision(app);
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
        return 1;

       
    }

    public boolean collides(int xCoord, int yCoord, App app) {
        for (Immobile i : app.getMap()) { // i = block
            // ! THINK HERE FOR COLLISION RULES
            if(i != null){
                if (((xCoord >= i.getX() && xCoord <= i.getX() + i.WIDTH)
                &&
                (yCoord >= i.getY() && yCoord <= i.getY() + i.HEIGHT))
                ){
                    //System.out.println(upAV + " " + rightAV + " " + downAV + " " + leftAV);
                this.collisionBlock = i; // finds the object the mobile collided with. 
                //System.out.println("bruh" + this.collisionBlock);
                //System.out.println("bruh " + i.getX() + " "+ i.getY());
                return true;
                }
            }
            this.collisionBlock = null;
        }
            
        return false;
    }

    public boolean mobCollision(int x, int y, App app){
        return false;
    
    }


}