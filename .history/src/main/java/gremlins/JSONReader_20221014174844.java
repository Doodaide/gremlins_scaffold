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
    private String layout; 
    public JSONReader(String path){
        this.json = loadJSONObject(new File(path));
        this.specs = new HashMap<String, Double>();
        this.layout = "";
    }
    
    public HashMap<String, Double> getSpecs(){
        return this.specs;
    }

    public void setSpecs(HashMap<String, Double> map){
        this.specs = map;
    }

    public String getLayout(){
        return this.layout;
    }

    public void readSpecs(int level){
        try {
            this.specs.put("lives", (double) (json.getInt("lives")));
            levelSpecs = json.getJSONArray("levels");
            JSONObject lvl = levelSpecs.getJSONObject(level);
            this.layout = lvl.getString("layout");
            
            this.specs.put("enemy_cooldown", lvl.getDouble("enemy_cooldown"));
            this.specs.put("wizard_cooldown", lvl.getDouble("wizard_cooldown"));
        } catch (Exception e) {
            throw new Exception(ARGS_DENSITY)
        }
        


    }
}