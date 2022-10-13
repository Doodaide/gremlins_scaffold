package gremlins; 

/**
 * Gremlin
 */
public class Gremlin extends Mobile{

    public Gremlin(int x, int y, int speed, int xVel, int yVel) {
        super(x, y, speed, xVel, yVel);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void up() {
        this.xVel = -speed;
        
    }

    @Override
    public void right() {
        this.xVel = speed;
        
    }

    @Override
    public void down() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void left() {
        // TODO Auto-generated method stub
        
    }

    
}