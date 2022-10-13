package gremlins;

import processing.core.*;

public abstract class Mobile extends Entity{

    protected int speed;
    protected int direction; // 0 up, 1 right, 2 down, 3 left 
    protected boolean alive;

    public Mobile(int x, int y, PImage img, int speed) {
        super(x, y, 20, 20, img);
        this.speed = speed; 
        this.alive = true;
        this.direction = 0; 
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

    public abstract void up();

    public abstract void right();

    public abstract void down(); 

    public abstract void left();

    /* Consider how to modify this, so that if the mobile is not on a multiple of 20
     * it will continue moving until it stops on a multiple of 20. 
     */ 

    public void xStop(){
        if 
    }

    public abstract void yStop();



}