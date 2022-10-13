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
 * SlimeBallTestND
 */
public class SlimeBallTestND {

    private SlimeBall s; 

    @BeforeEach
    public void instantiate(){
        s = new SlimeBall(0, 0, null, null); 
    }

    @AfterEach
    public void clear(){
        s = null; 
        assertNull(s);
    }

    @Test
    public void constructor(){
        assertNotNull(s);
    }

    @Test
    public void methodTest)()
}