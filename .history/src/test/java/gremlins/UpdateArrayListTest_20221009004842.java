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
    private Gremlin g1, g2, g3;
    @BeforeAll
    public void object(){
        a = new ArrayList<Entity>();
        u = new UpdateArrayList<Entity>();
        g1 = new Gremlin(0, 0, null, null, null, null);
        g2 = new Gremlin(0, 0, null, null, null, null); 
        g3 = new Gremlin(0, 0, null, null, null, null);
        a.add(g1);
        a.add(g2);
        a.add(g3);
       
    }

    @Test
    public void testRemoval(){
        assertEquals(3, a.size());
        assertEquals(g1, a.get(0));
        a.set(a.indexOf(g1), null);
        g1 = null;
        u.update(a);

        assertEquals();
    }
}