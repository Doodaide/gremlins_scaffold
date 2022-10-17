package gremlins;
import processing.data.JSONObject;
import processing.core.PApplet;
import processing.data.JSONArray;
import java.io.*;
import java.util.HashMap;

/**
 * JSONReader class: a helper class that reads a JSON file. 
 * An object of this class has a hashmap attribute which contains the content in the json file. 
 */
public class JSONReader extends PApplet{
    /**
     * Json object 
     */
    private JSONObject json;

    /**
     * The JSONArray that is inside the json file
     */
    private JSONArray levelSpecs; 

    /**
     * Hashmap that contains the contents of the json file
     */
    private HashMap<String, Double> specs; 
    private String layout; 
    public JSONReader(String path){
        this.json = loadJSONObject(new File(path));
        this.specs = new HashMap<String, Double>();
        this.specs.put("lives", (double) (json.getInt("lives")));
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

    public void readSpecs(int level) throws Exception{
        try {
            
            levelSpecs = json.getJSONArray("levels");
            JSONObject lvl = levelSpecs.getJSONObject(level);
            this.layout = lvl.getString("layout");
            this.specs.put("enemy_cooldown", lvl.getDouble("enemy_cooldown"));
            this.specs.put("wizard_cooldown", lvl.getDouble("wizard_cooldown"));
        } catch (Exception e) {
            throw new Exception("Invalid input");
        }
        


    }
}