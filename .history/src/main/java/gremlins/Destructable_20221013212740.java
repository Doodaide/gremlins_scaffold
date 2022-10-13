package gremlins; 
import processing.core.PImage;

/**
 * Destructable blocks -
 */

public class Destructable extends Immobile{
    private PImage[] decayTextures; 
    private boolean intact;
    private int currentState = 0;
    public Destructable(int x, int y, PImage defaultTexture, PImage[] decayTextures) {
        super(x, y);
        this.texture = defaultTexture;
        this.decayTextures = decayTextures;
        this.intact = true; 
        this.currentState = 0;
    }
    
    public void destroy(){
        if (this.intact == true){
            this.intact = false; 
            //System.out.println("Block has been destroyed");
            //System.out.println(x);
            
        }
    }

    public int update(){
        //System.out.println("Entering");
        this.texture = decayTextures[this.currentState++];
        if(this.currentState == 4){
            this.currentState = 0;
        }
        return 1;
    }

    public void draw(App app) {
        app.image(this.texture, this.x, this.y);
    }

    @Override
    public boolean getViable() {
        return this.viable;
    }

    @Override
    public void setViable(boolean b) {
        this.viable = b;
        
    }

    public boolean getIntact(){
        return this.intact;
    }

    public int getCurrentState(){
        return this.currentState;
    }

}