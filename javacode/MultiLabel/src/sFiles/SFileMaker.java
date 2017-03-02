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
 * Maakt de s-files voor clus. Er wordt rekening gehouden met alle seeds.
 * 10x10: 10 fold 10 cross validation: 10 seeds met elk 10 folds
 */
public class SFileMaker {
    
      
   public static void makeAllSFiles() throws FileNotFoundException{
       PrintStream stream;
       for(String dataset: Path.datasets){
           stream = new PrintStream(new File(Path.path+dataset+"/settings.s"));
           if(dataset.equals("genbase")){
               makeSFileEnsemble(stream, Path.path+dataset+"/"+dataset+"train.arff",
                   Path.path+dataset+"/"+dataset+"test.arff", true, 1);
           } else {
               makeSFileEnsemble(stream, Path.path+dataset+"/"+dataset+"train.arff",
                   Path.path+dataset+"/"+dataset+"test.arff", false, 0);
           }
           
       }
   }
      
   
      
     private static void makeSFileEnsemble(PrintStream stream, String pathTrain, String pathTest,
             boolean hasKey, int keyIndex){
          stream.println("[Data]");
          stream.println("File = "+pathTrain);//TODO
          stream.println("TestSet = "+pathTest);//TODO
          stream.println();
          stream.println("[Hierarchical]");
          stream.println("Type = TREE");
          stream.println("HSeparator = /");
          stream.println("SingleLabel = No");
          stream.println();
          stream.println("[Model]");
          stream.println("MinimalWeight = 1.0");
          stream.println();
          if(hasKey){
              stream.println("[Attributes]");
              stream.println("Key = "+keyIndex);
              stream.println();
          }
          stream.println("[Output]");
          stream.println("AllFoldErrors = Yes");
          stream.println("WritePredictions = Test");
          stream.println();
          stream.println("[Ensemble]");
          stream.println("Iterations = 10"); // of 100
          stream.println("EnsembleMethod = Bagging");
          stream.println("VotingType = Majority");// of    ProbabilityDistribution
      }
      
    
      
      public static void main(String[] args) throws FileNotFoundException{
        makeAllSFiles();
      }
}
