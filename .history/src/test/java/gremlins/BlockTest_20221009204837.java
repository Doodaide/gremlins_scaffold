package gremlins;
import processing.core.PApplet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PImage;

/**
 * BlockTest
 */

@TestInstance(Lifecycle.PER_CLASS) 
public class BlockTest {

    private Destructable d; 
    private ExitPortal e;
    private Indestructable i;
    private App app;
    private PImage defaultTexture; 
    private PImage decayTextures; 

    @BeforeAll 
    public void object(){
        app = new App();
        app.loop();
        PApplet.runSketch(new String[] { "App" }, app);
        app.setup();
        
        this.defaultTexture = app.loadImage(app.getClass().getResource("brickwall.png").getPath().replace("%20", " "));
        d = new Destructable(0, 0, null, null);
        e = new ExitPortal(0, 0, null);
        i = new Indestructable(0, 0, null);
    }
    
    @Test
    public void constructor(){
        assertNotNull(d);
        assertNotNull(e);
        assertNotNull(i);
    }

    @Test
    public void destructableTest(){
        assertTrue(d.getIntact());
        
        assertTrue(d.getViable());

        d.setViable(false);

        assertFalse(d.getViable());
        //d.update();
        

        assertEquals(1, d.getCurrentState());

        d.update();
        d.update();
        d.update();
        assertEquals(0, d.getCurrentState());

        d.destroy();

        assertFalse(d.getIntact());

    }

}