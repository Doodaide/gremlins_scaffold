package gremlins;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Test
    public void test0X_testWizardCreation(){
        
        Wizard w = new Wizard(0, 0, 2, wizardSprites[0],  wizardSprites[1],  wizardSprites[2],  wizardSprites[3],  wizardSprites[4]);
    }
}
