/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import staticData.Path;

/**
 * Als de namen van de files nog steeds te lang zijn na vervanging met een enkele letter:
 * verwijder de "-" in de hierarchie 
 * Wordt enkel gedaan op de files die effectief te lang zijn
 * @author katie
 */
public class RemovePlatteStreep {
    
    public static String[] datasetsClassBalanced = {"audiology"};
    public static String[] datasetsFurthestCentroid = {"audiology", "letterRecognition", "segmentation"};
    public static String[] datasetsND = {"audiology","krkopt", "letterRecognition", "segmentation", "vowel"};
    public static String[] datasetsRandomPair = {"audiology", "krkopt", "letterRecognition", "segmentation"};
 
  public static void removeStrepen() throws IOException{  
        PrintStream stream = new PrintStream(new File(Path.path+"/platteStreepScript.sh"));
        stream.println("#!/bin/bash");
        for(String dataset: datasetsClassBalanced){
            removeStrepenConcreet("classBalanced", dataset, stream);
        }
        for(String dataset: datasetsFurthestCentroid){
            removeStrepenConcreet("furthestCentroid", dataset, stream);
        }
        for(String dataset: datasetsND){
            removeStrepenConcreet("nd", dataset, stream);
        }
        for(String dataset: datasetsRandomPair){
            removeStrepenConcreet("randomPair", dataset, stream);
        }
  }
        
        private static void removeStrepenConcreet(String classifier, String dataset, PrintStream stream){
              String path = Path.path+"/"+classifier+"/"+dataset+"/";
               
               for(int seed = 0; seed<Path.nbSeeds; seed++){
                   for(int fold = 1; fold<Path.nbFolds+1; fold++){
                       File trainFile = new File(path+"S"+seed+"fold"+fold+".arff");
                       stream.println("perl -pi -e 's/-//g' "+trainFile.getAbsolutePath());
                       File testFile = new File(path+"S"+seed+"test"+fold+".arff");
                       stream.println("perl -pi -e 's/-//g' "+testFile.getAbsolutePath());
                   }
               }
           }

    public static void main(String[] args) throws IOException{
        removeStrepen();
    }
    
    
    
}
