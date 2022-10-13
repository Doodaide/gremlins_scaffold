package gremlins;

import processing.core.PApplet;

import org.junit.jupiter.api.AfterEach;
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
@TestInstance(Lifecycle.PER_METHOD)
public class WizardTest{

    private App app;
    //private Gremlin g; 
    //private Indestructable i;

    @BeforeEach
    public void object(){
        app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        app.delay(1000); 
    }

    @AfterEach
    public void clear(){
        app = null;
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

   

}