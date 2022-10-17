package gremlins;
import java.util.*;
import java.io.*;

/**
 * ReadMap
 */
public class ReadMap {
    ArrayList<Character[]> mapContents;
    
    public ReadMap(){
        this.mapContents = new ArrayList<Character[]>(); // temporary list
    }

    public ArrayList<Character[]> getMapContents(){
        return this.mapContents;
    }

    public void parseLayout(String layout){
        try {
            if(layout.equals("")){
                throw new FileNotFoundException("File not found");
            }
            File f = new File(layout); // File f = new File("level0.txt"); //
            Scanner sc = new Scanner(f);
            
            while(sc.hasNextLine()){
                Character [] temp = new String(
                    sc.nextLine().trim().toCharArray()).chars()
                    .mapToObj(c-> (char) c).toArray(Character[]::new);
                this.mapContents.add(temp);
                    
            }
    
            sc.close();
            f = null;
        } catch (FileNotFoundException e) {
            System.err.println("Map file could not be located");
        } catch (IndexOutOfBoundsException e){
            System.out.println("map too big bro");
        }
    }

    // * Parse file
   
}
