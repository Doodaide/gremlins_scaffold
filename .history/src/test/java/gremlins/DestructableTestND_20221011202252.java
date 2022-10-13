package gremlins;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import processing.core.PApplet;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
/**
 * DestructableTestND
 */
public class DestructableTestND {

    private Destructable d; 
    private App app; 
    private PImage[] t

    @BeforeAll
    public void object(){
        app = new App();
        app.loop(); 
        PApplet.runSketch(new String[] {"App"}, app);
        app.setup(); 
        d = new Destructable(0, 0, null, null)
    }
    
}