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
public class SFileMakerWithoutHier {
    
    public static void makeAllSFiles() throws FileNotFoundException{
       PrintStream stream;
     for(String d: Path.datasets){
        
             stream = new PrintStream(new File(Path.path+d+"/settings.s"));
             makeSFileEnsemble(stream, Path.pathPinac+d+"/"+d+"trainFlat.arff", Path.pathPinac+d+"/"+d+"testFlat.arff"
                     );
             stream.close();
         
     }
   }
      
   /*
   Setting voor lege verzameling labels:
EmptySetIndicator (binnen [Hierarchical])
bvb EmptySetIndicator = none
   */
      
     private static void makeSFileEnsemble(PrintStream stream, String pathTrain, String pathTest){
            stream.println("[Data]");
            stream.println("File = " + pathTrain);
            stream.println("TestSet = "+ pathTest);
            stream.println();
            stream.println("[Hierarchical]");
            stream.println("Type = TREE");
            stream.println("HSeparator = /");
            stream.println("SingleLabel = No");
            stream.println("EmptySetIndicator = None");
            stream.println();
            stream.println("[Output]");
            stream.println("AllFoldErrors = Yes");
            stream.println("WritePredictions = Test");
            stream.println();
            stream.println("[Ensemble]");
            stream.println("Iterations = "+Path.nbBags);
            stream.println("EnsembleMethod = Bagging");
      }
      
    
      
      public static void main(String[] args) throws FileNotFoundException{
        makeAllSFiles();
      }
}
