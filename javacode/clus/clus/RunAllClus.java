/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clus;

import java.io.FileNotFoundException;

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
    //for each dataset
      private static void runAllDatasets(String path) throws FileNotFoundException{
          for(String s: Path.datasets){
              runAllSeeds(path+"/"+s);
          }
      }
      
      //for each seed
      private static void runAllSeeds(String path) throws FileNotFoundException{
          for(int seed = 0; seed<Path.nbSeeds; seed++){
              runAllFolds(path+"/asettings/S"+Integer.toString(seed));
          }
      }
      
      
      //for each fold
      private static void runAllFolds(String path) throws FileNotFoundException{
             for(int i = 0; i<Path.nbFolds; i++){
               String[] arg = {path+"settingsFold"+Integer.toString(i+1)+".s"};
               Clus.main(arg);
             }
      }
      
      //run 1 settings file
      public static void runSegmentation(){
          String[] p = {Path.path + "/" + Path.classifiers[0]+"/"+"mfeatPix"+"/"+"settings1.s"};
          Clus.main(p);
      }
    
    
    
    
    public static void main(String[] args) throws FileNotFoundException{
       runAllClassifiers();
    }
    
}
