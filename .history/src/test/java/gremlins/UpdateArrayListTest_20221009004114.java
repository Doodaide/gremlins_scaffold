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
    @BeforeAll
    public void object(){
        ArrayList<Entity> a = new ArrayList<Entity>();
        a.add(new Wizard(0, 0, 0, null));

    }
}