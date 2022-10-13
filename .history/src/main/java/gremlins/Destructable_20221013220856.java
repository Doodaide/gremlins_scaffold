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
        this.currentState = 0;
    }

    /**
     * Updates the current texture of the destructable block 
     * by setting the current state. 
     * @return 1 for destructable bricks to recgonise that they have been updated. 
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
     * @param app the PApplet, which this method inherits the image method from. 
     */
    @Override
    public void draw(App app) {
        app.image(this.texture, this.x, this.y);
    }

    /** 
     * Method that establishes whether a block can still be "hit" by projectiles 
     * @return Returns whether the block can still engage in collisions. 
     */
    @Override
    public boolean getViable() {
        return this.viable;
    }

    /** 
     * Setter Method that sets whether a block can be hit be a projectile
     * @param b a boolean that sets whether a block can/cannot be "hit" by a projectile 
     */
    @Override
    public void setViable(boolean b) {
        this.viable = b;
        
    }

    /**
     * Getter method that gets wehther a block can be hit by a wizard projectile
     * @return returns a boolean for whether 
     */
    public int getCurrentState(){
        return this.currentState;
    }

}