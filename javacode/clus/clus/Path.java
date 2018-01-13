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
      
      //switch hier - pinacs: 
      //multilabelpath
      //multilabeldatasets
      //nbbags
      
      //multilabel
       public static String multilabelPath = "/Users/katie/thesiscode/datasets/multilabel/";
             //
       public static String pathPinac =  "/export/home1/NoCsBack/thesisdt/s0212310/nieuweDatasets/";
//"/home/s0212310/multilabel/";
   /* public static String[] multilabelDatasets = {"bibtex","corel5k","delicious","emotions","enron",
        //"eurlex",
        "flags","genbase","mediamill","yeast"};*/
    
     
    
   public static String[] multilabelStandardDatasets = {"birds","CAL500","Corel16k","flags","genbase","nuscVLADplus"};
    public static String[] multilabelSparseDatasets = {"Computers",
        "eurlex-dc",//"eurlex-ev",
        "eurlex-sm","rcv1subset1","tmc2007"};
    
    public static String[] multilabelDatasets = {"birds",//"CAL500",
        "Computers","Corel16k",
        "eurlex-dc",//"eurlex-ev",
        "eurlex-sm","flags","genbase", "nuscVLADplus","rcv1subset1","tmc2007"};
    
    public static String[] tempTodo = {"eurlex-dc","eurlex-sm" };//,"birds","CAL500","Corel16k","flags","genbase","nuscVLADplus"};
  public static String[] dataset = {"flags"};//mediamill
  //ok: flags, yeast, corel5k, emotions, genbase
      
  public static int nbBags = 50;
 
  public static String[] flatTodo = {"birds",//"CAL500",
        "Computers","Corel16k",
        "eurlex-dc",//"eurlex-ev",
        "eurlex-sm","flags","genbase"};
}
