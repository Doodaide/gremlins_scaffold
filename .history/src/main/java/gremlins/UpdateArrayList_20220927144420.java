package gremlins;

import java.util.ArrayList;

/**
 * UpdateArrayList
 */
public class UpdateArrayList <T> {

    public UnsupportedAddressTypeException

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