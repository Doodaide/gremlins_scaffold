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
    public Fireball f;
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
        this.xDir = 3;
        this.yDir = 0;
        
        
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
        this.direction = 1;
    }

    @Override
    public void down() {
        
        this.imgCurrent = imgDown;
        this.yVel = speed; 
        this.yDir = 2; 
        this.direction = 2;
    }

    @Override
    public void left() {
        
        this.imgCurrent = imgLeft; 
        this.xVel = -speed;
        this.xDir = 3; 
        this.direction = 3;
    }

    public Fireball shoot(){
        return new Fireball(this.x, this.y, this.imgProjectile);
    }


    // Wizard custom draw method, and how to update the sprite on the map. 
    public int draw(App app){
        app.image(this.imgCurrent, this.x, this.y);
        // Border behaviour 
        if (this.getX() + this.xVel < app.leftBound || this.getX() + this.xVel + WIDTH > app.rightBound){
            this.stop();
        }

        if (this.getY() + this.yVel < app.upperBound || this.getY() + this.yVel + HEIGHT > app.lowerBound - 20){
            this.stop();
        }

        if(!this.leftAV && (this.xVel < 0)){
            this.xStop();
            this.xVel = 0;
        }

        if(!this.rightAV && (this.xVel > 0)){
            this.xStop();
            this.xVel = 0;
        }

        if(!this.upAV &&(this.yVel < 0)){
            this.yStop();
            this.yVel = 0;
        }

        if(!this.downAV && (this.yVel > 0)){
            this.yStop();
            this.yVel = 0;
        }
        
        this.x += this.xVel;
        this.y += this.yVel;
        this.collision(app);
        if (this.getX() % 20 != 0 || this.getY() % 20 != 0){
            this.draw(this);
        }
        return 0;

        // return 0 or 1
    }
}