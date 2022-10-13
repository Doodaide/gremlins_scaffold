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
        if(this.direction ==0){ // up or down 
            for(int i = app.getWizard().getY(); i < this.beamLength; i -= 20){
                app.image(sprite, i, i);
            }
        }

        else if(this.direction == 1){

        }

        else if (this.direction == 2){

        }

        else if (this.direction == 3){ // left or right 

        }
        return 0;
    }

    @Override
    public int tick(App app) {
        int wX, wY, wDir; 
        wX = app.getWizard().getX(); 
        wY = app.getWizard().getY(); 
        wDir = app.getWizard().getDirection();

        
        if(wDir == 0 && wX%20==0 && wY%20==0){
            this.beamLength = (wY - app.upperBound)/20 - 1;
            for (int i = wY; i > app.upperBound; i -= 20){
                for(Immobile I : app.getMap()){
                    if(I != null){
                        if(I.getX() == wX && I.getY() < wY && !this.toDelete.contains(I)){
                            System.out.println("Found I " + I);
                            toDelete.add(I);
                            app.rect(I.getX(), I.getY(), 20, 20);
                            app.fill(255,255,255);
                            break;
                        }
                    }
                }
            }
        }
        

        else if(wDir == 1 && wX%20==0 && wY%20==0){
            this.beamLength = (app.rightBound - wX)/20 - 1;
            for (int i = wX; i < app.rightBound; i += 20){
                for(Immobile I : app.getMap()){
                    if(I != null){
                        //System.out.println(I.getX() + ", "+ I.getY());
                        if(I.getY() == wY && I.getX() > wX && !this.toDelete.contains(I)){
                            System.out.println("Found I " + I);
                            toDelete.add(I);
                            app.rect(I.getX(), I.getY(), 20, 20);
                            app.fill(255,255,255);
                            break;
                        }
                    }
                }
            }
        }

        else if(wDir == 2 && wX%20==0 && wY%20==0){
            this.beamLength = (app.lowerBound - wY)/20 - 1;
            for (int i = wY; i < app.lowerBound; i += 20){
                for(Immobile I : app.getMap()){
                    if(I != null){
                        //System.out.println(I.getX() + ", "+ I.getY());
                        if(I.getX() == wX && I.getY() > wY && !this.toDelete.contains(I)){
                            System.out.println("Found I " + I);
                            toDelete.add(I);
                            app.rect(I.getX(), I.getY(), 20, 20);
                            app.fill(255,255,255);
                            break;
                        }
                    }
                }
            }
        }

        else if (wDir == 3 && wX%20==0 && wY%20==0){
            this.beamLength = (app.lowerBound - wY)/20 - 1;
            for (int i = wX; i > app.leftBound; i -= 20){
                for(Immobile I : app.getMap()){
                    if(I != null){
                        //System.out.println(I.getX() + ", "+ I.getY());
                        if(I.getY() == wY && I.getX() < wX && !this.toDelete.contains(I)){
                            System.out.println("Found I " + I);
                            toDelete.add(I);
                            app.rect(I.getX(), I.getY(), 20, 20);
                            app.fill(255,255,255);
                            break;
                        }
                    }
                }
            }
        }
        

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