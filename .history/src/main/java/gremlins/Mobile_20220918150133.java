package gremlins;

import processing.core.*;

public abstract class Mobile extends Entity{

    protected int speed;

    public Mobile(int x, int y, PImage img, int speed) {
        super(x, y, 20, 20, img);
        this.speed = speed; 
    }


}