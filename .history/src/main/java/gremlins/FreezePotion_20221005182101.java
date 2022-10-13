package gremlins; 

import processing.core.PImage;

/**
 * FreezePotion
 */
public class FreezePotion extends Immobile{
    private boolean consumed;
    private PImage sprite;
    private 
    public FreezePotion(int x, int y, PImage sprite) {
        super(x, y);
        this.sprite = sprite;
        this.consumed = false;
    }

    public void setConsumed(boolean b){
        this.consumed = b;
    }

    public boolean getConsumed(){
        return this.consumed;
    }

    @Override
    public int draw(App app) {
        app.image(this.sprite, this.x, this.y);
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
        return false;
    }

    @Override
    public void setViable(boolean b) {
        this.viable = false;
    }

    
}