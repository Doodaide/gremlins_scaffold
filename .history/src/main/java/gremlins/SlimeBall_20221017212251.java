package gremlins;
import processing.core.PImage;
/**
 * SlimeBall Class: The Slime's projectiles that extends a projectile (originates from mobile entity)
 */
public class SlimeBall extends Projectile{
    /**
     * Frozen projectile, a unique projectile skin to the slime's projectiles that have the ability to be frozen
     */
    private PImage frozenS;

    /**
     * Constructor for SlimeBall 
     * @param x x coordinate of slimeball 
     * @param y y coordinate of slimeball 
     * @param sprite PImage that is the slimeball's sprite
     * @param frozenS PImage that is the slimeball's sprite when the frozen powerup is active. 
     */
    public SlimeBall(int x, int y, PImage sprite, PImage frozenS){
        super(x, y, sprite);
        this.frozenS = frozenS;
    }

    /**
     * Overriden up method that changes the yVelocity, xVelocity, and direction 
     */
    @Override
    public void up() {
        this.yVel = -speed;
        this.xVel = 0;
        this.direction = 0;
        
    }

    /**
     * Overriden right method that changes the yVelocity, xVelocity, and direction 
     */
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
            if(!app.getFreezeActive()){
                if(this.collision(app) == 1){ // block collision 
                    if( (!this.sideAV.get(0) && this.direction == 0) 
                     || (!this.sideAV.get(2) && this.direction == 2) 
                     || (!this.sideAV.get(1) && this.direction == 1) 
                     || (!this.sideAV.get(3) && this.direction == 3) ){
                        return 1;
                     }
                }
                this.x += this.xVel; 
                this.y += this.yVel;
            }
            
        }
        return 0;
    }
    
}