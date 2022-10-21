package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.junit.jupiter.api.*;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * ExitPortalTestND
 */
public class ExitPortalTestND {

    private ExitPortal e; 

    // Make object 
    @BeforeEach
    public void instantiate(){
        e = new ExitPortal(0, 0, null);
    }

    // Delete object 
    @AfterEach
    public void clear(){
        e = null; 
        assertNull(e);
    }

    // Assert that the constructor doesn't make a null object 
    @Test
    public void constructor(){
        assertNotNull(e);
    }
    
    // Check the non-drawing associated methods 
    @Test
    public void methodTest(){
        // Update essentially does nothing 
        assertEquals(0, e.update());

        assertTrue(e.getViable());
        e.setViable(false);
        assertTrue(e.getViable());
    }
}