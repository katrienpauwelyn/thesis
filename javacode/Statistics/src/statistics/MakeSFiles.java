/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package statistics;

import staticData.Path;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * @author katie
 */
public class MakeSFiles {

     
      //for each classifier
      public static void makeSFilesAll() throws FileNotFoundException{
          for(String s: Path.classifiers){
              makeSFilesAllDatasets(Path.path+"/"+s);
          }
      }
    //for each dataset
      private static void makeSFilesAllDatasets(String path) throws FileNotFoundException{
          for(String s: Path.datasets){
              if(s.equals("segmentation") || s.equals("letterRecognition")){
                  makeSFilesAllFolds(path+"/"+s, true);
              } else {
                  makeSFilesAllFolds(path+"/"+s, false);
              }
          }
      }
      //for each fold
      private static void makeSFilesAllFolds(String path, boolean indexTargetIsNul) throws FileNotFoundException{
             for(int i = 0; i<Path.nbFolds; i++){
                 PrintStream p = new PrintStream(new File(path+"/settings"+Integer.toString(i+1)+".s"));
               makeSFile(p,i+1, path, indexTargetIsNul);
               p.close();
             }
      }
   
      //the text of the .s files
      //indexTargetIsNul moet true zijn als de dataset letterRecognition of segmentation is
      private static void makeSFile(PrintStream stream, int nbFold, String path, boolean indexTargetIsNul){
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
          if(indexTargetIsNul){
            stream.println();
            stream.println("[Attributes]");
            stream.println("Target = 1");
          }
         
      }
      
      private static void makeSFileEnsemble(PrintStream stream, String path, boolean indexTargetIsOne, int nbFold){
          
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
          stream.println("Iterations = 100"); // of 100
          stream.println("EnsembleMethod = Bagging");
          stream.println("VotingType = Majority");// of    ProbabilityDistribution
      }
      
      
            //for each classifier
      public static void makeSFilesEnsembleAll() throws FileNotFoundException{
          for(String s: Path.classifiers){
              makeSFilesEnsembleAllDatasets(Path.path+"/"+s);
          }
      }
    //for each dataset
      private static void makeSFilesEnsembleAllDatasets(String path) throws FileNotFoundException{
          for(String s: Path.datasets){
             
              if(s.equals("segmentation") || s.equals("letterRecognition")){
                  //makeSFileEnsemble(p, path+"/"+s, true);
                  makeSFilesEnsembleAllFolds(path+"/"+s, true);
              } else {
                  //makeSFileEnsemble(p, path+"/"+s, false);
                  makeSFilesEnsembleAllFolds(path+"/"+s, false);
              }
             
          }
      }
      
      //for each fold
      private static void makeSFilesEnsembleAllFolds(String path, boolean indexTargetIsOne) throws FileNotFoundException{
          for(int i = 1; i<11; i++){
               PrintStream stream = new PrintStream(new File(path+"/"+"/settingsEnsemble"+i+".s"));
               makeSFileEnsemble(stream, path, indexTargetIsOne,i);
               stream.close();
          }
      }

      
    
    public static void main(String[] args) throws FileNotFoundException{
      //  makeSFilesAll();
        makeSFilesEnsembleAll();
    }
    
    
    
    
    
    
    
    
    
    
    
    
  
}
