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

    public boolean getDecaying(){
        return this.decaying;
    }

    public void setDecaying(Boolean d){
        this.decaying = d;
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
        }
        return 0;
    }

    @Override
    public void tick(App app) {
        // TODO Auto-generated method stub
        
    }
}