package gremlins;

import processing.core.PImage;

/**
 * Wizard
 */
public class Wizard extends Mobile{

    /*
     * x - x coord 
     * y - y coord
     * 
     */
    private PImage imgCurrent, imgProjectile;
    private PImage[] imgArray = new PImage[4];
    public Fireball f;
    public Wizard(int x, int y, int speed, PImage imgUp, 
            PImage imgRight, PImage imgDown, 
            PImage imgLeft, PImage projectile) {

        super(x, y, 2, 0, 0);
        this.imgCurrent = imgLeft;
        this.imgArray[0] = imgUp;
        this.imgArray[1] = imgRight; 
        this.imgArray[2] = imgDown;
        this.imgArray[3] = imgLeft;
        this.imgProjectile = projectile;
        this.xDir = 3;
        this.yDir = 0;
        
        
    }

    @Override
    public void up() {
        this.imgCurrent = this.imgArray[0];
        this.yVel = -speed;
        this.yDir = 0; 
        this.direction = 0;
    }

    @Override
    public void right() {
        this.imgCurrent = this.imgArray[1];
        this.xVel = speed;
        this.xDir = 1; 
        this.direction = 1;
    }

    @Override
    public void down() {
        this.imgCurrent = this.imgArray[2];
        this.yVel = speed; 
        this.yDir = 2; 
        this.direction = 2;
    }

    @Override
    public void left() {
        this.imgCurrent = this.imgArray[3]; 
        this.xVel = -speed;
        this.xDir = 3; 
        this.direction = 3;
    }

    public Fireball shoot(){
        if(this.getX() % 20 == 0 || this.getY() % 20 == 0){
            return new Fireball(this.getX(), this.getY(), this.imgProjectile);
        }
        return null;
    }


    // Wizard custom draw method, and how to update the sprite on the map. 
    public int draw(App app){
        if(this.alive){
            app.image(this.imgCurrent, this.x, this.y);
            this.collision(app);
            // Border behaviour 
    
            if(((this.direction == 0 || this.direction == 2) && this.xVel != 0 ) ||
                ((this.direction == 1 || this.direction == 3) && this.yVel != 0)
            ){
                this.stop();
            }
    
            else{
                if( (!this.sideAV.get(3) && (this.xVel < 0) && this.direction == 3) ||
                    (!this.sideAV.get(1) && (this.xVel > 0) && this.direction == 1)){
                    this.xStop();
                    this.xVel = 0;
                }
                
        
                if( (!this.sideAV.get(0) &&(this.yVel < 0) && this.direction == 0) ||
                    (!this.sideAV.get(2) && (this.yVel > 0) && this.direction == 2)){
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
                    if(this.xDir == 3 && this.sideAV.get(3)){
                        this.setX(this.getX() - 1);
                    }
                    else if (this.xDir == 1 && this.sideAV.get(1)){
                        this.setX(this.getX() + 1);
                    }
                   
                }
                if (this.getY() % 20 != 0 && this.getYVel() == 0){
                    if(this.yDir == 0 && this.sideAV.get(0)){
                        this.setY(this.getY() - 1);
                    }
                    else if(this.yDir == 2 && this.sideAV.get(2)){
                        this.setY(this.getY() + 1);
                    }
                    
                }
            }
    
            
            
            //System.out.println(this.getX() + " "+ this.getY());
            
    
            // return 0 or 1
        }
        return 0;
        }
        
        
}