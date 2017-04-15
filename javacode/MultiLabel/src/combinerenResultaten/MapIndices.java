/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package combinerenResultaten;

import java.util.HashMap;

/**
 *
 * @author katie
 */
public class MapIndices {
    
   public HashMap<String, Integer> mapActual;
   public HashMap<String, Integer> mapPredicted;
        
   public MapIndices(){
       mapActual = new HashMap();
       mapPredicted = new HashMap();
   }
   
   public void addActual(String s, int i){
       mapActual.put(s, i);
   }
   
   public void addPredicted(String s, int i){
       mapPredicted.put(s, i);
   }
    
}
