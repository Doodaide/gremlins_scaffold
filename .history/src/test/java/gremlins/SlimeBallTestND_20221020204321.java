package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.junit.jupiter.api.*;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * SlimeBallTestND
 */
public class SlimeBallTestND {

    private SlimeBall s; 

    // Instantiate a slimeball 
    @BeforeEach
    public void instantiate(){
        s = new SlimeBall(0, 0, null, null); 
    }

    // Delete it afterwards 
    @AfterEach
    public void clear(){
        s = null; 
        assertNull(s);
    }

    @Test
    public void constructor(){
        // Check constructor works 
        assertNotNull(s);
    }

    @Test
    public void methodTest(){
            
        // This is essentially just routine directionTesting and stuff 
        // The following 4 tests assess whether the up, right, down, and left 
        // methods make the slimeball have a certain set of expected behaviours. 

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
        assertEquals(0, s.getYVel()); 
        assertEquals(-4, s.getXVel()); 
        assertEquals(3, s.getDirection());    
    }

    @Test
    public void hitTest(){
        assertTrue(s.getAlive());
        s.hit();
        assertFalse(s.getAlive());
    }
}