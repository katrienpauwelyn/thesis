/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hierarchie;

import java.util.ArrayList;

/**
 *
 * @author katie
 */
public class Node {
    
    private String hierarchy;
    private ArrayList<String> classes; 
    
    public Node(String hier, ArrayList<String> c){
        classes = c;
        hierarchy = hier;
    }
    
    public void setHier(String s){
        hierarchy = s;
    }
    
    public void setClasses(ArrayList<String> c){
        classes = c;
    }
    
    public int nbClasses(){
        return classes.size();
    }
    
    public String getAndRemoveClass(int index){
        return classes.remove(index);
    }
    
    public ArrayList<String> getClasses(){
        return classes;
    }
    
    public String getHierarchy(){
        return hierarchy;
    }
    
    public void addClass(String c){
        classes.add(c);
    }
    
    public void appendToHier(String s){
        hierarchy = hierarchy.concat(s);
    }
}
