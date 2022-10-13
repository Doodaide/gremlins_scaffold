package gremlins;

import processing.core.*;

public abstract class Mobile extends Entity{

    protected int speed; // travel speed for each entity
    protected int direction; // 0 up, 1 right, 2 down, 3 left 
    protected int xDir, yDir; 
    protected boolean alive;
    protected int xVel, yVel; // essentially allows mob to move

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

    public void xStop(){
        while (this.x % 20 != 0){
            System.out.println("xStop");
            System.out.println("x " + this.x);
            System.out.println("y " + this.y);
            System.out.println("xDir " + this.xDir);
            System.out.println("yDir " + this.yDir);
            if (this.xDir == 5){
                this.xDir = direction;
            }
            //facing right
            if (this.xDir == 1){
                this.x += 1;
            }
            //facing left 
            if (this.xDir == 3){
                this.x -= 1;
            }
        }
        this.xVel = 0;
        this.yVel = 0;
    }

    public void yStop(){
        while (this.y % 20 != 0){
            System.out.println("yStop");
            System.out.println("x " + this.x);
            System.out.println("y " + this.y);
            System.out.println("xDir " + this.xDir);
            System.out.println("yDir " + this.yDir);
            // facing up
            if (this.yDir == 5){
                this.yDir = direction;
            }
            if(this.yDir == 0){
                this.y -= 1;
            }
            // facing down
            if (this.yDir == 2){
                this.y += 1;
            }
        }
        this.yVel = 0;
        this.xVel = 0;
    }



}