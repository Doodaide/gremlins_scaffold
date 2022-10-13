package gremlins;


import processing.core.PApplet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {

    @Test
    public void test0X_testWizardCreation(){
        
        Wizard w = new Wizard(0, 0, 2, this.wizardSprites[0], this.wizardSprites[1], this.wizardSprites[2], this.wizardSprites[3], this.wizardSprites[4]);
    }
}
