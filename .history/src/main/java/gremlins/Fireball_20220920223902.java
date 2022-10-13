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
        
    }

    @Override
    public void right() {
       this.xVel = speed;
        
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
        ;
    }

    public int draw(App app){
        
        if(this.collision(app) == 1){
            return 1; // something hit
        }
        else{
            app.image(this.sprite, this.x, this.y);
            this.x += this.xVel; 
            this.y += this.yVel;
        }
        return 0;
    }
}