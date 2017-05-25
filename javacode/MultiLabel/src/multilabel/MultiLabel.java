/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multilabel;

import arff.ArffParser;
import arff.SparseArffParser;
import arff.StringInt;
import combinerenResultaten.Averager;
import combinerenResultaten.Macro;
import combinerenResultaten.Micro;
import hierarchie.HierarchyMaker;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import sFiles.SFileMakerWithHier;
import statics.Path;


/**
 *
 * @author katie
 */
public class MultiLabel {

    /**
     * alle "-" en "/" die in de labels zaten zijn omgezet naar ":"
     */
    public static void main(String[] args) throws IOException {
       //pas de .arff files aan
     //  ArffParser.parseAllStandardArffs();
     //  SparseArffParser.parseAllSparseArffs();
      
      
      
        
        //maak alle hierarchieen

           
        HierarchyMaker.makeAllHierarchies();
        
        //maak de settings files
    //    SFileMakerWithHier.makeAllSFiles();
        
        //--------------------------------
        //in Clus clus laten runnen
        //--------------------------------
        
        //nabewerking van de resultaten
        //maak 1 gemiddelde file per dataset
      //  Averager.makeAllAverageArff();
        
        //micro en macro files
     //   Macro.makeMacroFilesForAllDatasets();
     //   Micro.makeMicroFilesForAllDatasets();
        
        
    }
    
}
