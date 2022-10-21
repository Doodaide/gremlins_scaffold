package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PApplet;
import processing.core.PImage;

import org.junit.jupiter.api.*;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * RespawnGeneratorTest
 */
public class RespawnGeneratorTest {

    private RespawnGenerator r;
    private PImage[] s = new PImage[5];
    // I will use a wizard entity to demonstrate its capabilities
    private Wizard w;
    @BeforeEach
    public void instantiate(){ 

        w = new Wizard(40, 40, 2, s);
        r = new RespawnGenerator(w);
    }

    @Test 
    public void constructor(){
        // Test to see that the constructor isn't null 
        assertNotNull(r); 
    }

    @Test 
    public void method(){
        /*
         * To do this, we need to run the app, and get the map
         * Firstly, let's freeze the gremlins 
         */

        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        // Generate map and stun gremlins, as we only focus on the wizard's movements and collisions this time 
        app.delay(1000);
        app.setFreezeActive(true);
        app.noLoop(); // pause everything 
        // Now we have access to the map 

        r.

    }


}