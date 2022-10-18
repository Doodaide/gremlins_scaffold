package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import processing.core.PApplet;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PImage;

import org.junit.jupiter.api.*;
import processing.event.KeyEvent;

/**
 * WizardTestND: Test wizard attributes and methods that do not 
 * require the draw or tick method
 */

@TestInstance(Lifecycle.PER_METHOD) 
public class WizardTestND {
    private Wizard w; 
    private PImage[] s = new PImage[5];

    @BeforeEach
    public void instantiate(){
        for(int i = 0; i < s.length; i++){
            s[i] = null;
        }
        w = new Wizard(0, 0, 2, s);
    }

    @AfterEach
    public void clear(){
        w = null; 
        assertNull(w);
    }

    @Test 
    public void constructor(){
        assertNotNull(w);
        assertEquals(0, w.getX());
        assertEquals(0, w.getY());

        w.setX(20);
        w.setY(20);

        assertEquals(20,w.getX());
        assertEquals(20,w.getY());
    }

    @Test
    public void manaTest(){
        int manaValue = 10; 
        w.setManaCooldown(10);
        assertEquals(manaValue, w.getManaCoolDown());
    }

    @Test
    public void tpTest(){
        assertEquals(0, w.getTpCooldown());
        assertFalse(w.getTeleported()); 

        w.setTpCooldown(100);
        w.setTeleported(true);

        assertEquals(100, w.getTpCooldown());
        assertTrue(w.getTeleported());
        // !Check coordinates later 
    }

    @Test
    public void directionTest(){
        assertEquals(2, w.getSpeed());

        w.up(); 
        assertEquals(-2, w.getYVel());

        assertEquals(0, w.getDirection());

        assertEquals(0, w.yDir);

        //
        w.right();
        assertEquals(2, w.getXVel());

        assertEquals(1, w.getDirection());

        assertEquals(1, w.xDir);

        //
        w.down(); 
        assertEquals(2, w.getYVel());

        assertEquals(2, w.getDirection());

        assertEquals(2, w.yDir);

        // 
        w.left();
        assertEquals(-2, w.getXVel());

        assertEquals(3, w.getDirection());

        assertEquals(3, w.xDir);
    }

    @Test
    public void fireBallMethodTest(){
        ArrayList<Fireball> a = new ArrayList<Fireball>();
        // To test whether the fireball associated methods function as intended 
        assertNotNull(w);

        //Should have no fireballs 
        assertEquals(0, w.getExistingFireballs().size()); 

        // After shooting a fireball, the size of the array should increase by 1
        w.shoot(); 
        assertEquals(1, w.getExistingFireballs().size());
        for(int i = 0; i < 5; i++){
            w.shoot();
        }
        // So after shooting x times (without hitting anything), there should be x fireballs in the arrayList 
        assertEquals(6, w.getExistingFireballs().size());

        // Test the setters and getters for the arrayList 
        w.setExistingFireballs(a);
        assertEquals(a, w.getExistingFireballs());

        // Test if the fireball shot in a direction is really going in that direction 
        w.setDirection(1); 
        w.shoot(); 
        assertEquals(1, w.getExistingFireballs().get(0).getDirection());

        w.setDirection(0); 
        w.shoot(); 
        assertEquals(0, w.getExistingFireballs().get(1).getDirection());

        w.setDirection(2);
        w.shoot();
        assertEquals(2, w.getExistingFireballs().get(2).getDirection());

        w.setDirection(3);
        w.shoot();
        assertEquals(3, w.getExistingFireballs().get(3).getDirection());

    }

    @Test
    public void staticImageTest(){
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
        assertEquals(app.wizardSprites[0], app.getWizard().getCurrentImg());
        app.delay(500);

        assertNull(app.getWizard().collisionEntity);
        assertNotNull(app.getWizard().collisionBlock);
        assertEquals("gremlins.Indestructable", app.getWizard().collisionBlock.getClass().getName());
        app.delay(500);

        app.getWizard().down();
        app.delay(500);
        assertNull(app.getWizard().collisionEntity);
        assertNotNull(app.getWizard().collisionBlock);
        assertEquals("gremlins.Destructable", app.getWizard().collisionBlock.getClass().getName());
        app.delay(1000);
    }

    @Test 
    public void movementImageTest(){
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

        app.getWizard().up();
        app.getWizard().right();
        app.delay(500);
        assertEquals(beforeX, app.getWizard().getX());
        assertEquals(beforeY, app.getWizard().getY());

        //assertEquals(2, app.getWizard().getXVel());
        //assertEquals(60, app.getWizard().getX());
    }
        
    @Test 
    public void ImmobileInteractionTest(){
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
        app.getWizard().shoot();
        //app.delay(10); 
        assertEquals(1, app.getWizard().getExistingFireballs().size());
        app.delay(1000);

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

        // Increment 17 milliseconds (just over 1 frame) in the future
        app.delay(16);
        assertEquals("gremlins.Destructable",f.collisionBlock.getClass().getName());
        app.delay(500); 

    }

    // Gremlin interaction test 

    // Slimeball interaction test 

    // FreezePotion interaction test

    // 

}