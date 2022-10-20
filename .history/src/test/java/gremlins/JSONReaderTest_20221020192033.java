package gremlins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        // Test that the jr object is not null 
        assertNotNull(jr); 

        // Test that the layout is currently blank
        // Nothing has been fed in yet 
        assertEquals("", jr.getLayout());

        // However, as the number of lives is consistent
        // they should always equal to 3. 
        assertEquals(3, jr.getSpecs().get("lives"));
    }

    @Test 
    public void readSpecsTest(){

        // Read level 1 data 
        try {
            jr.readSpecs(0);

            // check that the name is correct 
            assertEquals("level1.txt", jr.getLayout());            
        
            // Check that the levelSpecs are also correct 
            assertNotNull(jr.getSpecs()); 

            assertEquals(0.3333, jr.getSpecs().get("wizard_cooldown"));
            assertEquals(3.0, jr.getSpecs().get("enemy_cooldown"));
        } catch (Exception e) {
            throw new AssertionError();
        }
        

         // Read level 2 data 
         try {
            jr.readSpecs(1);

            // check that the name is correct 
            assertEquals("level2.txt", jr.getLayout());            
        
            // Check that the levelSpecs are also correct 
            assertNotNull(jr.getSpecs()); 

            assertEquals(2.0, jr.getSpecs().get("wizard_cooldown"));
            assertEquals(1.0, jr.getSpecs().get("enemy_cooldown"));
        } catch (Exception e) {
            throw new AssertionError();
        }

    }

    @Test 
    public void errorTest(){
        // An invalid integer will be fed into readspecs
        try {
            jr.readSpecs(999);
            
            // if no error is raised, something's awry 
            // Thus, an assertion error will be thrown 
        } catch (AssertionError e) {
            // TODO: handle exception
        }
        
    }
   
}