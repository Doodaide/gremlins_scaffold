package gremlins;

import processing.core.PImage;

/**
 * Fireball
 */
public class Fireball extends Projectile{
    private int frameCounter;
    private boolean decaying;
    public Fireball(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.frameCounter = 0;
        this.decaying = false;
    }

    public int getFrameCounter(){
        return this.frameCounter;
    }

    public void incFrameCounter(){
        this.frameCounter += 1;
    }

    @Override
    public void up() {
        this.yVel = -speed;
        this.xVel = 0;
        this.direction = 0;
    }

    @Override
    public void right() {
       this.xVel = speed;
       this.yVel = 0;
       this.direction = 1;
    }

    @Override
    public void down() {
        this.yVel = speed;
        this.xVel = 0;
        this.direction = 2;
    }

    @Override
    public void left() {
        this.xVel = -speed;
        this.yVel = 0;
        this.direction = 3;
    }

    public void hit(){
        this.alive = false;
    }

    public int draw(App app){
        if(this.alive){
            //System.out.println("Alive");
            app.image(this.sprite, this.x, this.y);
            //System.out.println(this.getX() + " " + this.getY());
            //if(this.mobCollision(x, y, app))
            if(this.collision(app) == 1){ // block collision 
                //System.out.println("Collision");
                if(!this.sideAV.get(0) && this.direction == 0){
                    //System.out.println("bottom of block hit");
                    return 1; // something hit
                }
                if(!this.sideAV.get(2) && this.direction == 2){
                    //System.out.println("top of block hit");
                    return 1; // something hit
                }
                if(!this.sideAV.get(1) && this.direction == 1){
                    //System.out.println("left of block hit");
                    return 1; // something hit
                }
                if(!this.sideAV.get(3) && this.direction == 3){
                    //System.out.println("Right of block hit");
                    return 1; // something hit
                }

            }
            this.x += this.xVel; 
            this.y += this.yVel;
        }
        return 0;
    }
}