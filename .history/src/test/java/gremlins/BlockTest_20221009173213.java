package gremlins;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

/**
 * BlockTest
 */

@TestInstance(Lifecycle.PER_CLASS) 
public class BlockTest {

    private Destructable d; 
    private ExitPortal e;
    private Indestructable i;
    private App app;

    @BeforeAll 
    public void object(){
        app = new App();
        d = new Destructable(0, 0, null, null);
        e = new ExitPortal(0, 0, null);
        i = new Indestructable(0, 0, null);
    }
    
    @Test
    public void constructor(){
        assertNotNull(d);
        assertNotNull(e);
        assertNotNull(i);
    }

    @Test
    public void destructableTest(){
        assertTrue(this.intact);
        
    }

}