package gremlins;

import java.util.ArrayList;

/**
 * UpdateArrayList
 */
public class UpdateArrayList <T> {
    ArrayList<T> temp = new ArrayList<T>();
    public UpdateArrayList(){
        this.temp = null;
    }

    public ArrayList<T> update(ArrayList<T> toBeUpdated){
        
        for(T e : toBeUpdated){
            if(e != null){
                temp.add(e);
            }
        }
        return temp;
    }
}