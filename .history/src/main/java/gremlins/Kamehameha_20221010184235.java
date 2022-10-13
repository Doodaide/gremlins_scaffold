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
    private int lifeSpan;
    private int beamLength; 
    public Kamehameha(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.drawing = false; 
        this.toDelete = new ArrayList<Immobile>();
        this.toKill = new ArrayList<Mobile>();
        this.lifeSpan = 0;
        this.beamLength = 0;
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

    public void setLifespan(int x){
        this.lifeSpan = x; 
    }

    public int getLifeSpan(){
        return this.lifeSpan;
    }

    @Override
    public int draw(App app) {
        
        return 0;
    }

    public void blocksToBreak(int wX, int wY, App app){
            for(Immobile I : app.getMap()){
                if(I != null){
                    if(I.getX() == wX || I.getY() == wY){
                        System.out.println("Found I " + I);
                        toDelete.add(I);
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

        /*
         * if(wDir == 0 && wX%20==0 && wY%20==0){
            for (int i = wY; i > app.upperBound; i -= 20){
                this.blocksToBreak(wX, wY, app);
                System.out.println("Broken up");
            }
        }
         */
        

        if(wDir == 1 && wX%20==0 && wY%20==0){
            System.out.println("Passed coord assessment");
            for (int i = wX; i < app.rightBound; i += 20){
                for(Immobile I : app.getMap()){
                    if(I != null){
                        if(I.getY() == wY && I.getY() > wY){
                            System.out.println("Found I " + I);
                            toDelete.add(I);
                        }
                    }
                }
                //this.blocksToBreak(wX, wY, app);
                System.out.println("Broken right" + ()i);
            }
        }

        /*
         * else if(wDir == 2 && wX%20==0 && wY%20==0){
            for (int i = wY; i < app.lowerBound; i += 20){
                this.blocksToBreak(wX, wY, app);
                System.out.println("Broken down");
            }
        }

        else if (wDir == 3 && wX%20==0 && wY%20==0){
            for (int i = wX; i > app.leftBound; i -= 20){
                this.blocksToBreak(wX, wY, app);
                System.out.println("Broken left");
            }
        }
         */
        

        if(this.alive){
            System.out.println("Entering into 1");
            System.out.println("toDelete size " + this.toDelete.size());
            for(Immobile i : this.toDelete){
                if(i.getClass().getName().equals("gremlins.Destructable")){
                    i.destroy(); 
                    app.getMap().set(app.getMap().indexOf(i), null);
                }
            }
        }
        return 0;
    }

    
}