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
 * SlimeBallTestND
 */
public class SlimeBallTestND {

    private SlimeBall s; 

    @BeforeEach
    public void instantiate(){
        s = new SlimeBall(0, 0, null, null); 
    }

    @AfterEach
    public void clear(){
        s = null; 
        assertNull(s);
    }

    @Test
    public void constructor(){
        assertNotNull(s);
    }

    @Test
    public void methodTest(){
        s.up(); 
        assertEquals(-4, s.getYVel()); 
        assertEquals(0, s.getXVel()); 
        assertEquals(0, s.getDirection()); 

        s.right();
        assertEquals(0, s.getYVel()); 
        assertEquals(4, s.getXVel()); 
        assertEquals(1, s.getDirection()); 

        s.down();
        assertEquals(4, s.getYVel()); 
        assertEquals(0, s.getXVel()); 
        assertEquals(2, s.getDirection()); 

        s.left();
        assertEquals(0, f.getYVel()); 
        assertEquals(-4, f.getXVel()); 
        assertEquals(3, f.getDirection()); 
        
    }
}