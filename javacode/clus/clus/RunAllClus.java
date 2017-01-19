/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 *
 * @author katie
 */
public class RunAllClus {

            //for each classifier
      public static void runAllClassifiers() throws FileNotFoundException{
          for(String s: Path.classifiers){
              runAllDatasets(Path.path+"/"+s);
          }
      }
      
      
      //   public static  String[] classifiers = {"classBalanced","furthestCentroid","nd","randomPair"};
   
      public static void runND() throws FileNotFoundException{
          runAllDatasets(Path.path+"/"+Path.classifiers[2]);
      }
      
      public static void runClassBalanced() throws FileNotFoundException{
          runAllDatasets(Path.path+"/"+Path.classifiers[0]);
      }
      
      public static void runFurthestCentroid() throws FileNotFoundException{
          runAllDatasets(Path.path+"/"+Path.classifiers[1]);
      }
      
      public static void runRandomPair() throws FileNotFoundException{
          runAllDatasets(Path.path+"/"+Path.classifiers[3]);
      }
      
      
    //for each dataset
      private static void runAllDatasets(String path) throws FileNotFoundException{
          for(String s: Path.datasets){
               PrintStream timeStream = new PrintStream(new File(
               path+"/"+s+"/asettings/CLUSaTime.txt"));

              runAllSeeds(path+"/"+s, timeStream);
             timeStream.close();
              /**
               *   System.setOut(outFalse);
                     
                    ClassBalancedND.main(getArgs(false, s, trainPath, testPath, Integer.toString(seed)));
                    
               */
              
          }
      }
      
      //for each seed
      private static void runAllSeeds(String path, PrintStream timeStream) throws FileNotFoundException{
          for(int seed = 0; seed<Path.nbSeeds; seed++){
              runAllFolds(path+"/asettings/S"+Integer.toString(seed), timeStream);
          }
      }
      
      
      //for each fold
      private static void runAllFolds(String path, PrintStream timeStream) throws FileNotFoundException{
             for(int i = 0; i<Path.nbFolds; i++){
               String[] arg = {path+"settingsFold"+Integer.toString(i+1)+".s"};
               long startTime = System.nanoTime();
               Clus.main(arg);
                long endTime = System.nanoTime();
                long durationMiliSeconds = (endTime - startTime)/1000000;
                timeStream.println(durationMiliSeconds);
             }
      }
  
    
    
    
    
    public static void main(String[] args) throws FileNotFoundException{
       runAllClassifiers();
    }
    
}
