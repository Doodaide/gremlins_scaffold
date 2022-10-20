package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

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
    
    @BeforeAll
    public void instantiate(){
        r = new ReadMap();
        d = new DrawMap();
    }

    @Test
    public void constructor(){
        assertNotNull(d);
        assertNotNull(r);
    }

    @Test 
    public void readFile(){
        r.parseLayout(""); // !How to get error catching 
        r.parseLayout("level0.txt");
        assertNotNull(r.getMapContents());
    }
    
}