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
        //app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        
        w = new Wizard(0, 0, 2, app.wizardSprites);
        g = new Gremlin(0, 0, app.gremlin, app.slime, app.frozenG, app.frozenS);
        i = new Indestructable(40, 40, app.stonewall);
        w.setX(20);
        w.setY(20);

        assertEquals(20, w.getX());
        assertEquals(20, w.getY());

    }

    @Test
    public void constructor(){
        assertNotNull(w);
        assertNotNull(g); 
        assertNotNull(i);
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


        w.right(); 
        // yVel 
        assertEquals(speed, w.xVel);

        // Direction 
        assertEquals(1, w.getDirection());
        
        // yDir 
        assertEquals(1, w.xDir);

        assertEquals(app.wizardSprites[1], w.getCurrentImg());


        w.down();
        // yVel 
        assertEquals(speed, w.yVel);

        // Direction 
        assertEquals(2, w.getDirection());
        
        // yDir 
        assertEquals(2, w.yDir);

        assertEquals(app.wizardSprites[2], w.getCurrentImg());


        w.left(); 
        // yVel 
        assertEquals(-speed, w.xVel);

        // Direction 
        assertEquals(3, w.getDirection());
        
        // yDir 
        assertEquals(3, w.xDir);

        assertEquals(app.wizardSprites[3], w.getCurrentImg());


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

        // as Wizard cooldown is a float, it is difficult to test equality.
        // It just has to be less within a reasonable range (as it is a third) 
        assertTrue(app.getWizardCooldown() <= 20); 
        assertTrue(w.getManaCoolDown() <= wizardCooldown);

        // No collisions
        assertNull(w.collisionEntity);
        assertNull(w.collisionBlock);
        
        assertTrue(w.sideAV.get(0));
        assertTrue(w.sideAV.get(1));
        assertTrue(w.sideAV.get(2));
        assertTrue(w.sideAV.get(3));
        
        // Test stop diagonal motion: 
        w.up();
        w.setDirection(3);
        w.tick(app);
        assertEquals(0, w.getXVel());
        assertEquals(0, w.getYVel());
        
        w.up();
        w.setDirection(1);
        w.tick(app);
        assertEquals(0, w.getXVel());
        assertEquals(0, w.getYVel());

        
        w.down();
        w.tick(app);
        assertEquals(0, w.getXVel());
        assertEquals(2, w.getYVel());
w.setX(0);
        w.setY(0);
        assertEquals(0, w.getX());
        assertEquals(2, w.getY());

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

        // ! Check again 
        //w.tp(app);

    }
    
    @Test
    public void drawTest(){
        assertEquals(w.getDirection(),w.draw(app));
        assertEquals(20, w.getX());
        assertEquals(20, w.getY());

    }
}