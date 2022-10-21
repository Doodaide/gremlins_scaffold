package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.junit.jupiter.api.*;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * IndestructableTestND
 */
public class IndestructableTestND {

    private Indestructable i; 
    // Instantiate object 
    @BeforeEach
    public void instantiate(){
        i = new Indestructable(0, 0, null);
    }
    // Delete object 
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
        assertNotNull(i);

        assertTrue(i.getViable());

        i.setViable(false);
        assertTrue(i.getViable());

        assertEquals(0, i.update()); 
        
    }
}