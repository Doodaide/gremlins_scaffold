package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * DrawMapTest
 */
public class MapTest {

    private DrawMap d; 
    
    @BeforeEach
    public void instantiate(){
        d = new DrawMap();
    }

    @AfterEach
    public void clear(){
        d = null; 
        assertNull(d); 
    }

    @Test
    public void constructor(){
        assertNotNull(d);
    }

    @Test 
    public void readFile(){
        ; 
    }
    
}