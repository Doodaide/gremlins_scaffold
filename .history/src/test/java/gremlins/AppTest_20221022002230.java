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

        // There's no way that I am aware of that I can test the kamehameha method without hitting the key
        // So here is the null return statement 
        assertNull(app.getK());

        // Test the setter and getter for freeze 
        app.delay(3000);
        assertTrue(app.getFreezeActive()); 
        app.setFreezeActive(false);

        // Check gArray is not null and is equal to 4
        assertNotNull(app.getGArray());
        assertEquals(4, app.getGArray());
        "wizard_cooldown": 0.3333,
        "enemy_cooldown": 3.0
        // Level 1 stats: enemy cooldown = , wizard cooldown = 0.3333
    }
}