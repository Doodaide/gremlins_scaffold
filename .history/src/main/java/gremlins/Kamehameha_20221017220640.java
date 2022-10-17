package gremlins;
import java.util.ArrayList;
import processing.core.PImage;

/**
 * Kamehameha class: A special projectile class that clears out all destructable blocks in a row
 */
public class Kamehameha extends Projectile{
    
    /**
     * Whether the beam is still displayed on screen 
     */
    private boolean drawing; 

    /**
     * An ArrayList of the blocks that should be deleted once the beam dissapates 
     */
    private ArrayList<Immobile> toDelete; 

    /**
     * The amount of time the beam should be displayed on screen 
     */
    private int lifeSpan;

    /**
     * The length of the beam 
     */
    private int beamLength; 

    /**
     * Constructor for Kamehameha object 
     * @param x x coordinate of beam start 
     * @param y y coordinate of beam start 
     * @param sprite Sprite of the beam (there are two available, pointing horizontally or pointing vertically)
     */
    public Kamehameha(int x, int y, PImage sprite) {
        super(x, y, sprite);
        this.drawing = false; 
        this.toDelete = new ArrayList<Immobile>();
        this.lifeSpan = 0;
        this.beamLength = 0;
    }

    /**
     * getDrawing method returns whether the beam is still being drawn
     * @return boolean denoting whether the beam is still being drawn or not
     */
    public boolean getDrawing(){
        return this.drawing; 
    }

    /**
     * setDrawing method sets whether the beam is being drawn 
     * @param b sets the drawing attribute to be either true or false, depending on if the beam is still being drawn 
     */
    public void setDrawing(boolean b){
        this.drawing = b;
    }

    /**
     * Overriden up method, sets the direction to 0
     */
    @Override
    public void up() {
        this.direction = 0;
    }

    /**
     * Overriden right method, sets the direction to 1
     */
    @Override
    public void right() {
        this.direction = 1;
        
    }

    /**
     * Overriden down method, sets the directino to 2
     */
    @Override
    public void down() {
        this.direction = 2;
        
    }

    /**
     * Overriden left method, sets direction to 3
     */
    @Override
    public void left() {
        this.direction = 3;
    }

    /**
     * Setter method for the lifespan of the beam 
     * @param x an integer that corresponds to the lifespan of the beam 
     */
    public void setLifespan(int x){
        this.lifeSpan = x; 
    }

    /**
     * Getter method for the lifespan of the beam 
     * @return returns an integer that corresponds to the lifespan of the beam 
     */
    public int getLifeSpan(){
        return this.lifeSpan;
    }

    /** 
     * Overriden draw method. There's 4 slighlty different if/else if statements in there that essentially draws the beam 
     * in differing directions with slighlty different offsets based. 
    */
    @Override
    public int draw(App app) {
        if(this.direction == 0){ // up or down 
            for(int i = 0; i < this.beamLength; i += 1){
                app.image(sprite, app.getWizard().getX(), app.getWizard().getY() - 20*i - 20);
            }
        }

        else if(this.direction == 1){
            for(int i = 0; i < this.beamLength; i += 1){
                app.image(sprite, app.getWizard().getX() + 20*i + 20, app.getWizard().getY());
            }
        }

        else if (this.direction == 2){
            for(int i = 0; i < this.beamLength; i += 1){
                app.image(sprite, app.getWizard().getX(), app.getWizard().getY() + 20*i + 20);
            }
        }

        else if (this.direction == 3){ // left or right 
            for(int i = 0; i < this.beamLength; i += 1){
                app.image(sprite, app.getWizard().getX() - 20*i - 20, app.getWizard().getY());
            }
        }
        return 0;
    }

    @Override
    public int tick(App app) {
        int wX, wY, wDir; 
        wX = app.getWizard().getX(); 
        wY = app.getWizard().getY(); 
        wDir = app.getWizard().getDirection();

        
        if(wDir == 0 && wX%20==0 && wY%20==0 && app.getWizard().getXVel() == 0){
            this.up();
            this.beamLength = (wY - app.upperBound)/20 + 1;
            for (int i = wY; i > app.upperBound; i -= 20){
                for(Immobile I : app.getMap()){
                    if(I != null){
                        if(I.getX() == wX && I.getY() < wY && !this.toDelete.contains(I)){
                            toDelete.add(I);
                            app.rect(I.getX(), I.getY(), 20, 20);
                            app.fill(255,255,255);
                            break;
                        }
                    }
                }
            }
        }
        

        else if(wDir == 1 && wX%20==0 && wY%20==0 && app.getWizard().getYVel() == 0){
            this.right();
            this.beamLength = (app.rightBound - wX)/20 - 1;
            for (int i = wX; i < app.rightBound; i += 20){
                for(Immobile I : app.getMap()){
                    if(I != null){
                        if(I.getY() == wY && I.getX() > wX && !this.toDelete.contains(I)){
                            toDelete.add(I);
                            app.rect(I.getX(), I.getY(), 20, 20);
                            app.fill(255,255,255);
                            break;
                        }
                    }
                }
            }
        }

        else if(wDir == 2 && wX%20==0 && wY%20==0 && app.getWizard().getXVel() == 0){
            this.down();
            this.beamLength = (app.lowerBound - wY)/20 - 1;
            for (int i = wY; i < app.lowerBound; i += 20){
                for(Immobile I : app.getMap()){
                    if(I != null){
                        if(I.getX() == wX && I.getY() > wY && !this.toDelete.contains(I)){
                            toDelete.add(I);
                            app.rect(I.getX(), I.getY(), 20, 20);
                            app.fill(255,255,255);
                            break;
                        }
                    }
                }
            }
        }

        else if (wDir == 3 && wX%20==0 && wY%20==0 && app.getWizard().getYVel() == 0){
            this.left();
            this.beamLength = (wX - app.leftBound)/20 + 1;
            for (int i = wX; i >= app.leftBound; i -= 20){
                for(Immobile I : app.getMap()){
                    if(I != null){
                        if(I.getY() == wY && I.getX() < wX && !this.toDelete.contains(I)){
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
            for(Immobile i : this.toDelete){
                if(i.getClass().getName().equals("gremlins.Destructable")){
                    app.getMap().set(app.getMap().indexOf(i), null);
                }
            }
        }
        return 0;
    }

    public void update(App app){
        if(!this.getDrawing()){
            this.tick(app);
            this.setLifespan(this.getLifeSpan() + 1);
            this.setDrawing(true);
        }
        
        if(this.getDrawing()){
            this.setLifespan(this.getLifeSpan() + 1);
            this.draw(app);
            
        }
    }

    public static void stuffUI(App app){
        if(app.getKCharge() >= 0){
            app.text("R to clear all destructable blocks in front of you", 20, 710);
            for(int i = 0; i < app.getKCharge(); i++){
                app.image(app.hBeam, 375 + i*25, 695);
            }
        }
    }

}