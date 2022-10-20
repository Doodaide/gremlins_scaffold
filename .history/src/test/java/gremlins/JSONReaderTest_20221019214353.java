package gremlins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * JSONReaderTest
 */
public class JSONReaderTest {

    private JSONReader jr; 

    @BeforeEach
    public void instantiate(){
        String configPath = "config.json";
        jr = new JSONReader(configPath);
    }

    @AfterEach
    public void clear(){
        jr = null; 
        assertNull(jr);
    }

    @Test 
    public void constructor(){
        assertNotNull(jr); 
        assertEquals("", jr.getLayout());
        assertEquals(3, jr.getSpecs().get("lives"));
    }

    @Test 
    public void readSpecsTest(){
        jr.readSpecs(0);
    }
   
}