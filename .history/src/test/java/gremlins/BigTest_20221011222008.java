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
 * BigTest
 */
@TestInstance(Lifecycle.PER_CLASS)
public class BigTest {

    private App app; 

    @BeforeAll
    public void object(){
        app = new App(); 
        PApplet.runSketch(new String[] { "App" }, app);
        app.settings(); 
        app.setup();
    }

    @Test
    public void test01(){
        // Variable tests
        
        assertEquals(720, App.WIDTH);
        assertEquals(720, App.HEIGHT); 

        // Setup 
        assertEquals(20, App.SPRITESIZE);
        assertEquals(60, App.BOTTOMBAR);
        assertNotNull(App.randomGenerator);
        assertEquals(60, App.FPS);
        // ! Ask about making objects public or private? 
        
        // ! Do I need to check the json is loaded in? If So how? 




        
        assertNotNull(app.getGArray());
        app.loop();
        app.delay(3000);
    }

    @Test
    public void wizardTest(){
        assertNotNull(app.getWizard());
        // Check wizard starting location 
        x = 60, 
        y = 20
        assertEquals(0, 0);
    }
}