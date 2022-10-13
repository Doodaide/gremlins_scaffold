package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PImage;

import org.checkerframework.checker.units.qual.A;
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
        assertEquals(-1, g.getYVel()); 

        g.right();
        assertEquals(1, g.getDirection()); 
        assertEquals(1, g.xDir); 
        assertEquals(1, g.getXVel());

        g.down(); 
        assertEquals(2, g.getDirection()); 
        assertEquals(2, g.yDir); 
        assertEquals(1, g.getYVel()); 

        g.left(); 
        assertEquals(3, g.getDirection());
        assertEquals(3, g.xDir);
        assertEquals(-1, g.getXVel());
    }

    @Test
    public void gremlinAITest(){
        // !How to test this?
    }

    @Test
    public void basicSlimeBallTest(){
        ArrayList<SlimeBall> sArr = new ArrayList<SlimeBall>();
        
        assertNotNull(g.getAvailableSlimeballs());
        assertEquals(0, g.getAvailableSlimeballs().size());

        g.setAvailableSlimeballs(sArr);
        assertNotNull(g.getAvailableSlimeballs());
        assertEquals(sArr, g.getAvailableSlimeballs());
        assertEquals(0, g.getSlimeCoolDown());
        g.setSlimeCooldown(10);
        assertEquals(10, g.getSlimeCoolDown());
        g.shootSlimeBall();
    }

    @Test
    public void shootSlimeBallTest(){
        
    }
}