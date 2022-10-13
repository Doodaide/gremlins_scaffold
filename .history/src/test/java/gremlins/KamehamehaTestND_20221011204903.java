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
 * KamehamehaTestND
 */
public class KamehamehaTestND {

    private Kamehameha k; 

    @BeforeEach
    public void object(){
        k = new Kamehameha(0, 0, null); 
    }

    @AfterEach 
    public void clear(){
        k = null; 
    }

    @Test
    public void constructor(){
        assertNotNull(k);

        assertFalse(k.getDrawing());
        k.setDrawing(true); 
        assertTrue(k.getDrawing());

        assertEquals(0, k.getLifeSpan()); 
        k.setLifespan(10); 
        assertEquals(10, k.getLifeSpan());
    }

    @Test
    public void directionTest(){
        k.up(); 
        assertEquals()
    }
}