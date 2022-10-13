package gremlins;

import java.util.ArrayList;

import processing.core.PImage;

/**
 * Kamehameha
 */
public class Kamehameha extends Projectile{
    
    private boolean drawing; 
    private ArrayList<Immobile> toDelete; 
    private ArrayList<Mobile> toKill; 
    public Kamehameha(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.drawing = false; 
        toDelete = new ArrayList<Immobile>();
        toKill = new ArrayList<Mobile>();
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

    public ArrayList<Immobile> blocksToBreak(int wX, int wY){
        if(wX%20==0 && wY%20==0){
            for(Immobile I : app.getMap()){
                if(I != null){
                    if(I.getX() == wX && I.getY() == wY){
                        toDelete.add(I);
                        break; 
                    }
                }
            }
        }
    }

    @Override
    public int tick(App app) {
        int wX, wY, wDir; 
        wX = app.getWizard().getX(); 
        wY = app.getWizard().getY(); 
        wDir = app.getWizard().getDirection();

        if(wDir == 0 && wX%20==0 && wY%20==0){
            for (int i = wY; i < app.upperBound; i-=20){
                
            }
        }

        else if(wDir == 1){
            ;
        }

        else if(wDir == 2){
            ;
        }

        else if (wDir == 3){
            ;
        }

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