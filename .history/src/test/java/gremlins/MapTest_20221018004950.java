package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import java.util.*; 
import java.io.*;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * MapTest
 */
public class MapTest {

    private DrawMap d; 
    private ReadMap r;
    
    @BeforeEach
    public void instantiate(){
        r = new ReadMap();
        d = new DrawMap();
    }

    @AfterEach
    public void clear(){
        d = null; 
        r = null; 
        assertNull(d); 
    }

    @Test
    public void constructor(){
        assertNotNull(d);
        assertNotNull(r);
    }

    @Test 
    public void readFile(){
        r.parseLayout(null);;
    }
    
}