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

    /**
     * Overriden down method that changes the yVelocity, xVelocity, and direction 
     */
    @Override
    public void down() {
        this.yVel = speed;
        this.xVel = 0;
        this.direction = 2;
        
    }

    /**
     * Overriden method that changes the yVelocity, xVelocity, and direction 
     */
    @Override
    public void left() {
        this.xVel = -speed;
        this.yVel = 0;
        this.direction = 3;
        
    }

    /**
     * Draw method draws the slimeball at each tick of the game. 
     * If the powerup is active, the frozen skin is used. 
     * @param app the App class which extends PApplet, thus methods such as app.image() can be used 
     * @return integer 0, that indicates no expected behaviour from the draw method. 
     */
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

    /**
     * Overriden Tick method that detects if the slimeball is both not frozen (powerup is not active)
     * @param 
     * @return 
     */
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