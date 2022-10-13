package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PImage;

import org.junit.jupiter.api.*;

@TestInstance(Lifecycle.PER_CLASS)
/**
 * IndestructableTestND
 */
public class IndestructableTestND {

    private Indestructable i; 
    
    @BeforeAll
    public void instantiate(){
        i = new Indestructable(0, 0, null);
    }

    @AfterAll
    public void clear(){
        i = null; 
    }

    @Test
    public void constructor(){
        assertNotNull(i); 

        assertEquals(0, i.getX());
        assertEquals(0, i.getY());
    }
}