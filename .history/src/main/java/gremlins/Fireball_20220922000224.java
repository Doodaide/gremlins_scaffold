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
       this.direction = 0;
    }

    @Override
    public void down() {
        this.yVel = speed;
        
    }

    @Override
    public void left() {
        this.xVel = -speed;
        
    }

    public void hit(){
        this.alive = false;
    }

    public int draw(App app){
        if(this.alive){
            app.image(this.sprite, this.x, this.y);
            this.x += this.xVel; 
            this.y += this.yVel;
            if(this.collision(app) == 1){
                return 1; // something hit
            }
        }
        return 0;
    }
}