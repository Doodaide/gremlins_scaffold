package gremlins;

import java.util.ArrayList;

/**
 * UpdateArrayList class: a helper class used to remove any null entries from an array fed 
 */
public class UpdateArrayList <T> {
    public UpdateArrayList(){
    }

    public ArrayList<T> update(ArrayList<T> toBeUpdated){
        ArrayList<T> temp = new ArrayList<T>();
        for(T e : toBeUpdated){
            if(e != null){
                temp.add(e);
            }
        }
        return temp;
    }
}