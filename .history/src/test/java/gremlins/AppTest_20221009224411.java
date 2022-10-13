package gremlins;

import processing.core.PApplet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.*;
/**
 * AppTest
 */

 @TestInstance(Lifecycle.PER_CLASS)
public class AppTest {
    private App app;
    @BeforeAll
    public void object(){
        app = new App();
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup();
    }

    @Test
    public void testSetup(){

    }
    
}