package gremlins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import java.util.*;
import java.io.*;

/**
 * BigTest
 */
public class BigTest {

    @Test
    public void wizardStaticTest(){

         // This test will examine how the wizard responds to some commands that will keep it static (in place, i.e.
        // its coordinates are not changing)
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        // This is used to stun the gremlins for the moment. This ensures no unwanted interactions between entities occurrs 
        app.delay(1000);
        app.setFreezeActive(true);
        
        
        // checks the current image is not null
        assertNotNull(app.getWizard().getCurrentImg());

        // checks the current image is facing left (default generation )
        assertEquals(app.wizardSprites[3], app.getWizard().getCurrentImg()); 

        // assesses current coordinates to be where expected on the map
        assertEquals(40, app.getWizard().getX());
        assertEquals(20, app.getWizard().getY());

        // Direction of wizard changed (but collided into a block)
        app.getWizard().up();

        app.delay(500);

        // Check the image is correctly pointing up 
        assertEquals(app.wizardSprites[0], app.getWizard().getCurrentImg());
        app.delay(500);

        // Check that the wizard is colliding with the correct things 
        // Should be colliding into an indestructable block 
        assertNull(app.getWizard().collisionEntity);
        assertNotNull(app.getWizard().collisionBlock);
        assertEquals("gremlins.Indestructable", app.getWizard().collisionBlock.getClass().getName());
        app.delay(500);

        // Should be colliding into a destructable block 
        app.getWizard().down();
        app.delay(500);
        assertNull(app.getWizard().collisionEntity);
        assertNotNull(app.getWizard().collisionBlock);
        assertEquals("gremlins.Destructable", app.getWizard().collisionBlock.getClass().getName());
        app.delay(1000);
    }

    @Test
    public void wizardDynamicTest(){

        // this test will make a new app, and then run the wizard through a set of programmed behaviours
        // The interactions of the wizard, in conjunction with the different objects, and the app itself will be tested
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        // Generate map and stun gremlins, as we only focus on the wizard's movements and collisions this time 
        app.delay(1000);
        app.setFreezeActive(true);
        assertNotNull(app.getWizard()); // non null wizard 
        assertEquals(3, app.getWizard().draw(app)); // assert that the current wizard (not moving) is not drawing either 
        app.delay(500);
        // Move the wizard right 
        app.getWizard().right();
        app.delay(500);
        // A collision with a destructable block is expected, but not any entity
        assertEquals("gremlins.Destructable",app.getWizard().collisionBlock.getClass().getName());
        assertNull(app.getWizard().collisionEntity);

        // Now the wizard is moved down to hit another block
        app.getWizard().stop();
        app.getWizard().down();
        app.delay(3000);
        
        // A destructable block is now hit. 
        assertNotNull(app.getWizard().collisionBlock);
        assertEquals("gremlins.Destructable", app.getWizard().collisionBlock.getClass().getName());
        assertEquals(2, app.getWizard().getDirection());
        // No movement/change in the x or y coordinates should be observed even when repeated down commands are spammed 
        // Or other invalid commands (such as attempting diagonal movement, or going left, as it will collide with an 
        // Indestructable block, all of which will be simualted and tested)
        int beforeX, beforeY;
        beforeX = app.getWizard().getX();
        beforeY = app.getWizard().getY();

        // Simulates spamming the down key
        app.getWizard().down(); 
        app.getWizard().down();
        app.delay(500);
        assertEquals(beforeX, app.getWizard().getX());
        assertEquals(beforeY, app.getWizard().getY());
        app.delay(500);

        // Simulates trying to go left, into another indestructable block 
        // No change in coordinates should be observed 
        app.getWizard().left(); 
        app.getWizard().left();
        app.delay(500);
        assertEquals(beforeX, app.getWizard().getX());
        assertEquals(beforeY, app.getWizard().getY());
        app.delay(500);

        // Simulates an invalid set of movement commands, as two commands were essentially simultaneously requested. As such,
        // The wizard is not expected to move at all
        app.getWizard().up();
        app.getWizard().right();
        app.delay(500);
        assertEquals(beforeX, app.getWizard().getX());
        assertEquals(beforeY, app.getWizard().getY());
        app.getWizard().stop(); 

        // Teleportation check
        int currentX = app.getWizard().getX(); 
        int currentY = app.getWizard().getY(); 

        // This will check whether the coordinates have changed to at least 10 blocks away. 
        app.getWizard().tp(app);
        app.delay(500); 

        boolean statement1 = ((currentX >= app.getWizard().getX() + 200) || (currentX <= app.getWizard().getX() - 200));
        boolean statement2 = ((currentY >= app.getWizard().getY() + 200) || (currentY <= app.getWizard().getY() - 200));

        // it just must be the case that the respawn is at least 10 blocks away from the wizard in either direction
        // Thus, we check whether we are greater than or less than x and y + 10 blocks. If either of the conditions are true
        // We know the teleportation has located sufficient coordinates. 
        assertTrue(statement1 || statement2);
        app.delay(1000);
        
        // Now we must assert that the wizard has not teleported into any immobile blocks 

        try {
            for(Immobile i : app.getMap()){
                if(i.getX() == app.getWizard().getX() && i.getY() == app.getWizard().getY()){
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            throw new AssertionError();
        }
    }

    @Test 
    public void wizardImmobileInteractionTest(){
        // There are four immobile type objects in this game: the destructable, the indestructable, exit portal, and freeze potion 
        // Each will be tested sequentially concerning their interactions with the wizard 
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        // Generate map and stun gremlins, as we only focus on the wizard's movements and collisions this time 
        app.delay(1000);
        app.setFreezeActive(true);
        assertNotNull(app.getWizard()); // non null wizard 
        
        // Fire a fireball at the indestructable block on the left 
        // Nothing apart from the fireball dissapating is expected 
        app.noLoop();
        app.getWizard().shoot();
        app.draw();
        //app.delay(10); 
        assertEquals(1, app.getWizard().getExistingFireballs().size());
        app.delay(10);
        app.loop();
        app.delay(500);
        // by this point, the fireball should have hit the block, and become nulled. Then, the arrayList updater will have removed it from 
        // The array of existing fireballs. 
        assertEquals(0, app.getWizard().getExistingFireballs().size());
        app.getWizard().right();
        app.delay(500);
        app.getWizard().stop();
        app.getWizard().shoot();

        // Fireball has just spawned in, which means it hasn't had any time to hit anything 
        // Thus, all of its collisions, etc. have not been calculated yet
        Fireball f = app.getWizard().getExistingFireballs().get(0);
        assertNotNull(f);
        assertNull(f.collisionBlock);

        app.noLoop();
        // Increment 1 in the future
        app.draw();
        assertEquals("gremlins.Destructable",f.collisionBlock.getClass().getName());
        // Now to test the how the block responds to the fireball collision 
        assertNotNull(f.collisionBlock);

        // As the frame has been incremented by 1, and the fireball has hit something, the f frame counter will have increased by 1.
        assertEquals(1, f.getFrameCounter());

        // The fireball should now be decaying, and the block it hit should not be "contactable" by anything else 
        assertTrue(f.getDecaying()); 
        assertFalse(f.collisionBlock.getViable());

        // The brickwall's texture should still be the same
        assertEquals(app.brickwall, f.collisionBlock.texture);
        // three sets of 4 frames later: 
        for(int j = 0; j < 3; j++){
            for(int i = 0; i < 4; i++){
                app.draw();
            }
            // Check whether every 4 frames, the image of the block is updating 
            assertEquals(app.brickwallDestruction[j], f.collisionBlock.texture);
        }

        // 4 more frames in the future
        for(int i = 0; i < 4; i++){
            app.draw();
        }
        // Now the block the fireball hit should register as null, and be deleted from the ArrayList 
        assertNull(f.collisionBlock);

        // This bit is pretty self explanatory
        assertFalse(app.getMap().contains(f.collisionBlock));
        app.delay(1000); 
        app.loop();

        app.getWizard().shoot();
        app.getWizard().shoot();
        app.getWizard().shoot();
        
        app.delay(3000);
        // at this point, we wait until the potion spawns
        assertNotNull(app.getPowerUp());
        app.getWizard().shoot();
        //nothing should happen 
        assertNotNull(app.getPowerUp());
        assertTrue(app.getPowerUpAvailable());

        app.setFreezeActive(false);
        assertFalse(app.getFreezeActive());
        // Wizard collides with Freeze potion 
        app.getWizard().right(); 
        app.delay(1000); 

        assertTrue(app.getFreezeActive());
        
        // Take a test gremlin (from gArray[0])

        app.getGArray().get(0).

        // The delay for the potion is 5 seconds 
        // So after this 5 second timer, the potion is guaranteed to have 'dissapated'

        app.delay(5000);
        assertFalse(app.getFreezeActive());



        app.delay(4000); 

    }

   
}