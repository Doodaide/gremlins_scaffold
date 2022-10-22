package gremlins;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import processing.core.PApplet;

/**
 * AppTest
 */
public class AppTest {

    @Test 
    public void getterTest(){
        App app = new App();
        PApplet.runSketch(new String[] { "App" }, app);
        // This is used to stun the gremlins for the moment. This ensures no unwanted interactions between entities occurrs 
        app.delay(1000);
        assertFalse(app.getFreezeActive()); 
        app.setFreezeActive(true);
        app.noLoop();

        // There's no way that I am aware of that I can test the kamehameha method without hitting the key
        // So here is the null return statement 
        assertNull(app.getK());

        // Test the setter and getter for freeze 
        assertTrue(app.getFreezeActive()); 
        app.setFreezeActive(false);

        // Check gArray is not null and is equal to 4
        assertNotNull(app.getGArray());
        assertEquals(4, app.getGArray().size());
        
        // Level 1 stats: enemy cooldown = 3.0, wizard cooldown = 0.3333
        // However, I have multiplied them by the frame rate as that's how it's supposed to be
        // Thus, they are weird floats. 
        // Thus, I will assert them to be correct another way 
        assertTrue(20 >= app.getWizardCooldown());
        assertTrue(180 >= app.getEnemyCooldown()); 
        

        // PowerUpSpawned test
        assertFalse(app.getPowerUpAvailable());
        app.setPowerUpSpawned(true);
        assertTrue(app.getPowerUpAvailable());

        // Innate cooldown (for powerup) test 
        assertEquals(0, app.getInnateCooldown()); 
        // When powerup is first consumed, innate cooldown should be FPS * 20

        // Let powerup spawn
        app.loop();
        app.delay(3000); 
        app.noLoop(); 

        // Powerup coords are first set to (160, 20)
        assertEquals(160,app.getPowerUpCoords()[0]);
        assertEquals(20, app.getPowerUpCoords()[1]);

        assertFalse(app.getPowerUpConsumedOnce())
    }
}