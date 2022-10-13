package gremlins;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import processing.core.PApplet;
import processing.core.PImage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;

@TestInstance(Lifecycle.PER_CLASS)
/**
 * DestructableTestND
 */
public class DestructableTestND {

    private Destructable d; 
    private PImage[] textures; 

    @BeforeEach
    public void object(){
        textures = new PImage[4];
        for(int i = 0; i < textures.length; i++){
            textures[i] = null; 
        }
        d = new Destructable(0, 0, null, textures);
    }

    @AfterEach
    public void clear(){
        d = null; 
        assertNull(d);
    }
    
    @Test
    public void 
}