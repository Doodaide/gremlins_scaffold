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

        // some random output is going to be spit out
        int[] output = r.respawnXBlocks(app);
        /*
         * things to assess: 
         * whether the coordinates are NOT equal to any blocks 
         * in the current map 
         * 
         * Gremlin/other mobiles don't need to be tested, solely 
         * because that wasn't included in as a check in the 
         * respawn generator. As I intended to use this 
         * generator for the teleport, gremlins, and potion, 
         * it makes no sense to make it so that something can't spawn 
         * on top of another mobile. 
         */

        for (int i = 0; i < app.getMap().size(); i++) {
            assertNotEquals(, output[0]);
            assertNotEquals(app.getMap().get(i).getY(), output[1]); 
            boolean statement = (app.getMap().get(i).getX()) && ()
            assertFalse()
        }

    }


}