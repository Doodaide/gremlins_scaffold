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
 * IndestructableTestND
 */
public class IndestructableTestND {

    private Indestructable i; 
    
    @BeforeEach
    public void instantiate(){
        i = new Indestructable(0, 0, null);
    }

    @AfterEach
    public void clear(){
        i = null; 
        assertNull(i);
    }

    @Test
    public void constructor(){
        assertNotNull(i); 

        assertEquals(0, i.getX());
        assertEquals(0, i.getY());
    }

    @Test
    public void methodTest(){
        i.destroy(); 
        assertNotNull(i);

        assertTrue(i.getViable());

        i.setViable(false);
        assertTrue(i.getViable());

        assertEquals(0, i.update()); 
        
    }
}