package gremlins; 

/**
 * Gremlin
 */
public class Gremlin extends Mobile{

    public Gremlin(int x, int y, int speed, int xVel, int yVel) {
        super(x, y, speed, xVel, yVel);
        
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