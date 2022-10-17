package gremlins;

import java.util.ArrayList;

/**
 * DrawMap
 */
public class DrawMap {

    private ArrayList<Immobile> mapToDraw;
    private ArrayList<Gremlin> gArray; 
    private int xCounter = 0; 
    private int yCounter = 0; 
    private int incrementer = 10; 
    int wizStartX = 0, wizStartY = 0;
    public DrawMap(){
        this.gArray = new ArrayList<Gremlin>();
    }

    public void generateMap(ArrayList<Character[]> a, App app){
        for (Character[] j : a){
            for(Character k :j){
                if(k == 'X'){ // stone block
                    app.getMap().add(new Indestructable(xCounter, yCounter, app.stonewall));
                }

                else if (k == ' '){ // Space 
                    ;
                }

                else if (k =='B'){ // Brick
                    app.getMap().add(new Destructable(xCounter, yCounter, this.brickwall, this.brickwallDestruction)); 
                }

                else if (k == 'W'){ // wizard spawnpoint
                    wizStartX = xCounter;
                    wizStartY = yCounter;
                }

                else if (k == 'G'){ // ! Gremlin spawnpoint ... CHANGE LATER 
                    app.getGArray().add(new Gremlin(xCounter, yCounter, this.gremlin, this.slime, this.frozenG, this.frozenS));
                }

                else if (k == 'E'){ // ! Exit ... CHANGE LATER
                    mapToDraw.add(new ExitPortal(xCounter, yCounter, this.portal));
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
}