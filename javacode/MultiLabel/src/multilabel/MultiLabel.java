/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multilabel;

import arff.ArffParser;
import arff.SparseArffParser;
import arff.StringInt;
import hierarchie.HierarchyMaker;
import java.io.IOException;
import java.util.HashMap;
import sFiles.SFileMaker;


/**
 *
 * @author katie
 */
public class MultiLabel {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       //pas de .arff files aan
       HashMap<String, StringInt> standard =  ArffParser.parseAllStandardArffs();
      HashMap<String, StringInt> sparse =  SparseArffParser.parseAllSparseArffs();
        
        //maak alle hierarchieen
            HashMap<String, String> normalMap = new HashMap();
            HashMap<String, String> sparseMap = new HashMap();
            HashMap<String, Integer> intMap = new HashMap();
            for(String s: standard.keySet()){
            
                normalMap.put(s, standard.get(s).stringPart);
                intMap.put(s, standard.get(s).intPart+1);
            }
            
            for(String s: sparse.keySet()){
                sparseMap.put(s, sparse.get(s).stringPart);
                 intMap.put(s, sparse.get(s).intPart+1);
            }
           
        HierarchyMaker.makeAllHierarchies(normalMap, sparseMap, intMap);
        
        //maak de settings files
        SFileMaker.makeAllSFiles();
        
        //--------------------------------
        //in Clus clus laten runnen
        //--------------------------------
        
        //nabewerking van de resultaten
        
        
        
    }
    
}
