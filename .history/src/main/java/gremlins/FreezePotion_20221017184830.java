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
     * Draw method draw the image of the potion on screen. "Draws" the image onscreen iff the potion is not consumed
     * @param app used to inherit the image() method. 
     */
    @Override
    public void draw(App app) {
        if(!this.consumed){
            app.image(this.sprite, this.x, this.y);
        }
    }

    /**
     * The potion has no update states
     * @return int 0, as it is not a destructable block that returns a 1. 
     */
    @Override
    public int update() {
        return 0;
    }

    /**
     * Getter method for the potion's viability (to be hit)
     * The potion is never 'collidable' by projectiles, which means the wizard's fireballs can pass through. 
     * @return false 
     */
    @Override
    public boolean getViable() {
        return false;
    }

    /** 
     * Setter method for the potion's viability 
     * The potion is never collidable, so despite the aguments supplied, the viability will always be false.
     * @param b boolean
     */
    @Override
    public void setViable(boolean b) {
        this.viable = false;
    }

    public void update(App app){
        if(!app.getPowerUpAvailable() && this.getConsumed() && app.getFreezeActive()){ // consumed and not on screen 
            if(this.getCooldown() == 5*App.FPS){
                this.setCooldown(0);
                app.setFreezeActive(false);
            }

            else if (this.getCooldown() < 5*App.FPS){
                this.setCooldown(this.getCooldown() + 1);
            }
        }
    }

    public static int[] spawn(App app){
        RespawnGenerator respawn = new RespawnGenerator(app.getWizard());
        int[] coords = {0,0};
        boolean canBreak = false;
        while(!canBreak){
            coords = respawn.respawnXBlocks(app);
            for(Gremlin g : app.getGArray()){
                if(coords[0] == g.getX() && coords[1] == g.getY()){
                    break;
                }
                canBreak = true;
            }
        }
        return coords;
    }

    public void tick(App app){
        if(!this.getConsumed()){
            app.getMap().add(this); 
        }
        
        else if(this.innateCooldown == 0){
            System.out.println("p nulled");
            app.setPowerUpAvailable(false);
            this.setFreezeActive(false);
            p = null;
        }

        else if(this.getConsumed() && this.innateCooldown == 20*FPS){ 
            this.mapToDraw.set(mapToDraw.indexOf(p), null);
            this.powerUpSpawned = false;
            this.innateCooldown -= 1;
        }

        else if (this.getConsumed() && this.innateCooldown <= 20*FPS){
            this.innateCooldown -= 1;
        }
    }
}