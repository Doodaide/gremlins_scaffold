package gremlins; 

import processing.core.PImage;

/**
 * FreezePotion class: An extension of the immobile entity that freezes all hostile entities. 
 */
public class FreezePotion extends Immobile{
    /**
     * whether the potion has collided with the wizard
     */
    private boolean consumed;

    /**
     * the potion's sprite
     */
    private PImage sprite;

    /**
     * The length of time the freeze effect lasts
     */
    private int innateCooldown;

    /**
     * Constructor for Freeze potion
     * @param x x coordinate of potion 
     * @param y y coordinate of potion 
     * @param sprite sprite of potion 
     */
    public FreezePotion(int x, int y, PImage sprite) {
        super(x, y);
        this.sprite = sprite;
        this.consumed = false;
        this.innateCooldown = 0;
    }

    /**
     * Setter method for whether the potion has been collided with a wizard mobile entity 
     * @param b a boolean for whether the potion is consumed (not display on screen) or not. 
     */
    public void setConsumed(boolean b){
        this.consumed = b;
    }

    /**
     * Getter method for whether the potion has been consumed by the wizard mobile entity
     * @return boolean for whether the potion has been consumed or not. 
     */
    public boolean getConsumed(){
        return this.consumed;
    }

    /**
     * Getter method for the potion's cooldown (how long the freeze 
     * effect lasts, not the time taken for the potion to respawn)
     * @return integer for how much longer the potion lasts 
     */
    public int getCooldown(){
        return this.innateCooldown;
    }

    /**
     * Setter method for the potion's cooldown (how long the freeze 
     * effect lasts, not the time taken for the potion to respawn)
     * @param i an integer corresponding to whether the time should be incremented up. 
     */
    public void setCooldown(int i){
        this.innateCooldown = i;
    }

    /**
     * Draw method draw the image of the potion on screen. 
     * @param app used to inherit the image() method. 
     */
    @Override
    public void draw(App app) {
        if(!this.consumed){
            app.image(this.sprite, this.x, this.y);
        }
    }

    @Override
    public int update() {
        return 0;
    }


    @Override
    public boolean getViable() {
        return false;
    }

    @Override
    public void setViable(boolean b) {
        this.viable = false;
    }

    
}