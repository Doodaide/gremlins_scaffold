package gremlins; 
import processing.core.PImage;

/**
 * Destructable class: blocks that can be destroyed and deleted by the wizard's projectiles
 */

public class Destructable extends Immobile{
    /**
     * An array of the sequence of decaying images 
     */
    private PImage[] decayTextures; 

    /**
     * Whether the block should be displayed onscreen 
     */
    private boolean intact;

    /**
     * identifies which decay image to show 
     */
    private int currentState = 0;

    /**
     * Constructor for destructable object 
     * @param x x coordinate of destructable block 
     * @param y y coordinate of destructable block 
     * @param defaultTexture the sprite used by a non-destroyed block 
     * @param decayTextures an array of textures that will be displayed upon a wizard projectile collision. 
     */
    public Destructable(int x, int y, PImage defaultTexture, PImage[] decayTextures) {
        super(x, y);
        this.texture = defaultTexture;
        this.decayTextures = decayTextures;
        this.intact = true; 
        this.currentState = 0;
    }
    
    /**
     * Sets intact to false 
     * if the current destructable object is intact. 
     */
    @Override
    public void destroy(){
        if (this.intact == true){
            this.intact = false; 
            //System.out.println("Block has been destroyed");
            //System.out.println(x);
            
        }
    }

    /**
     * Updates the current texture of the 
     */
    @Override
    public int update(){
        //System.out.println("Entering");
        this.texture = decayTextures[this.currentState++];
        if(this.currentState == 4){
            this.currentState = 0;
        }
        return 1;
    }

    /**
     * 
     */
    @Override
    public void draw(App app) {
        app.image(this.texture, this.x, this.y);
    }

    /** 
     * 
     */
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