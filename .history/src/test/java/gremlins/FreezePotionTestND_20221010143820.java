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
 * FreezePotionTestND
 */
public class FreezePotionTestND {

    private FreezePotion p;
    
    @BeforeEach
    public void instantiate(){
        p = new FreezePotion(0, 0, null);
    }

    @AfterEach
    public void clear(){
        p = null; 
        assertNull(p);
    }

    @Test
    public void constructor(){
        assertNotNull(p);

        assertEquals(0, p.getX()); 
        assertEquals(0, p.getY());
    }

    @Test 
    public void methodTest(){
        
    }
}