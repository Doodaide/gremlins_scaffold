package gremlins;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import org.junit.jupiter.api.TestInstance.Lifecycle;

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
        // Make objects 
        r = new ReadMap();
        d = new DrawMap();
    }

    @Test
    public void constructor(){
        // Test to see that the objects aren't null pointers 
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
    
    //@Test 
    public void drawMapTest(){
        
        /*
         *The drawMap method is more difficult to test 
         as the map is drawn within the App class when instantiated 
         as part of the setup. 
         Most of the drawMap stuff is tested within BigTest 
         */
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        app.delay(1000);

        try {
            r.parseLayout("level1.txt");
        } catch (Exception e) {
            System.err.println("File not found");
        }

        d.generateMap(r.getMapContents(), app);
        assertNotNull(app.getMap());

        app.delay(5000);
    }

    @Test 
    public void errorTest(){
        // Try feeding in a string that doesn't exist 
        try {
            r.parseLayout("level9090.txt");
        } catch (FileNotFoundException e) {
            
        }
    }
    
    
}