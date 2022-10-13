package gremlins;

import processing.core.PImage;

/**
 * Kamehameha
 */
public class Kamehameha extends Projectile{
    
    private boolean drawing; 
    public Kamehameha(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.drawing = false; 
    }

    public boolean getDrawing(){
        return this.drawing; 
    }

    public void setDrawing(boolean b){
        this.drawing = b;
    }

    @Override
    public void up() {
        this.direction = 0;
        
    }

    @Override
    public void right() {
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
        
        return 0;
    }

    @Override
    public int tick(App app) {
        if(this.alive){
            if(this.collision(app) == 1){ // block collision 
                //System.out.println("Collision");
                if(!this.sideAV.get(0) && this.direction == 0){
                    //System.out.println("bottom of block hit");
                    return 1; // something hit
                }
                if(!this.sideAV.get(2) && this.direction == 2){
                    //System.out.println("top of block hit");
                    return 1; // something hit
                }
                if(!this.sideAV.get(1) && this.direction == 1){
                    //System.out.println("left of block hit");
                    return 1; // something hit
                }
                if(!this.sideAV.get(3) && this.direction == 3){
                    //System.out.println("Right of block hit");
                    return 1; // something hit
                }

            }
            this.x += this.xVel; 
            this.y += this.yVel;
        }
        return 0;
    }

    
}