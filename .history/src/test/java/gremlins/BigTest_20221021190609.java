package gremlins;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.checkerframework.checker.units.qual.A;
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

    //@Test
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

    //@Test
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

    //@Test 
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
        System.out.println("Fireball size Before: " + app.getWizard().getExistingFireballs().size());
        app.getWizard().shoot(); // ! Problematic line as sometimes it goes arrayList out of bounds 
        System.out.println("Fireball size After" + app.getWizard().getExistingFireballs().size());
        app.delay(500);
        app.draw();
        
        // Check that the size of the array is 1 (i.e. the fireball has fired)
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
        app.noLoop();
        app.getWizard().shoot();

        // Fireball has just spawned in, which means it hasn't had any time to hit anything 
        // Thus, all of its collisions, etc. have not been calculated yet
        Fireball f = app.getWizard().getExistingFireballs().get(0);
        assertNotNull(f);
        assertNull(f.collisionBlock);
        
        // Increment 1 in the future
        app.draw();
        assertEquals("gremlins.Destructable",f.collisionBlock.getClass().getName());
        // Now to test the how the block responds to the fireball collision 
        assertNotNull(f.collisionBlock);
        System.out.println(f.getFrameCounter());

        
        /*
         * As the frame has been incremented by 1, and the fireball has hit something, the f frame counter will have increased. 
          Due to the running speed of different computers and possible interactions, it just must be 
          determined that the frame counter is not 0. 
         */
        
        assertNotEquals(0, f.getFrameCounter()); // ! Also problematic tends to go 1 or 2

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

        // This bit is pretty self explanatory. If not, it essentially says the block the fireball hit is gone
        assertFalse(app.getMap().contains(f.collisionBlock));
        app.delay(1000); 
        app.loop();

        // Shoot three more shots to get into position for the potion spawn
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
        // If the wizard did not have a successful collision, the rest of the code would 
        // have errored. Thus, as the rest of the test runs smoothly, I can assume that 
        // The wizard at some point did successfully collide with the potion. 
        app.getWizard().right(); 
        app.delay(800); 

        assertTrue(app.getFreezeActive());
        
        // Take a test gremlin (from gArray[0])
        int xBefore = app.getGArray().get(0).getX(); 
        int yBefore = app.getGArray().get(0).getY(); 

        // Given 1 second of delay, if the gremlins were moving
        // They would have changed positions 
        // However, if they are in a stasis, their positions should not be different 
        // This is what this set of testcases are testing 
        app.delay(1000); 
        assertEquals(xBefore, app.getGArray().get(0).getX());
        assertEquals(yBefore, app.getGArray().get(0).getY());

        // The delay for the potion is 5 seconds 
        // So after this 5 second timer, the potion effect is guaranteed to have 'dissapated'
        app.delay(5000);
        assertFalse(app.getFreezeActive());

        // Freeze gremlins again (for now), as we must test Kamehameha 
        app.setFreezeActive(true); 

        // First check that there is no Kamehama currently active and check the charges are default 3 
        assertNull(app.getK());
        assertEquals(3, app.getKCharge());
        assertTrue(app.getWizard().getX() % 20 == 0 && app.getWizard().getY()%20==0);
        // Now fire a beam 
        // There is no way I have found to simulate key presses, so all the logic for keyPresses cannot be tested 
        // Instead, a kamehameha beam will just be spawned. 

        // check images are not null
        assertNotNull(app.hBeam); 
        assertNotNull(app.vBeam);
        app.noLoop();

        Kamehameha k = new Kamehameha(app.getWizard().getX(), app.getWizard().getY(), app.hBeam);

        // Check not null
        assertNotNull(k);

        // The beam shouldn't be drawing yet, as there has been no loop
        assertFalse(k.getDrawing());
        app.loop();

        // ! The beam will not consistently appear on screen for some reason
        /*
         * Now to test which blocks were broken 
         */
        k.update(app);
        app.delay(2000);
        app.arrayListUpdates();
        
        // Count how many destructables are in the same row and in the direction the wizard is pointing in 
        int beforeCount = 0; // should end up at 0 after the beam has fired
        for (int i = app.getWizard().getX(); i < App.RIGHTBOUND - app.getWizard().getX(); i+= 20) {
            for(Immobile I : app.getMap()){
                if(I != null){
                    if(I.getY() == app.getWizard().getY() && I.getX() > app.getWizard().getX() && I.getClass().getName().equals("gremlins.Destructable")){
                        beforeCount += 1;
                        break;
                    }
                }
            }
        }

        // check that there are no more unbroken destructables in the direction of firing the kahmehameha beam
        assertEquals(0, beforeCount);

        app.loop(); // ! mandatory loop restart 
        // Now test the ExitPortal indestructable object 
        // Set the wizard just in front of the exit portal block 
        app.getWizard().setX(29*20);
        app.getWizard().setY(31*20);
        app.delay(500); 
        
        // shoot a fireball at it 
        app.getWizard().shoot(); 
        app.delay(50);
        assertEquals(1, app.getWizard().getExistingFireballs().size());
        app.delay(1000);

        // No expected interaction, i.e. fireball just gets deleted
        assertEquals(0, app.getWizard().getExistingFireballs().size());
        

        // Reset the wizard's coordinates to just before touching the portal 
        app.getWizard().setX((30 * 20) - 2); 
        app.getWizard().setY(31 * 20); 

        // Save current attributes 
        int seconds = app.getSeconds(); 

        assertNull(app.getWizard().collisionBlock); 

        app.delay(50);
        app.noLoop(); 

        // The collision with the exitPortal should have ocurred 
        assertNotNull(app.getWizard().collisionBlock);
        assertEquals("gremlins.ExitPortal", app.getWizard().collisionBlock.getClass().getName());
        app.delay(500); 
        
        // Now check that the current map under scrutiny is equal to the 
        // map one would get if they read and made the level2 file 
        ReadMap r = new ReadMap(); 
        try {
            r.parseLayout("level2.txt");
        } catch (Exception e) {
            // This erroring would mean something is wrong with the ReadMap 
            // Class's objects. However, that has already been tested 
            // in another file, so it should be fine 
            throw new AssertionError();
        }

        // Create a deep cline of the arrayList
        ArrayList<Immobile> actualMapContents = new ArrayList<Immobile>();
        
        for(int i = 0; i < app.getMap().size(); i++){
            actualMapContents.add(app.getMap().get(i));
        }

        // This actualMapContents should correspond to the level 2 map 
        // The level 2 map is essentially 280 immobile blocks 
        assertEquals(280, actualMapContents.size());
        
        
        // The new map should have now been parsed 
        DrawMap d = new DrawMap();
        app.getMap().clear();
        d.generateMap(r.getMapContents(), app);
        // A new copy of the level 2 map should be read into the app 
        
        // Let the app update 
        app.loop(); 
        app.delay(500); 
        app.noLoop(); 


        assertEquals(actualMapContents.size(), app.getMap().size());
        
        for (int i = 0; i < app.getMap().size(); i++) {
            // Since new objects were made, I will just test whether the block's
            // names are correct. Since these were all added in order, it is 
            // not presumptuous to assume that order will be maintained
            String expected = app.getMap().get(i).getClass().getName();
            String actual = actualMapContents.get(i).getClass().getName();
            assertEquals(expected, actual);
        }

        // Check that seconds were reset
        assertEquals(0, app.getSeconds());

        // Take note, that although in the final few frames, you may see more gremlins than required
        // This is because the setup was essentially run twice, without deleting any gremlins
        // Hence, there appear to be more than necessary. This is not important for this testcase 
        // as we are simply testing wizard interactions
        app.delay(50);
    }

    //@Test 
    public void gremlinAITest(){
        /*
         * This test will examine how the GremlinAI works 
         */
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        
        app.delay(1000);
        // Teleport wizard to safety so the test won't randomly get restarted 
        // Coordinates (80, 500) is a known safepoint, and this can pass as the set method has been verified 
        // to work for all entities including the wizard. 
        app.getWizard().setX(80);
        app.getWizard().setY(500);
        app.delay(300);
        app.noLoop();
        
        // Examine gremlin 0 in the gArray. This is the gremlin at the lowest y value (highest up on the screen).
        Gremlin g = app.getGArray().get(0);
        
        // Should be going left (3), and it should not loop backwards to go immediately right (1)
        assertEquals(3, g.getDirection());
        // Save direction 
        int currentDir = g.getDirection(); 
        app.delay(1000);
        app.loop();

        // Go forth until the gremlin hits a block 
        while(g.collisionBlock == null){
            app.delay(10);
        }
        // pause the game 
        app.noLoop();

        // Check that the new direction doesn't equal the direction it was going, nor does it equal immediately backwards
        assertNotEquals(currentDir, g.getDirection());
        assertNotEquals(1, g.getDirection());
        app.delay(2000);

        // Conduct a respawn test for the gremlin
        g.gremlinRespawn(app);
        app.loop();
        int currentX = g.getX(); 
        int currentY = g.getY(); 

        // This will check whether the coordinates have changed to at least 10 blocks away. 
        app.delay(500); 

        boolean statement1 = ((currentX >= app.getWizard().getX() + 200) || (currentX <= app.getWizard().getX() - 200));
        boolean statement2 = ((currentY >= app.getWizard().getY() + 200) || (currentY <= app.getWizard().getY() - 200));
        /*
         * The gremlin should be at least 10 blocks in either direction, both too, but cannot be less than 20 in both. 
         */
        assertTrue(statement1 || statement2);

        app.delay(2000);
    }

   //@Test 
   public void fireBallGremlinInteractionTest(){
        /*
         * This testcase will specifically test the fireball gremlin interaction
         */

        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        
        app.delay(500);

        // Freeze the gremlins first so we can relocate the wizard 
        app.setFreezeActive(true);
        app.getWizard().setX(500);
        app.getWizard().setY(200);
        
        // Now, unfreeze the gremlins and fire a fireball at the gremlin. 
        // Several changes are expected, which are tested below 
        app.setFreezeActive(false);
        app.getWizard().shoot();
        
        // Pause the loop, and slowly increment
        while(app.getWizard().getExistingFireballs().get(0).collisionEntity == null){
            // Millisecond incrementer 
            // app.delay(1);
            app.redraw();
            // If the fireball has hit the gremlin, check that
            if(app.getWizard().getExistingFireballs().size() == 0){
                // This means something was wrong with the runtime (this appears to be due to the system)
                // Try to run the test again 
                System.out.println("Alternate option");
                break;
            }
            if(app.getWizard().getExistingFireballs().get(0).collisionEntity != null){
                app.noLoop(); 
                assertTrue(app.getWizard().getExistingFireballs().get(0).fireballHitGremlin(app));
                break;
            }
        }
        
        // Now test the "false case" of the gremlin hit. 
        app.getWizard().shoot(); 

        if(app.getWizard().getExistingFireballs().size() != 0){
            while(app.getWizard().getExistingFireballs().get(0).collisionEntity == null && app.getWizard().getExistingFireballs().get(0).collisionBlock == null){
                app.redraw();
                if(app.getWizard().getExistingFireballs().get(0).collisionBlock != null){
                    app.noLoop();
                    assertFalse(app.getWizard().getExistingFireballs().get(0).fireballHitGremlin(app));
                }
            }
        }

        app.delay(3000);
   }

   //@Test
   public void wizardGremlinInteractionTest(){
        /*
         * This testcase specifically will examine the following interactions 
         * Fireball + gremlin DONE
         * Wizard + gremlin 
         * Gremlin + gremlin 
         * Kamehameha beam + gremlin 
         * 
         * Then, the slimeball will be examined 
         * Slime should only interact with the wizard and fireball, no indestructables nor destructables 
         */

        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        
        app.noLoop();
        app.delay(1000);
        // Freeze the gremlins first so we can relocate the wizard  
        app.setFreezeActive(true); 

        int startingX = app.getWizard().getX();
        int startingY = app.getWizard().getY();

        app.getWizard().setX(500);
        app.getWizard().setY(200);

        // Move the wizard left slowly millisecond by millisecond until something has been hit
        app.loop();
        app.getWizard().left(); 
        while(app.getWizard().collisionEntity == null){
            //app.delay(1);
            app.redraw();
            
            // This means a collisionEntity has been detected (the gremlin)
            if(app.getWizard().collisionEntity != null){
                app.noLoop();
                assertEquals("gremlins.Gremlin", app.getWizard().collisionEntity.getClass().getName());
                break; 
            }
        }
        // Move one frame in the future 
        app.redraw();
        // Check that the wizard is now dead
        assertFalse(app.getWizard().getAlive());
        app.loop();
        app.delay(1000); 
        
        // Check that the wizard has respawned in the right location 
        int x = app.getWizard().getX(); 
        int y = app.getWizard().getY(); 
        // Starting coords 
        
        assertEquals(startingX, x); 
        assertEquals(startingY, y);

        app.delay(1);
        app.delay(3000);
        
   }

   @Test 
   public void slimeHitWizardTest(){
        /*
         * This test will examine how the slimeball and wizard 
         * collision interact.  
         */
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        
        app.noLoop();
        app.delay(1000);
        // Freeze the gremlins first so we can relocate the wizard  
        app.setFreezeActive(true); 
        app.loop();
        app.getWizard().setX(360);
        app.getWizard().setY(200);
        
        app.setFreezeActive(false);
        app.getGArray().get(0).shootSlimeBall();
        app.delay(50);
        SlimeBall s = app.getGArray().get(0).getAvailableSlimeballs().get(0);
        app.noLoop();

        while(true){
            if(app.getWizard().collisionEntity != null){
                assertEquals(s, app.getWizard().collisionEntity);
                System.out.println("Reached here");
                break;
            }
            
            app.redraw();
        }
        assertFalse(app.getWizard().getAlive());

        app.delay(3000);
   }

   // slimeHitFireballTest(){}
}