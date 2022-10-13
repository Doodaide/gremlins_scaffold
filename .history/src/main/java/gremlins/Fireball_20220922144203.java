package gremlins;

import processing.core.PImage;

/**
 * Fireball
 */
public class Fireball extends Projectile{

    public Fireball(int x, int y, PImage sprite) {
        super(x, y, sprite);
        
    }

    @Override
    public void up() {
        this.yVel = -speed;
        this.direction = 0;
    }

    @Override
    public void right() {
       this.xVel = speed;
       this.direction = 1;
    }

    @Override
    public void down() {
        this.yVel = speed;
        this.direction = 2;
    }

    @Override
    public void left() {
        this.xVel = -speed;
        this.direction = 03;
    }

    public void hit(){
        this.alive = false;
    }

    public int draw(App app){
        if(this.alive){
            app.image(this.sprite, this.x, this.y);
            this.x += this.xVel; 
            this.y += this.yVel;
            //System.out.println("up " + this.upAV + " down " + this.downAV + " right " + this.rightAV + " left " + this.leftAV);
            if(!this.upAV && this.direction == 2){
                System.out.println("top of block hit");
            }
            if(!this.downAV && this.direction == 0){
                System.out.println("bottom of block hit");
            }
            if(!this.rightAV && this.direction == 3){
                System.out.println("Right of block hit");
            }
            if(!this.leftAV this.direction == ){
                System.out.println("left of block hit");
            }

            if(this.collision(app) == 1){
                return 1; // something hit
            }
        }
        return 0;
    }
}