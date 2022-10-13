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
 * IndestructableTestND
 */
public class IndestructableTestND {

    private Indestructable i; 
    
    @BeforeAll
    public void instantiate(){
        i = new Indestructable(0, 0, null);
    }

    @AfterAll
    public void clear(){
        i = null; 
    }

    @Test
    
}