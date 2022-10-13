package gremlins;

import processing.core.PApplet;

import org.junit.jupiter.api.BeforeAll;
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
import java.util.ArrayList;
import java.util.Random;

/**
 * WizardTest
 */
public class WizardTest{
    @Test
    public void constructor(){
        PImage[] wizardSprites = new PImage[5];
        Wizard w = new Wizard(0, 0, 2, wizardSprites);
        assertNotNull(w);
        //assertEquals(wizardSprites, w.wizardSprites); // implement getters 
    }

    @Test
    public void directionTest(){
        PImage[] wizardSprites = new PImage[5];
        int speed = 2;
        Wizard w = new Wizard(0,0,speed,wizardSprites);
        w.up();

        // yVel 
        assertEquals(-speed, w.yVel);

        // Direction 
        assertEquals(0, w.getDirection());
        
        // yDir 
        assertEquals(0, w.yDir);


        w.right(); 
        // yVel 
        assertEquals(speed, w.xVel);

        // Direction 
        assertEquals(1, w.getDirection());
        
        // yDir 
        assertEquals(1, w.xDir);


        w.down();
        // yVel 
        assertEquals(speed, w.yVel);

        // Direction 
        assertEquals(2, w.getDirection());
        
        // yDir 
        assertEquals(2, w.yDir);


        w.left(); 
        // yVel 
        assertEquals(-speed, w.xVel);

        // Direction 
        assertEquals(3, w.getDirection());
        
        // yDir 
        assertEquals(3, w.xDir);


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
        PImage[] wizardSprites = new PImage[5];
        App app = new App();
        //app.settings();
        //app.setup();
        int speed = 2;
        Wizard w = new Wizard(0,0,speed,wizardSprites);
        // alive wizard check
        assertTrue(w.getAlive());
        double wizardCooldown = app.getWizardCooldown();
        int manaValue = 10;
        w.setManaCooldown(manaValue);
        assertEquals(manaValue, w.getManaCoolDown());
        //assertEquals(0.333, wizardCooldown);
        //assertTrue(w.getManaCoolDown() <= wizardCooldown);

        
        


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
    }
    
    @Test
    public void drawTest(){
        PImage[] wizardSprites = new PImage[5];
        try {
            this.wizardSprites = new PImage[5];
            this.wizardSprites[0] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard2.png").getPath(), StandardCharsets.UTF_8.toString())); // up
            this.wizardSprites[1] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard1.png").getPath(), StandardCharsets.UTF_8.toString()));  // right 
            wizardSprites[2] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard3.png").getPath(), StandardCharsets.UTF_8.toString())); // down
            wizardSprites[3] = loadImage(URLDecoder.decode(this.getClass().getResource("wizard0.png").getPath(), StandardCharsets.UTF_8.toString())); // left  
            wizardSprites[4] = loadImage(URLDecoder.decode(this.getClass().getResource("fireball.png").getPath(), StandardCharsets.UTF_8.toString())); // fireball
            // Load images during setup
            this.stonewall = loadImage(this.getClass().getResource("stonewall.png").getPath().replace("%20", " "));
            this.brickwall = loadImage(this.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
            this.brickwallDestruction = new PImage[4];
            this.brickwallDestruction[0] = loadImage(this.getClass().getResource("brickwall_destroyed0.png").getPath().replace("%20", " "));
            this.brickwallDestruction[1] = loadImage(this.getClass().getResource("brickwall_destroyed1.png").getPath().replace("%20", " "));
            this.brickwallDestruction[2] = loadImage(this.getClass().getResource("brickwall_destroyed2.png").getPath().replace("%20", " "));
            this.brickwallDestruction[3] = loadImage(this.getClass().getResource("brickwall_destroyed3.png").getPath().replace("%20", " "));
            this.gremlin = loadImage(URLDecoder.decode(this.getClass().getResource("gremlin.png").getPath(), StandardCharsets.UTF_8.toString()));
            this.slime = loadImage(this.getClass().getResource("slime.png").getPath().replace("%20", " "));
            this.portal = loadImage(this.getClass().getResource("portal.png").getPath().replace("%20", " "));
            this.powerUp = loadImage(this.getClass().getResource("Powerup_Freeze.png").getPath().replace("%20", " "));
            this.frozenG = loadImage(this.getClass().getResource("frozenGremlin.png").getPath().replace("%20", " "));
            this.frozenS = loadImage(this.getClass().getResource("frozenSlime.png").getPath().replace("%20", " "));
            this.tPIcon = loadImage(this.getClass().getResource("wizardTP.png").getPath().replace("%20", " "));
        } catch (UnsupportedEncodingException e) {
            System.err.println("Could not find Wizard");
            e.printStackTrace();
        }
        App app = new App();
        app.settings();
        app.setup();
        int speed = 2;
        Wizard w = new Wizard(0,0,speed,wizardSprites);
        w.draw(app);

        assertEquals(0,w.getX());
        assertEquals(0, w.getY());



    }

}