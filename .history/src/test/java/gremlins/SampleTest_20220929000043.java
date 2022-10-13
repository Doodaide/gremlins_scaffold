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

public class SampleTest {

    /*
     * assertTrue(boolean)
     * 
     * assertFalse(boolean)
     * 
     * assertEquals(expected, actual)
     * 
     * assertNull(object)
     * 
     * assertSame(object1, object2)
     */

    @Test
    public void test0X_testWizardCreation(){
        PImage[] wizardSprites = new PImage[4];
        Wizard w = new Wizard(0, 0, 2, null,null, null, null,  wizardSprites[4]);
        assertNotNull(w);
    }
}
