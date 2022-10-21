package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PImage;

import org.junit.jupiter.api.*;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * RespawnGeneratorTest
 */
public class RespawnGeneratorTest {

    private RespawnGenerator r;
    private PImage[] s = new PImage[5];
    // I will use a wizard entity to demonstrate its capabilities
    private Wizard w;
    @BeforeEach
    public void instantiate(){ 

        w = new Wizard(40, 40, 2, s);
        r = new RespawnGenerator(w);
    }

    @Test 
    public void constructor(){
        // Test to see that the constructor isn't null 
        assertNotNull(r); 

        // Test the various attributes 
    }


}