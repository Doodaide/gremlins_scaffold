package gremlins;

import processing.core.PApplet;

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
    public void test01(){
        // ! Wizard static test 1

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
    public void test02(){


        
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



}