package gremlins; 

import processing.core.PImage;

/**
 * FreezePotion
 */
public class FreezePotion extends Immobile{

    private PImage sprite;
    public FreezePotion(int x, int y, PImage sprite) {
        super(x, y);
        this.sprite = sprite;
    }

    @Override
    public int draw(App app) {
        app.image(this.texture, this.x, this.y);
        return 0;
    }

    @Override
    public int update() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void destroy(int x) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean getViable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setViable(boolean b) {
        // TODO Auto-generated method stub
        
    }

    
}