package gremlins;

import processing.core.PApplet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

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
 * WizardTest
 */
@TestInstance(Lifecycle.PER_CLASS)
public class WizardTest{
    
    private Wizard w;
    private App app;
    private Gremlin g; 
    private Indestructable i;

    @BeforeAll
    public void object(){
        app = new App();
        //app.loop(); // loops 
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        
        w = app.getWizard();
        w.setX(20);
        w.setY(20);

        assertEquals(20, w.getX());
        assertEquals(20, w.getY());

    }

    @Test
    public void constructor(){
        assertNotNull(w);
    }

    @Test
    public void directionTest(){
        int speed = 2;

        assertEquals(2, w.getSpeed());

        w.up();

        // yVel 
        assertEquals(-speed, w.yVel);

        // Direction 
        assertEquals(0, w.getDirection());
        
        // yDir 
        assertEquals(0, w.yDir);

        assertEquals(app.wizardSprites[0], w.getCurrentImg());
        w.stop();
        assertEquals(0, w.getXVel());
        assertEquals(0, w.getYVel());

        w.right(); 
        // yVel 
        assertEquals(speed, w.xVel);

        // Direction 
        assertEquals(1, w.getDirection());
        
        // yDir 
        assertEquals(1, w.xDir);

        assertEquals(app.wizardSprites[1], w.getCurrentImg());
        w.stop();
        assertEquals(0, w.getXVel());
        assertEquals(0, w.getYVel());

        w.down();
        // yVel 
        assertEquals(speed, w.yVel);

        // Direction 
        assertEquals(2, w.getDirection());
        
        // yDir 
        assertEquals(2, w.yDir);

        assertEquals(app.wizardSprites[2], w.getCurrentImg());
        w.stop();
        assertEquals(0, w.getXVel());
        assertEquals(0, w.getYVel());

        w.left(); 
        // yVel 
        assertEquals(-speed, w.xVel);

        // Direction 
        assertEquals(3, w.getDirection());
        
        // yDir 
        assertEquals(3, w.xDir);

        assertEquals(app.wizardSprites[3], w.getCurrentImg());
        w.stop();
        assertEquals(0, w.getXVel());
        assertEquals(0, w.getYVel());

    }

    @Test
    public void tickTest(){
        w.tick(app);
        assertNull(w.collisionEntity);
        assertNotNull(w.collisionBlock);
        assertEquals("gremlins.Indestructable", w.collisionBlock.getClass().getName());
        
        // bumping into a block on the map, no movement 
        w.left();
        app.loop();
        assertEquals(20, w.getX());
        assertEquals(20, w.getY());
        w.stop();



        w.right();
        app.loop();
        assertEquals(1, w.getDirection());
        app.loop();
        assertNull(w.collisionBlock);
        //assertEquals(20, w.getX());
        
        //w.right(); 
        //w.tick(app);
        //assertEquals(40, w.getX());
        //assertEquals(20, w.getY());
    
    }

    @Test 
    public void manaTest(){
        PImage[] wizardSprites = new PImage[5];
        App app = new App();
        int speed = 2;
        Wizard w = new Wizard(0,0,speed,wizardSprites);
        int manaValue = 10;
        w.setManaCooldown(manaValue);
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

        // ! Check again 
        //w.tp(app);

    }
    
    @Test
    public void drawTest(){
        assertEquals(w.getDirection(),w.draw(app));
        assertEquals(20, w.getX());
        assertEquals(20, w.getY());
        // Img current 

    }

}