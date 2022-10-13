package gremlins;

import processing.core.PImage;

/**
 * Wizard
 */
public class Wizard extends Mobile{

    /*
     * x - x coord 
     * y - y coord
     * 
     */
    private PImage imgUp, imgRight, imgDown, imgLeft, imgCurrent, imgProjectile;
    public Wizard(int x, int y, int speed, PImage imgUp, 
            PImage imgRight, PImage imgDown, 
            PImage imgLeft, PImage projectile) {

        super(x, y, 2, 0, 0);
        this.imgLeft = imgLeft; 
        this.imgRight = imgRight; 
        this.imgDown = imgDown; 
        this.imgUp = imgUp; 
        this.imgCurrent = imgLeft;
        this.imgProjectile = projectile;
        
    }

    @Override
    public void up() {
        
        this.imgCurrent = this.imgUp;
        this.yVel = -speed;
        this.yDir = 0; 
        
        this.direction = 0;
    }

    @Override
    public void right() {
        
        this.imgCurrent = this.imgRight;
        this.xVel = speed;
        this.xDir = 1; 
        this.yDir = 5; // null value 
        this.direction = 1;
    }

    @Override
    public void down() {
        
        this.imgCurrent = imgDown;
        this.yVel = speed; 
        this.xDir = 5; // null value
        this.yDir = 2; 
        this.direction = 2;
    }

    @Override
    public void left() {
        
        this.imgCurrent = imgLeft; 
        this.xVel = -speed;
        this.xDir = 3; 
        this.yDir = 5; //null value
        this.direction = 3;
    }


    // Wizard custom draw method, and how to update the sprite on the map. 
    public void draw(App app){
        app.image(this.imgCurrent, this.x, this.y);;
        // Border behaviour 
        if (this.getX() + this.xVel < app.leftBound || this.getX() + this.xVel + WIDTH > app.rightBound){
            this.stop();
        }

        if (this.getY() + this.yVel < app.upperBound || this.getY() + this.yVel + HEIGHT > app.lowerBound){
            this.stop();
        }
        this.x += this.xVel;
        this.y += this.yVel;
    }
}