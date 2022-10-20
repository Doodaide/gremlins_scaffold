package gremlins;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.opentest4j.AssertionFailedError;

import java.util.*; 
import java.io.*;
/**
 * MapTest
 */
@TestInstance(Lifecycle.PER_CLASS)
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
    public void readFileTest(){

        // The parseLayout should throw an AssertionError()
        try {
            r.parseLayout("");

            // A fileNotFound Exception should be thrown 
            // if the assertionError is not thrown in parseLayout 
            throw new FileNotFoundException(); 
        } catch (AssertionError e) {
            ; // If an assertionError is thrown, nothing should happen 
        } catch(FileNotFoundException f){
            // however, if a fileNotFound exception is seen, this means 
            // Something has gone wrong. Hence, an Assertion should be raised 
            throw new AssertionError();
        } catch(Exception g){
            ;
        }

        // Feeding in a valid string
        try {
            r.parseLayout("level0.txt");
            assertNotNull(r.getMapContents());
        } catch (Exception e) {
            throw new AssertionError();
        }
    }
    
    @Test 
    public void drawMapTest(){
        r.parseLayout("level1.txt");
        
    }
    
    
}