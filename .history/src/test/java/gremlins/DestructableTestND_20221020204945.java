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

    // Make an object 
    @BeforeEach
    public void object(){
        textures = new PImage[4];
        for(int i = 0; i < textures.length; i++){
            textures[i] = null; 
        }
        d = new Destructable(0, 0, null, textures);
    }

    // Clear that object 
    @AfterEach
    public void clear(){
        d = null; 
        assertNull(d);
    }
    
    @Test
    public void constructor(){
        // Tests that the constructor isn't null producing 
        assertNotNull(d); 

        // Test whether the coordinates are in the right places 
        assertEquals(0, d.getX()); 
        assertEquals(0, d.getY());

        // Test that an "un hit" block is viable 
        assertTrue(d.getViable());
        
        // Check that the current state of the block is 0. 
        assertEquals(0, d.getCurrentState());
    }

    @Test
    public void methodTest(){

        // Test update function (which destruction state to draw)
        assertTrue(d.getViable()); 
        d.setViable(false); 
        assertFalse(d.getViable()); 

        d.update();
        assertEquals(1, d.getCurrentState());
        d.update(); 
        assertEquals(2, d.getCurrentState());
        d.update();
        assertEquals(3, d.getCurrentState());
        
        // after the 4th update, d should go back to original state
        d.update(); 
        assertEquals(0, d.getCurrentState());
        
    }

}