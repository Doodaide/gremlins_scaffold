package gremlins; 
import processing.core.PImage;
import processing.core.*; 

public class Destructable extends Immobile{
    private PImage[] decayTextures; 
    private boolean intact;
    public Destructable(int x, int y, PImage defaultTexture, PImage[] decayTextures) {
        super(x, y);
        this.texture = defaultTexture;
        this.decayTextures = decayTextures;
        this.intact = true; 
    }
    
    public void destroy(){
        if (this.intact == true){
            this.intact = false; 
            System.out.println("Block has been destroyed");
        }
        else{
            //System.out.println("bruh");
        }
    }


    public int draw(App app) {
        if (intact) {
            app.image(this.texture, this.x, this.y);
        }
        else{
            app.image(this.decayTextures[i], this.x, this.y)
        }
        
        return 0;
    }



}