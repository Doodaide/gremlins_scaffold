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

    @Test
    public void test0X_testWizardCreation(){
        PImage[] wizardSprites = new PImage[4];
        
        Wizard w = new Wizard(0, 0, 2, wizardSprites[0],  wizardSprites[1],  wizardSprites[2],  wizardSprites[3],  wizardSprites[4]);
    }
}
