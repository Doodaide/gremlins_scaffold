package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;
import processing.core.PImage;
import processing.data.JSONObject;
import processing.data.JSONArray;
import processing.event.KeyEvent;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * WizardTest
 */
public class WizardTest{
    @Test
    public void constructor(){
        PImage[] wizardSprites = new PImage[5];
        assertNotNull(new Wizard(0, 0, 2, wizardSprites));
    }

    @Test
    public void directionTest(){
        PImage[] wizardSprites = new PImage[5];
        Wizard w = new Wizard(0,0,2,wizardSprites);
        // Up
        w.up();
        // Coordinates 
        assertEquals(0, );

        // Direction 

        // xDir 

        // yDir 
    }
}