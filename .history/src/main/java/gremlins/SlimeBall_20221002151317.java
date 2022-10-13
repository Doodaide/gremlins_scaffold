package gremlins;

import processing.core.PImage;

/**
 * SlimeBall
 */
public class SlimeBall extends Projectile{
    public SlimeBall(int x, int y, PImage sprite){
        super(x, y, sprite);
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void left() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public int draw(App app) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int tick(App app) {
        // TODO Auto-generated method stub
        return 0;
    }
    
}