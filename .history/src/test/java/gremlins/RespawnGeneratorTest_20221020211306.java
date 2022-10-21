package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.junit.jupiter.api.*;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * RespawnGeneratorTest
 */
public class RespawnGeneratorTest {

    private RespawnGenerator r;

    @BeforeEach
    public void instantiate(){ 
        
        r = new RespawnGenerator(null);
    }
}