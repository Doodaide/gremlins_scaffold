package gremlins;

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

    @BeforeAll 
    public void object(){
        
    }
    
}