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

    public void stop(){
        System.out.println("x: " + this.x);
        System.out.println("y: "+ this.y);
        while (this.x % 20 != 0 || this.y % 20 != 0){
            System.out.println("Stop loop entered");
            System.out.println("x: " + this.x);
            System.out.println("y: "+ this.y);
            System.out.println("direction: " + this.direction);
            // facing up
            if(this.direction == 0 || ){
                this.setY(this.getY() - 1);
                System.out.println("1 up");
            }
            // facing right
            if (this.direction == 1){
                this.setX(this.getX() + 1);
                System.out.println("1 right");
            }
            // facing down
            if (this.direction == 2){
                this.setY(this.getY() + 1);
                System.out.println("1 down");
            }
            // facing left 
            if (this.direction == 3){
                this.setX(this.getX() - 1);
                System.out.println("1 left");
            }
        }
        System.out.println("Stop loop exited");
        this.xVel = 0;
        this.yVel = 0;
    }

    



}