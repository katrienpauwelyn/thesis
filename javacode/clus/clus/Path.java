/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clus;

/**
 *
 * @author katie
 */
public class Path {
      public static  String path = "/Users/katie/thesisoutput/out";
      public static String pathDatasets = "/Users/katie/thesiscode/datasets";
      public static  String[] classifiers = {"classBalanced","furthestCentroid","nd","randomPair"};
      public static  String[] datasets = {"audiology","krkopt","letterRecognition","mfeatFac","mfeatFou","mfeatKar","mfeatMor",
        "mfeatPix","optdigits","pageBlocks","pendigits","segmentation","shuttle","vowel","yeast","zoo"};
      public static int nbFolds = 10;
      public static int nbSeeds = 10;
      
      public static int getIndexOfClassAttribute(String s){
          if(s.equals("letterRecognition")||s.equals("segmentation")){
              return 1;
          } else {
              return -1;
          }
      }
      
      
      
      //multilabel
       public static String multilabelPath = "/Users/katie/thesiscode/datasets/multilabel/";
    public static String[] multilabelDatasets = {"bibtex","corel5k","delicious","emotions","enron","eurlex","flags","genbase","mediamill","yeast"};
    public static String[] multilabelStandardDatasets = {"corel5k","emotions","flags","genbase","mediamill","yeast"};
    public static String[] multilabelSparseDatasets = { "bibtex","delicious","enron","eurlex"};
    
  
      
 
}
