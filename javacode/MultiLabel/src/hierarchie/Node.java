/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hierarchie;

import java.io.PrintStream;
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
  
    public void removeClass(String s){
        classes.remove(s);
    }
    
//update de hierarchie met "/" en alle knopen
    //als er maar 1 klasse meer zit, druk de hierarchy af (enkel voor de leaf nodes)
    public void updateAndPrintHierarchy(PrintStream stream){
        hierarchy = hierarchy.concat(",");
        for(String s: classes){
            hierarchy = hierarchy.concat(s+"-");
        }
        hierarchy = hierarchy.substring(0, hierarchy.length()-1);
        if(classes.size()==1){
           stream.println(hierarchy);
        }
    }
   
    public String classesToString(){
        String s = "";
        for(String c: classes){
            s=s.concat(c+" ");
            
        }
        return s;
    }
   
    public String toString(){
        return hierarchy+" - "+classesToString();
    }

}
