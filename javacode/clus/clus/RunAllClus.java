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
          public static  String path = "/Users/katie/NetBeansProjects/weka/trunk/packages/internal/ensemblesOfNestedDichotomies/out";
      public static  String[] classifiers = {"classBalanced","furthestCentroid","nd","randomPair"};
      public static  String[] datasets = {"audiology","krkopt","letterRecognition","mfeatFac","mfeatFou","mfeatKar","mfeatMor",
        "mfeatPix","optdigits","pageBlocks","pendigits","segmentation","shuttle","vowel","yeast","zoo"};
      public static int nbFolds = 10;
      
            //for each classifier
      public static void runAllClassifiers() throws FileNotFoundException{
          for(String s: classifiers){
              runAllDatasets(path+"/"+s);
          }
      }
    //for each dataset
      private static void runAllDatasets(String path) throws FileNotFoundException{
          for(String s: datasets){
              runAllFolds(path+"/"+s);
          }
      }
      //for each fold
      private static void runAllFolds(String path) throws FileNotFoundException{
             for(int i = 0; i<nbFolds; i++){
               String[] arg = {path+"/"+"settings"+Integer.toString(i+1)+".s"};
               Clus.main(arg);
             }
      }
      
      //run 1 settings file
      public static void runSegmentation(){
          String[] p = {path + "/" + classifiers[0]+"/"+"mfeatPix"+"/"+"settings1.s"};
          Clus.main(p);
      }
    
    
    
    
    public static void main(String[] args) throws FileNotFoundException{
       runAllClassifiers();
    }
    
}
