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
 * GremlinTestND
 */
public class GremlinTestND {

    private Gremlin g; 

    @BeforeEach
    public void instantiate(){
        g = new Gremlin(0, 0, null, null, null, null);
    }

    @AfterEach
    public void clear(){
        g = null; 
        assertNull(g); 
    }

    @Test
    public void constructor(){
        assertNotNull(g); 
        assertEquals(1, g.getSpeed()); 
    }

    @Test
    public void movementTest(){
        g.up(); 
        assertEquals(0, g.getDirection()); 
        assertEquals(0, g.yDir); 
        assertEquals(-1, g.get)

        g.right();
        assertEquals(1, g.getDirection()); 

        g.down(); 
        assertEquals(2, g.getDirection()); 

        g.left(); 
        assertEquals(3, g.getDirection()); 
    }
}