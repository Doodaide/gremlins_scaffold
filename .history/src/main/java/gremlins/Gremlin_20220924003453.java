package gremlins;

import processing.core.PImage;

/**
 * Gremlin
 */
public class Gremlin extends Mobile{

    private PImage sprite; 
    public Gremlin(int x, int y, PImage img) {
        super(x, y, 1, 0, 0);
        this.sprite = 
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
    public void draw(App app){
        app.image(this.imgCurrent, this.x, this.y);;
        
        if (this.getX() + xVel < app.leftBound || this.getX() + xVel + WIDTH > app.rightBound){
            this.xStop();
        }

        if (this.getY() + yVel < app.upperBound || this.getY() + yVel + HEIGHT > app.lowerBound){
            this.yStop();
        }
        this.x += xVel;
        this.y += yVel;
    }    



    
}