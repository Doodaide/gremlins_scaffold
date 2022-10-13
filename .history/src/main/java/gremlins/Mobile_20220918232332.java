package gremlins;

import processing.core.*;

public abstract class Mobile extends Entity{

    protected int speed; // travel speed for each entity
    protected int direction; // 0 up, 1 right, 2 down, 3 left 
    protected int xDir, yDir; 
    protected boolean alive;
    protected int xVel, yVel; // essentially allows mob to move
    //private int velMult = 2;

    public Mobile(int x, int y, int speed, int xVel, int yVel) {
        super(x, y, 20, 20);
        this.speed = speed; 
        this.alive = true;
        this.direction = 0; 
        this.xVel = xVel;
        this.yVel = yVel;
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

    // Custom movement instructions 

    public abstract void up();

    public abstract void right();

    public abstract void down(); 

    public abstract void left();

    /* Consider how to modify this, so that if the mobile is not on a multiple of 20
     * it will continue moving until it stops on a multiple of 20. 
     */ 

    // ! FIX STOP MECHANICS
     
    public void stop(){
        System.out.println("Stop loop entered");
            System.out.println("x: " + this.x);
            System.out.println("y: "+ this.y);
            System.out.println("direction: " + this.direction);
            System.out.println("xdir: " + this.xDir);
            System.out.println("ydir: " + this.yDir);
            this.xVel = 0;
            this.yVel = 0;
            /*
             * while (this.x % 20 != 0 || this.y % 20 != 0){
       // facing up
            if(this.yDir == 0){
                this.setY(this.getY() - 2);
                System.out.println("1 up");
            }
            // facing right
            else if (this.xDir == 1){
                this.setX(this.getX() + 2);
                System.out.println("1 right");
            }
            // facing down
            else if (this.yDir == 2){
                this.setY(this.getY() + 2);
                System.out.println("1 down");
            }
            // facing left 
            else if (this.xDir == 3){
                this.setX(this.getX() - 2);
                System.out.println("1 left");
            }
        }
        System.out.println("Stop loop exited");
             */
        
        
    }

    
    public int collision(App app) {

        if (collides(this.x, this.y, app) ||
            collides(this.x, this.y+this.HEIGHT, app) ||
            collides(this.x+this.WIDTH, this.y, app) ||
            collides(this.x+this.WIDTH, this.y+this.HEIGHT, app)) {
               this.stop();
        }
        if (collides(this.x, this.y, app) ||
            collides(this.x+this.WIDTH, this.y, app) ||
            collides(this.x, this.y+this.WIDTH, app) ||
            collides(this.x+this.WIDTH, this.y+this.WIDTH-1, app)) {
                this.stop();
        }
        return 0;
    }

    public boolean collides(int xCoord, int yCoord, App app) {
        for (Immobile i : app.mapToDraw) {
            if (xCoord > i.getX() && xCoord < i.getX() + i.WIDTH && yCoord > i.getY() && yCoord < i.getY() + i.HEIGHT){
                System.out.println("bonk");
                return true;
            }
        }
        
        return false;
    }


}