package gremlins;

/**
 * Fireball
 */
public class Fireball extends Projectile{

    public Fireball(int x, int y, ) {
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

    public int draw(App app){
        app.image(this.imgCurrent, this.x, this.y);

    }
}