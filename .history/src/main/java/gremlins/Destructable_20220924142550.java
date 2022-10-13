package gremlins; 
import processing.core.PImage;
import processing.core.*; 

public class Destructable extends Immobile{
    private PImage[] decayTextures; 
    private boolean intact;
    private int currentState = 0;
    public Destructable(int x, int y, PImage defaultTexture, PImage[] decayTextures) {
        super(x, y);
        this.texture = defaultTexture;
        this.decayTextures = decayTextures;
        this.intact = true; 
        this.currentState = 0;
    }
    
    public void destroy(int x){
        if (this.intact == true){
            this.intact = false; 
            System.out.println("Block has been destroyed");
            System.out.println(x);
            
        }
        else{
            //System.out.println("bruh");
        }
    }

    public void update(){
        
        this.texture = decayTextures[this.currentState++];
        if(this.currentState == 4){
            this.currentState = 0;
        }
    }

    public int draw(App app) {
        if (intact) {
            //System.out.println("Drawing block default");
            app.image(this.texture, this.x, this.y);
        }
        else{
            
            
        }
        
        return 0;
    }



}