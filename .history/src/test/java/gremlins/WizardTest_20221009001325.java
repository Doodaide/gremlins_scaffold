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
public class WizardTest extends PApplet{
    
    private Wizard w;
    private App app;
    PImage[] wizardSprites = new PImage[5];

    @BeforeAll
    public void object(){
        app = new App();
            this.wizardSprites[0] =null;
            this.wizardSprites[1] = null;
            this.wizardSprites[2] = null;
            this.wizardSprites[3] = null;
            this.wizardSprites[4] = null;
        
        w = new Wizard(0, 0, 2, wizardSprites);

    }

    @Test
    public void constructor(){
        assertNotNull(w);
        //assertEquals(wizardSprites, w.wizardSprites); // implement getters 
    }

    @Test
    public void directionTest(){
        PImage[] wizardSprites = new PImage[5];
        int speed = 2;
        Wizard w = new Wizard(0,0,speed,wizardSprites);

        assertEquals(2, w.getSpeed());

        w.up();

        // yVel 
        assertEquals(-speed, w.yVel);

        // Direction 
        assertEquals(0, w.getDirection());
        
        // yDir 
        assertEquals(0, w.yDir);


        w.right(); 
        // yVel 
        assertEquals(speed, w.xVel);

        // Direction 
        assertEquals(1, w.getDirection());
        
        // yDir 
        assertEquals(1, w.xDir);


        w.down();
        // yVel 
        assertEquals(speed, w.yVel);

        // Direction 
        assertEquals(2, w.getDirection());
        
        // yDir 
        assertEquals(2, w.yDir);


        w.left(); 
        // yVel 
        assertEquals(-speed, w.xVel);

        // Direction 
        assertEquals(3, w.getDirection());
        
        // yDir 
        assertEquals(3, w.xDir);


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
    public void tickTest(){
        // alive wizard check
        assertTrue(w.getAlive());
        double wizardCooldown = app.getWizardCooldown();
        int manaValue = 10;
        w.setManaCooldown(manaValue);
        assertEquals(manaValue, w.getManaCoolDown());
        //assertEquals(0.333, wizardCooldown);
        //assertTrue(w.getManaCoolDown() <= wizardCooldown);

        // No collisions
        assertNull(w.collisionEntity);
        assertNull(w.collisionBlock);
        
        assertTrue(w.sideAV.get(0));
        assertTrue(w.sideAV.get(1));
        assertTrue(w.sideAV.get(2));
        assertTrue(w.sideAV.get(3));
        
        // Test stop diagonal motion: 
        w.up()


        // dead wizard check
        w.setAlive(false);
        assertFalse(w.getAlive());
        assertEquals(10, w.tick(app));

    }

    @Test
    public void tpTest(){
        PImage[] wizardSprites = new PImage[5];
        App app = new App();
        int speed = 2;
        Wizard w = new Wizard(0,0,speed,wizardSprites);

        assertEquals(0, w.getTpCooldown());
        assertFalse(w.getTeleported());
        
        w.setTpCooldown(100);
        w.setTeleported(true);

        assertEquals(100, w.getTpCooldown());
        assertTrue(w.getTeleported());
    }
    
    @Test
    public void drawTest(){
        assertEquals(w.getDirection(),w.draw(app));
        assertEquals(0, w.getX());
        assertEquals(0, w.getY());

    }
}