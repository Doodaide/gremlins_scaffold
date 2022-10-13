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

    @BeforeAll
    public void object(){
        ArrayList<Entity> a = new ArrayList<Entity>();
    }
}