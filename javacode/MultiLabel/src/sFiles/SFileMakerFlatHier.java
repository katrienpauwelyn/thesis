/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import statics.Path;

/**
 *
 * @author katie
 */
public class SFileMakerFlatHier {
      
   public static void makeAllSFiles() throws FileNotFoundException{
       PrintStream stream;
     for(String d: Path.datasets){
         for(int i = 0; i<Path.nbBags; i++){
             int index = i+1;
             stream = new PrintStream(new File(Path.pathPinac+d+"/flat/settingsFlat"+i+".s"));
             makeSFileEnsemble(stream, Path.pathPinac+d+"/"+"settings-bag-"+index+".arff", Path.pathPinac+d+"/"+d+"test.arff",
                     Path.pathPinac+d+"/flat/flatHier.txt");
             stream.close();
         }
     }
   }//settings-bag-7
      
   /*
   Setting voor lege verzameling labels:
EmptySetIndicator (binnen [Hierarchical])
bvb EmptySetIndicator = none
   */
   
   
   //TODO: pathtrain verandert voor iedere bag
   //ensemble mag uit staan
      
     private static void makeSFileEnsemble(PrintStream stream, String pathTrain, String pathTest,
             String pathHierarchy){
            stream.println("[General]");
            stream.println("RandomSeed = 0");
            stream.println();
            stream.println("[Data]");
            stream.println("File = " + pathTrain);
            stream.println("TestSet = "+ pathTest);
            stream.println();
            stream.println("[Hierarchical]");
            stream.println("Type = DAG");
            stream.println("HSeparator = /");
            stream.println("DefinitionFile = "+pathHierarchy);
            stream.println("EmptySetIndicator = None");
            stream.println();
            stream.println("[Output]");
            stream.println("AllFoldErrors = Yes");
            stream.println("WritePredictions = Test");
      }
      
    
      
      public static void main(String[] args) throws FileNotFoundException{
        makeAllSFiles();
      }
}
