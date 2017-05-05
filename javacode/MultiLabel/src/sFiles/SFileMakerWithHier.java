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
 *s-files voor clus met hierarchie
 */
public class SFileMakerWithHier {
    
      
   public static void makeAllSFiles() throws FileNotFoundException{
       PrintStream stream;
     for(String d: Path.datasets){
         for(int i = 0; i<Path.nbBags; i++){
             stream = new PrintStream(new File(Path.path+d+"/settings"+i+".s"));
             makeSFileEnsemble(stream, Path.path+d+"/"+d+"train.arff", Path.path+d+"/"+d+"test.arff",
                     Path.path+d+"/hier"+d+i, i+1);
             stream.close();
         }
     }
   }
      
   /*
   Setting voor lege verzameling labels:
EmptySetIndicator (binnen [Hierarchical])
bvb EmptySetIndicator = none
   */
      
     private static void makeSFileEnsemble(PrintStream stream, String pathTrain, String pathTest,
             String pathHierarchy, int bagSelection){
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
            stream.println();
            stream.println("[Ensemble]");
            stream.println("Iterations = "+Path.nbBags);
            stream.println("EnsembleMethod = Bagging");
            stream.println("BagSelection=["+bagSelection+","+bagSelection+"]");
      }
      
    
      
      public static void main(String[] args) throws FileNotFoundException{
        makeAllSFiles();
      }
}
