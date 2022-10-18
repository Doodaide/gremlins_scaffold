package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import processing.core.PApplet;
import static org.junit.jupiter.api.Assertions.*;

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
    public void staticImageTest(){
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        app.delay(1000);

        // checks the current image is not null
        assertNotNull(app.getWizard().getCurrentImg());

        // checks the current image is facing left (default generation )
        assertEquals(app.wizardSprites[3], app.getWizard().getCurrentImg()); 

        // assesses current coordinates to be where expected on the map
        assertEquals(40, app.getWizard().getX());
        assertEquals(20, app.getWizard().getY());

        // Direction of wizard changed (but collided into a block)
        app.getWizard().up();
        assertEquals(app.wizardSprites[0], app.getWizard().getCurrentImg());
        assertNull(app.getWizard().collisionEntity);
        assertEquals("gremlins.",app.getWizard().collisionBlock.getClass().getName());
        app.delay(2000);
        app.exit();
        app = null; 
    }

    @Test 
    public void movementImageTest(){
        // this test will make a new app, and then run the wizard through a set of programmed behaviours
        // The interactions of the wizard, in conjunction with the different objects, and the app itself will be tested
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        // Generate map
        app.delay(1000);
        assertNotNull(app.getWizard()); // non null wizard 
        assertEquals(3, app.getWizard().draw(app)); // assert that the current wizard (not moving) is not drawing either 
        app.delay(1000);
        // Move the wizard right 
        app.getWizard().right();
        app.delay(1000);
        // A collision with a destructable block is expected 
        assertEquals("gremlins.Destructable",app.getWizard().collisionBlock.getClass().getName());
        assertNull(app.getWizard().collisionEntity);
        app.getWizard().stop();
        app.getWizard().down();
        app.delay(1000);
        app.getWizard().shoot();
        app.getWizard().tp(app);
        app.delay(1000);
        //assertEquals(1, app.getWizard().getDirection());
        //assertEquals(2, app.getWizard().getXVel());
        //assertEquals(60, app.getWizard().getX());
    }
    
}