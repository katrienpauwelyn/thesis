/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hsc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import staticData.Path;
import statistics.MakeSFilesAllSeeds;

/**
 *
 * @author katie
 */
public class SettingsFileCreator {
    
   public static void makeSFiles10x10() throws FileNotFoundException{
          for(String s: Path.classifiers){
              makeSFiles10x10AllDatasets(Path.path+"/"+s);
          }
    }
    
    //for each dataset
    private static void makeSFiles10x10AllDatasets(String path) throws FileNotFoundException{
        String[] datasets = {"segmentation","letterRecognition"};
        for(String s: datasets){
                  makeSFiles10x10AllSeeds(path+"/"+s, false);
          }
    }
    
    //for each seed
    private static void makeSFiles10x10AllSeeds(String path, boolean indexTargetIsOne) throws FileNotFoundException{
        for(int seed = 0; seed<Path.nbSeeds; seed++){
            makeSFiles10x10AllFolds(path+"/asettings/hscS"+seed, indexTargetIsOne, 
                    seed, path);
        }
    }
    
    
          //for each fold
      private static void makeSFiles10x10AllFolds(String pathToSFile, boolean indexTargetIsOne, 
              int seed, String pathToDataFiles) throws FileNotFoundException{
             for(int fold = 1; fold<Path.nbFolds+1; fold++){
                 PrintStream p = new PrintStream(new File(pathToSFile+"settingsFold"+fold+".s"));
               MakeSFilesAllSeeds.makeSFile(p,fold, pathToDataFiles, indexTargetIsOne, seed);
               p.close();
             }
      }
    
      
      
    
}
