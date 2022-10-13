package gremlins;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.dataflow.qual.TerminatesExecution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.google.common.annotations.VisibleForTesting;

import processing.core.PImage;

import org.junit.jupiter.api.*;

@TestInstance(Lifecycle.PER_METHOD)
/**
 * ExitPortalTestND
 */
public class ExitPortalTestND {

    private ExitPortal e; 

    @BeforeEach
    public void instantiate(){
        e = new ExitPortal(0, 0, null);
    }

    @AfterEach
    public void clear(){
        e = null; 
        assertNull(e);
    }

    @Test
    public void constructor(){
        
    }
}