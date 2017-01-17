/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExtractData;

import java.util.ArrayList;

/**
 *
 * @author katie
 */
public class NewElementHierarchy {
    private ArrayList<String> newElements;
    private int index;
    
    public NewElementHierarchy(){
        newElements = new ArrayList<String>();
        index = -1;
    }
    
    public void addNewElement(String element){
        newElements.add(element);
    }
    
    public ArrayList<String> getElements(){
        return newElements;
    }
    
    public int getIndex(){
        return index;
    }
    
    public boolean hasNewElements(){
        return !newElements.isEmpty();
    }
    
    public void setIndex(int i){
        index = i;
    }
    
}
