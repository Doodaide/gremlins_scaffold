package gremlins;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

@TestInstance(Lifecycle.PER_CLASS)
/**
 * UpdateArrayListTest
 */
public class UpdateArrayListTest {
    // Supposed to remove nulls from arrayLists
    private ArrayList<Entity> a;
    private UpdateArrayList<Entity> u;
    @BeforeAll
    public void object(){
        a = new ArrayList<Entity>();
        u = new UpdateArrayList<Entity>();
        a.add(new Gremlin(0, 0, null, null, null, null));
        a.add(new Destructable(0, 0, null, null));
        a.add(new Indestructable(0, 0, null));
    }

    @Test
    public void testRemoval(){
        assertEquals(4, a.size());

        a.set(0, null);
        
        u.update(a);

        assertEquals(3, a.size());
    }
}