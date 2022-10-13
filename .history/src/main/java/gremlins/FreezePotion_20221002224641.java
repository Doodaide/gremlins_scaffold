package gremlins; 

import processing.core.PImage;

/**
 * FreezePotion
 */
public class FreezePotion extends Immobile{

    PImage sprite;
    public FreezePotion(int x, int y) {
        super(x, y);
        //TODO Auto-generated constructor stub
    }

    @Override
    public int draw(App app) {
        // TODO Auto-generated method stub
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