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
    public void constructor(){
        assertNotNull(app.getWizard());
        app.getWizard().setX(20);
        app.getWizard().setY(20);
        assertEquals(20,app.getWizard().getX());
        assertEquals(20,app.getWizard().getY());
    }

    

}