package gremlins;

import processing.core.PImage;

/**
 * Gremlin
 */
public class Gremlin extends Mobile{

    private PImage sprite; 
    public Gremlin(int x, int y, PImage img) {
        super(x, y, 1, 0, 0);
        this.sprite = img;
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
        app.image(this.sprite, this.x, this.y);;
        
        if (this.getX() + xVel < app.leftBound || this.getX() + xVel + WIDTH > app.rightBound ||
        this.getY() + yVel < app.upperBound || this.getY() + yVel + HEIGHT > app.lowerBound){
            this.xStop();
        }
        
        else{
            if( (!this.leftAV && (this.xVel < 0) && this.direction == 3) ||
                (!this.rightAV && (this.xVel > 0) && this.direction == 1)){
                this.xStop();
                this.xVel = 0;
            }
            
    
            if( (!this.upAV &&(this.yVel < 0) && this.direction == 0) ||
                (!this.downAV && (this.yVel > 0) && this.direction == 2)){
                this.yStop();
                this.yVel = 0;
            }
            
            // Prevents diagonal movement 
            if(this.getXVel() == 0 || this.getYVel() == 0){
                // Check direction corresponds to velocity. 
                // if direction != velocity direction 
                this.x += this.xVel;
                this.y += this.yVel;
            }
            
            // full block movement 
            if (this.getX() % 20 != 0 && this.getXVel() == 0){
                if(this.xDir == 3 && this.leftAV){
                    this.setX(this.getX() - 1);
                }
                else if (this.xDir == 1 && this.rightAV){
                    this.setX(this.getX() + 1);
                }
               
            }
            if (this.getY() % 20 != 0 && this.getYVel() == 0){
                if(this.yDir == 0 && this.upAV){
                    this.setY(this.getY() - 1);
                }
                else if(this.yDir == 2 && this.downAV){
                    this.setY(this.getY() + 1);
                }
                
            }
        }

        
        
        //System.out.println(this.getX() + " "+ this.getY());
        
    }    



    
}