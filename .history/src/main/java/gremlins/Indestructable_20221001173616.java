package gremlins;

import processing.core.PImage;


/**
 * Indestructable
 */
public class Indestructable extends Immobile{
    public Indestructable(int x, int y, PImage texture) {
        super(x, y);   
        this.texture = texture;
    }
    
    public int draw(App app){
        app.image(this.texture, this.x, this.y);
        return 0; //for collisions later
    }

    @Override
    public void destroy(int x) {
        //System.out.println("indestructable hit");;
    }

    @Override
    public int update() {
        //System.out.println("Hit unbreakable");
        return 0;
        
    }

    @Override
    public boolean getViable() {
        return true;
    }

    @Override
    public void setViable(boolean b) {
        ;
        
    }
}