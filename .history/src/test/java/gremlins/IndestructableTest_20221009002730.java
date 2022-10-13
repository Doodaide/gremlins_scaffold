package gremlins;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(Lifecycle.PER_CLASS)
public class IndestructableTest{

    private Indestructable i;
    private App app;

    @BeforeAll
    public void object(){
        i = new Indestructable(0, 0, null);
    }

    @Test
    public void constructor(){
        
    }

}

