package gremlins; 

import processing.core.PImage;

/**
 * FreezePotion
 */
public class FreezePotion extends Immobile{
    private boolean consumed;
    private PImage sprite;
    private int innateCooldown;
    public FreezePotion(int x, int y, PImage sprite) {
        super(x, y);
        this.sprite = sprite;
        this.consumed = false;
        this.innateCooldown = 0;
    }

    public void setConsumed(boolean b){
        this.consumed = b;
    }

    public boolean getConsumed(){
        return this.consumed;
    }

    public int getCooldown(){
        return this.innateCooldown;
    }

    public void setCooldown(int i){
        this.innateCooldown = i;
    }

    @Override
    public int draw(App app) {
        if(this.viable){
            app.image(this.sprite, this.x, this.y);
        }
        return 0;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public void destroy(int x) {
        ;
    }

    @Override
    public boolean getViable() {
        return true;
    }

    @Override
    public void setViable(boolean b) {
        this.viable = false;
    }

    
}