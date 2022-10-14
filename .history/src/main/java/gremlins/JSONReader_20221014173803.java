package gremlins;
import processing.data.JSONObject;
import processing.core.PApplet;
import processing.data.JSONArray;
import java.io.*;
/**
 * JSONReader
 */
import java.util.HashMap;
public class JSONReader extends PApplet{

    private JSONObject json; // config file 
    private JSONArray levelSpecs; // contents of said file 
    private HashMap<String, Double> specs; 
    public JSONReader(String path){
        this.json = loadJSONObject(new File(path));
        this.specs = new HashMap<String, Double>();
    }
    
    public HashMap<String, Double> getSpecs(){
        return this.specs;
    }

    public void setSpecs(HashMap<String, Double> map){
        this.specs = map;
    }

    public void readSpecs(int level){
        this.specs.put("lives", (double) (json.getInt("lives")));
        if(level == 1){
            
        }
        else if (level == 2){

        }
    }
        this.lives = ;
        //this.livesToDraw = this.lives;
        levelSpecs = json.getJSONArray("levels");
        String layout = "";
        if(!passedLvl2 && passedLvl1){
            JSONObject level2 = levelSpecs.getJSONObject(1);
            layout = level2.getString("layout");
            this.enemyCooldown = level2.getDouble("enemy_cooldown") * FPS;
            this.wizardCooldown = level2.getDouble("wizard_cooldown") * FPS;
            this.level = 2;
        }

        else if (!passedLvl1){
            JSONObject level1 = levelSpecs.getJSONObject(0);
            layout = level1.getString("layout");
            this.enemyCooldown = level1.getDouble("enemy_cooldown") * FPS;
            this.wizardCooldown = level1.getDouble("wizard_cooldown") * FPS;
            this.level = 1;
        }
}