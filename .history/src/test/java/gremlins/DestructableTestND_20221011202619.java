package gremlins;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import processing.core.PApplet;
import processing.core.PImage;

import static org.junit.jupiter.api.Assertions.*;

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
    
}