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

    // Instantiate an instance of the updateArrayList class 
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
        // Check that the object isn't null 
        assertNotNull(a);
    }

    @Test
    public void methodTest(){
        // This test will examine how the updater will remove null entries 

        // Make an arrayList of entities (I will fill it with wizards)
        // The wizard constructor has already been tested 
        ArrayList<Entity> arr = new ArrayList<Entity>();

        // Need a non-null arrayList for wizardSprites 
        PImage[] wSprites = new PImage[5];

        // add in 3 instances of different wizards into the arrayList 
        arr.add(new Wizard(0,0,2,wSprites));
        arr.add(new Wizard(20, 20, 2, wSprites));
        arr.add(new Wizard(40, 40, 2, wSprites));

        // Check the size is 3 
        assertEquals(3, arr.size()); 

        // check that if the arrayList is run through the updater without any nulls, 
        // it does not get nulled, nor does it change anything 
        assertNotNull(a.update(arr));
        assertEquals(arr, a.update(arr)); 

        // set one value of the arrayList to null 
        arr.set(0, null);

        // Check that the arrayList has been "updated" through its change in size 
        assertNotEquals(arr, a.update(arr));

        assertEquals(2, a.update(arr).size());
    }
    
    
}