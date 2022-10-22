package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import processing.core.PApplet;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import processing.core.PImage;

import org.junit.jupiter.api.*;

/**
 * WizardTestND: Test wizard attributes and methods that do not 
 * require the draw or tick method
 */

@TestInstance(Lifecycle.PER_METHOD) 
public class WizardTestND {
    private Wizard w; 
    private PImage[] s = new PImage[5];

    @BeforeEach
    public void instantiate(){
        for(int i = 0; i < s.length; i++){
            s[i] = null;
        }
        w = new Wizard(0, 0, 2, s);
    }

    @AfterEach
    public void clear(){
        w = null; 
        assertNull(w);
    }

    @Test 
    public void constructor(){
        assertNotNull(w);
        assertEquals(0, w.getX());
        assertEquals(0, w.getY());

        w.setX(20);
        w.setY(20);

        assertEquals(20,w.getX());
        assertEquals(20,w.getY());
    }

    @Test
    public void manaTest(){
        int manaValue = 10; 
        w.setManaCooldown(10);
        assertEquals(manaValue, w.getManaCoolDown());
    }

    @Test
    public void tpTest(){
        assertEquals(0, w.getTpCooldown());
        assertFalse(w.getTeleported()); 

        w.setTpCooldown(100);
        w.setTeleported(true);

        assertEquals(100, w.getTpCooldown());
        assertTrue(w.getTeleported());
        // !Check coordinates later 
    }

    @Test
    public void directionTest(){
        assertEquals(2, w.getSpeed());

        w.up(); 
        assertEquals(-2, w.getYVel());

        assertEquals(0, w.getDirection());

        assertEquals(0, w.yDir);

        //
        w.right();
        assertEquals(2, w.getXVel());

        assertEquals(1, w.getDirection());

        assertEquals(1, w.xDir);

        //
        w.down(); 
        assertEquals(2, w.getYVel());

        assertEquals(2, w.getDirection());

        assertEquals(2, w.yDir);

        // 
        w.left();
        assertEquals(-2, w.getXVel());

        assertEquals(3, w.getDirection());

        assertEquals(3, w.xDir);
    }

    @Test
    public void fireBallMethodTest(){
        ArrayList<Fireball> a = new ArrayList<Fireball>();
        // To test whether the fireball associated methods function as intended 
        assertNotNull(w);

        //Should have no fireballs 
        assertEquals(0, w.getExistingFireballs().size()); 

        // After shooting a fireball, the size of the array should increase by 1
        w.shoot(); 
        assertEquals(1, w.getExistingFireballs().size());
        for(int i = 0; i < 5; i++){
            w.shoot();
        }
        // So after shooting x times (without hitting anything), there should be x fireballs in the arrayList 
        assertEquals(6, w.getExistingFireballs().size());

        // Test the setters and getters for the arrayList 
        w.setExistingFireballs(a);
        assertEquals(a, w.getExistingFireballs());

        // Test if the fireball shot in a direction is really going in that direction 
        w.setDirection(1); 
        w.shoot(); 
        assertEquals(1, w.getExistingFireballs().get(0).getDirection());

        w.setDirection(0); 
        w.shoot(); 
        assertEquals(0, w.getExistingFireballs().get(1).getDirection());

        w.setDirection(2);
        w.shoot();
        assertEquals(2, w.getExistingFireballs().get(2).getDirection());

        w.setDirection(3);
        w.shoot();
        assertEquals(3, w.getExistingFireballs().get(3).getDirection());

    }
    
}