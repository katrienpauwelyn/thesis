/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import staticData.Path;

/**
 *
 * @author katie
 */
public class MakeSFilesAllSeeds {
    
      
    
    public static void makeSFiles10x10() throws FileNotFoundException{
          for(String s: Path.classifiers){
              makeSFiles10x10AllDatasets(Path.path+"/"+s);
          }
    }
    
    private static void makeSFiles10x10AllDatasets(String path) throws FileNotFoundException{
        for(String s: Path.datasets){
              if(s.equals("segmentation") || s.equals("letterRecognition")){
                  makeSFiles10x10AllSeeds(path+"/"+s, true);
              } else {
                  makeSFiles10x10AllSeeds(path+"/"+s, false);
              }
          }
    }
    
    private static void makeSFiles10x10AllSeeds(String path, boolean indexTargetIsOne) throws FileNotFoundException{
        for(int i = 1; i<11; i++){
            makeSFiles10x10AllFolds(path, indexTargetIsOne, i);
        }
    }
    
    
          //for each fold
      private static void makeSFiles10x10AllFolds(String path, boolean indexTargetIsOne, int seed) throws FileNotFoundException{
             for(int fold = 1; fold<Path.nbFolds+1; fold++){
                 PrintStream p = new PrintStream(new File(path+"/settings"+seed+fold+".s"));
               makeSFile(p,fold, path, indexTargetIsOne, seed);
               p.close();
             }
      }
      
      
      
   
      
      
      
      public static void makeSFiles10x10Ens() throws FileNotFoundException{
          for(String s: Path.classifiers){
              makeSFiles10x10AllDatasetsEns(Path.path+"/"+s);
          }
    }
    
    private static void makeSFiles10x10AllDatasetsEns(String path) throws FileNotFoundException{
        for(String s: Path.datasets){
              if(s.equals("segmentation") || s.equals("letterRecognition")){
                  makeSFiles10x10AllSeedsEns(path+"/"+s, true);
              } else {
                  makeSFiles10x10AllSeedsEns(path+"/"+s, false);
              }
          }
    }
    
    private static void makeSFiles10x10AllSeedsEns(String path, boolean indexTargetIsOne) throws FileNotFoundException{
        for(int i = 1; i<11; i++){
            makeSFiles10x10AllFoldsEns(path, indexTargetIsOne, i);
        }
    }
    
    
          //for each fold
      private static void makeSFiles10x10AllFoldsEns(String path, boolean indexTargetIsOne, int seed) throws FileNotFoundException{
             for(int fold = 1; fold<Path.nbFolds+1; fold++){
                 PrintStream p = new PrintStream(new File(path+"/settings"+seed+fold+".s"));
               makeSFileEnsemble(p, path, indexTargetIsOne,fold, seed);
               p.close();
             }
      }
      
      //the text of the .s files
      //indexTargetIsNul moet true zijn als de dataset letterRecognition of segmentation is
      private static void makeSFile(PrintStream stream, int nbFold, String path, boolean indexTargetIsOne, int seed){
          stream.println("[General]");
          stream.println("RandomSeed = seed");
          stream.println();
          stream.println("[Data]");
          stream.println("File = "+path+"/fold"+Integer.toString(nbFold)+".arff");
          stream.println("TestSet = "+path+"/test"+Integer.toString(nbFold)+".arff");
          stream.println();
          stream.println("[Hierarchical]");
          stream.println("Type = TREE");
          stream.println("HSeparator = /");
          stream.println("SingleLabel = Yes");
          stream.println();
          stream.println("[Model]");
          stream.println("MinimalWeight = 1.0");
          stream.println();
          stream.println("[Output]");
          stream.println("AllFoldErrors = Yes");
          stream.println("WritePredictions = Test");
          stream.println();
          stream.println("[Tree]");
          stream.println("FTest= 0.05");
          if(indexTargetIsOne){
            stream.println();
            stream.println("[Attributes]");
            stream.println("Target = 1");
          }
         
      }
      
      private static void makeSFileEnsemble(PrintStream stream, String path, boolean indexTargetIsOne, int nbFold, int seed){
          stream.println("[General]");
          stream.println("RandomSeed = seed");
          stream.println();
          stream.println("[Data]");
          stream.println("File = "+path+"/fold"+nbFold+".arff");
          stream.println("TestSet = "+path+"/test"+Integer.toString(nbFold)+".arff");
          stream.println();
          stream.println("[Hierarchical]");
          stream.println("Type = TREE");
          stream.println("HSeparator = /");
          stream.println("SingleLabel = Yes");
          stream.println();
          stream.println("[Model]");
          stream.println("MinimalWeight = 1.0");
          stream.println();
          stream.println("[Output]");
          stream.println("AllFoldErrors = Yes");
          stream.println("WritePredictions = Test");
          stream.println();
          stream.println("[Tree]");
          stream.println("FTest= 0.05");
          if(indexTargetIsOne){
            stream.println();
            stream.println("[Attributes]");
            stream.println("Target = 1");
          }
          stream.println();
          stream.println("[Ensemble]");
          stream.println("Iterations = 75"); // of 100
          stream.println("EnsembleMethod = Bagging");
          stream.println("VotingType = Majority");// of    ProbabilityDistribution
      }
      
    
}
