package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PImage;

import org.junit.jupiter.api.*;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * FireballTestND
 */
public class FireballTestND {

    private Fireball f; 

    @BeforeEach
    public void instantiate(){
        f = new Fireball(0, 0, null); 
    }

    @AfterEach
    public void clear(){
        f = null; 
        assertNull(f);
    }

    @Test 
    public void constructor(){
        assertNotNull(f);
        assertFalse(f.getDecaying()); 

        f.setDecaying(true); 
        assertTrue(f.getDecaying()); 
    }
}