package gremlins;

/**
 * Fireball
 */
public class Fireball extends Projectile{

    public Fireball(int x, int y) {
        super(x, y);
        
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
        this.xVel = 
        
    }

    
}