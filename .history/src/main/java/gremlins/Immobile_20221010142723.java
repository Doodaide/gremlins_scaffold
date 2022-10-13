package gremlins;

import processing.core.PImage;

/**
 * Immobile
 */
public abstract class Immobile extends Entity{
    protected PImage texture;
    protected boolean viable;
    public Immobile(int x, int y) {
        super(x, y, 20, 20);  
        this.viable = true;
    }

    public abstract int draw(App app);
    public abstract int update();
    public abstract void destroy();
    public void setViable(boolean b){
        this.viable = b;
    }
    public boolean getViable(){
        return this.viable;
    }
}