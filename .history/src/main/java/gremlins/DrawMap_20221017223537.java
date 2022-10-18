package gremlins;

import java.util.ArrayList;

/**
 * DrawMap class: A helper class that reads the character ArrayList produced by the 
 * ReadMap class. This method puts all of the correct objects in the right locations, and has the attribute of the wizard's starting location
 */
public class DrawMap {
    /**
     * the row incrrementer 
     */
    private int xCounter = 0; 

    /**
     * The row incrementer 
     */
    private int yCounter = 0; 
    private int incrementer = 20; 
    private int wizStartX, wizStartY;
    public DrawMap(){
        this.wizStartX = 0;
        this.wizStartY = 0;

    }

    public void generateMap(ArrayList<Character[]> a, App app){
        
        for (Character[] j : a){
            for(Character k :j){
                if(k == 'X'){ // stone block
                    app.getMap().add(new Indestructable(xCounter, yCounter, app.stonewall));
                }

                else if (k =='B'){ // Brick
                    app.getMap().add(new Destructable(xCounter, yCounter, app.brickwall, app.brickwallDestruction)); 
                }

                else if (k == 'W'){ // wizard spawnpoint
                    wizStartX = xCounter;
                    wizStartY = yCounter;
                }

                else if (k == 'G'){ // ! Gremlin spawnpoint ... CHANGE LATER 
                    app.getGArray().add(new Gremlin(xCounter, yCounter, app.gremlin, app.slime, app.frozenG, app.frozenS));
                }

                else if (k == 'E'){ // ! Exit ... CHANGE LATER
                    app.getMap().add(new ExitPortal(xCounter, yCounter, app.portal));
                }

                else if (k == 'P'){
                    app.setPowerUpCoords(xCounter, yCounter);
                }
                xCounter += incrementer; 
            }
            yCounter += incrementer;
            xCounter = 0;
        }
    }

    public int[] getWizardStart(){
        int[] coords = {this.wizStartX, wizStartY};
        return coords;
    }
}