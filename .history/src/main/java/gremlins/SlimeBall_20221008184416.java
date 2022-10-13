package gremlins;

import processing.core.PImage;

/**
 * SlimeBall
 */
public class SlimeBall extends Projectile{
    private PImage frozenS;
    public SlimeBall(int x, int y, PImage sprite, PImage frozenS){
        super(x, y, sprite);
        this.frozenS = frozenS;
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

    @Override
    public int draw(App app) {
        if(this.alive){
            if(!app.getFreezeActive()){
                app.image(this.sprite, this.x, this.y);
            }
            else if(app.getFreezeActive()){
                app.image(this.frozenS, this.x, this.y);
            }
        }
        return 0;
    }

    @Override
    public int tick(App app) {
        if(this.alive){
            if(app.getFreezeActive()){

            }
            
        }
        return 0;
    }
    
}