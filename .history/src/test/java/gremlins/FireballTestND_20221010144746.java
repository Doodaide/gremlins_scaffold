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
        assertTrue(f.getAlive());
        f.setDecaying(true); 
        assertTrue(f.getDecaying()); 
    }
    
    @Test
    public void methodTest(){
        assertEquals(4, f.getSpeed());

        f.up(); 
        assertEquals(-4, f.getYVel()); 
        assertEquals(0, f.getXVel()); 
        assertEquals(0, f.getDirection()); 

        f.right();
        assertEquals(0, f.getYVel()); 
        assertEquals(4, f.getXVel()); 
        assertEquals(1, f.getDirection()); 

        f.down();
        assertEquals(4, f.getYVel()); 
        assertEquals(0, f.getXVel()); 
        assertEquals(2, f.getDirection()); 

        f.left();
        assertEquals(0, f.getYVel()); 
        assertEquals(-4, f.getXVel()); 
        assertEquals(0, f.getDirection()); 
    }
}