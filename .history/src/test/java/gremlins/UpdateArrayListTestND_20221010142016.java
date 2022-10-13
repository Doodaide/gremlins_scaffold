package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PImage;

import org.junit.jupiter.api.*;
/**
 * UpdateArrayListTestND
 */
@TestInstance(Lifecycle.PER_METHOD)
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

    @Test
    public void constructor(){
        assertNotNull(a);
    }

    @Test
    public void methodTest(){
        ArrayList<Entity> arr = new ArrayList<Entity>();
        PImage[] wSprites = new PImage[5];
        arr.add(new Wizard(0,0,2,wSprites));
        arr.add(new Wizard(20, 20, 2, wSprites));
        arr.add(new Wizard(40, 40, 2, wSprites));

        assertEquals(3, arr.size()); 

        assertNotNull(a.update(arr));
        assertEquals(arr, a.update(arr)); 

        a

    }
    
    
}