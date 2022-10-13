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
        this.direction = 2;
        
    }

    @Override
    public void left() {
        this.direction = 3;
        
    }

    @Override
    public int draw(App app) {
        
        return 0;
    }

    @Override
    public int tick(App app) {
        int 
        if(this.alive){
            for(Immobile i : app.getMap()){
                if(i == this.collisionBlock && i.getClass().getName().equals("gremlins.Destructable")){
                    i.destroy(); 
                }
            }
        }
        return 0;
    }

    
}