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
public class WizardTest extends App{
    private PImage[] wizardSprites = new PImage[5];
    
    @Test
    public void constructor(){
        assertNotNull(new Wizard(0, 0, 2, ));
    }

    @Test
    public void upTest(){
        ;
    }
}