package gremlins;

import processing.core.PApplet;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    private App app;
    //private Gremlin g; 
    //private Indestructable i;

    @BeforeEach
    public void object(){
        //app.loop(); // loops 
        app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();

    }

    @Test
    public void constructor(){
        assertNotNull(app.getWizard());
        app.getWizard().setX(20);
       app.getWizard().setY(20);
        assertEquals(20,app.getWizard().getX());
        assertEquals(20,app.getWizard().getY());
    }

    @Test
    public void directionTest(){
        int speed = 2;

        assertEquals(2,app.getWizard().getSpeed());

       app.getWizard().up();

        // yVel 
        assertEquals(-speed,app.getWizard().yVel);

        // Direction 
        assertEquals(0,app.getWizard().getDirection());
        
        // yDir 
        assertEquals(0,app.getWizard().yDir);

        assertEquals(app.wizardSprites[0],app.getWizard().getCurrentImg());
       app.getWizard().stop();
        assertEquals(0,app.getWizard().getXVel());
        assertEquals(0,app.getWizard().getYVel());

       app.getWizard().right(); 
        // yVel 
        assertEquals(speed,app.getWizard().xVel);

        // Direction 
        assertEquals(1,app.getWizard().getDirection());
        
        // yDir 
        assertEquals(1,app.getWizard().xDir);

        assertEquals(app.wizardSprites[1],app.getWizard().getCurrentImg());
       app.getWizard().stop();
        assertEquals(0,app.getWizard().getXVel());
        assertEquals(0,app.getWizard().getYVel());

       app.getWizard().down();
        // yVel 
        assertEquals(speed,app.getWizard().yVel);

        // Direction 
        assertEquals(2,app.getWizard().getDirection());
        
        // yDir 
        assertEquals(2,app.getWizard().yDir);

        assertEquals(app.wizardSprites[2],app.getWizard().getCurrentImg());
       app.getWizard().stop();
        assertEquals(0,app.getWizard().getXVel());
        assertEquals(0,app.getWizard().getYVel());

       app.getWizard().left(); 
        // yVel 
        assertEquals(-speed,app.getWizard().xVel);

        // Direction 
        assertEquals(3,app.getWizard().getDirection());
        
        // yDir 
        assertEquals(3,app.getWizard().xDir);

        assertEquals(app.wizardSprites[3],app.getWizard().getCurrentImg());
       app.getWizard().stop();
        assertEquals(0,app.getWizard().getXVel());
        assertEquals(0,app.getWizard().getYVel());

    }

    @Test
    public void tickTest(){
        
        //app.getWizard()izard starting position, (x,y): (40, 20) 
        
        assertEquals(40,app.getWizard().getX());
        assertEquals(20,app.getWizard().getY());


        app.getWizard().tick(app);
        assertEquals(app.getWizard(),app.getWizard());
        assertNull(app.getWizard().collisionEntity);
        assertNull(app.getWizard().collisionBlock)
        //assertNotNull(app.getWizard().collisionBlock);
        //assertEquals("gremlins.Indestructable",app.getWizard().collisionBlock.getClass().getName());
        
        // bumping into a block on the map, no movement 
        app.getWizard().left();
        app.loop();
        assertEquals(40,app.getWizard().getX());
        assertEquals(20,app.getWizard().getY());
        app.getWizard().stop();



        app.getWizard().right();
        app.loop();
        assertEquals(1,app.getWizard().getDirection());
        app.loop();
        //assertNull(w.collisionBlock);
        assertEquals(40,app.getWizard().getX());
        
        //w.right(); 
        //w.tick(app);
        //assertEquals(40,app.getWizard().getX());
        //assertEquals(20,app.getWizard().getY());
    
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
        assertEquals(0,app.getWizard().getTpCooldown());
        assertFalse(app.getWizard().getTeleported());
        
       app.getWizard().setTpCooldown(100);
       app.getWizard().setTeleported(true);

        assertEquals(100,app.getWizard().getTpCooldown());
        assertTrue(app.getWizard().getTeleported());

        // ! Check again 
        //w.tp(app);

    }
    
    @Test
    public void drawTest(){
        assertEquals(app.getWizard().getDirection(),app.getWizard().draw(app));
        assertEquals(40,app.getWizard().getX());
        assertEquals(20,app.getWizard().getY());
        // Img current 

    }

}