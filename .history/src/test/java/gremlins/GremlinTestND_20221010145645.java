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

    }
}