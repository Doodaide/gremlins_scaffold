package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PImage;

import org.junit.jupiter.api.*;
/**
 * UpdateArrayListTestND
 */
@TestInstance(Lifecycle.PER_CLASS)
public class UpdateArrayListTestND {

    private UpdateArrayList<Entity> a; 

    @BeforeEach
    public void instantiate(){
        a = new UpdateArrayList<Entity>(); 
    }

    @AfterEach
    public void clear(){
        a = null; 
        assertNull(a);
    }
    
    
}