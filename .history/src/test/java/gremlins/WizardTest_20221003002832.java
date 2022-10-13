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
public class WizardTest extends PApplet{
    private PImage[] wizardSprites = new PImage[5];
    @Test
    public void constructor(){
        this.wizardSprites[0] = loadImage(this.getClass().getResource("wizard0.png").getPath().replace("%20", " ")); // up
        this.wizardSprites[1] = loadImage(this.getClass().getResource("wizard1.png").getPath().replace("%20", " "));  // right 
        this.wizardSprites[2] = loadImage(this.getClass().getResource("wizard2.png").getPath().replace("%20", " ")); // down
        this.wizardSprites[3] = loadImage(this.getClass().getResource("wizard3.png").getPath().replace("%20", " ")); // left  
        this.wizardSprites[4] = loadImage(URLDecoder.decode(this.getClass().getResource("fireball.png").getPath(), StandardCharsets.UTF_8.toString())); // fireball
        assertNotNull(new Wizard(0, 0, 2, wizardSprites));
    }

    @Test
    public void upTest(){
        ;
    }
}