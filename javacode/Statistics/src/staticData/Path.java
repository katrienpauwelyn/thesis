/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticData;

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
      

/**
 * Nodig voor het aanpassen van de datasets om hsc te kunnen toepassen
 */
      public static  String[] restrictedDatasets = {"mfeatFac","mfeatFou","mfeatKar","mfeatMor",
        "mfeatPix","optdigits","pageBlocks","pendigits","shuttle","vowel","yeast",
        "zoo"};
      public static String[] datasetsNotFirst = {//"letterRecognition",
          "segmentation"};
      
      public static String[] datasetShortening = {//"audiology",
         // "krkopt","yeast",
      "segmentation"};
      public static String[] alfabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m",
      "n","o","p","q","r","s","t","u","v","w","x","y","z"};
      public static String[] audiologyClasses = {"mixed_cochlear_age_fixation","mixed_cochlear_age_otitis_media",
          "normal_ear","cochlear_poss_noise","cochlear_age_and_noise","acoustic_neuroma","mixed_cochlear_unk_ser_om","conductive_discontinuity",
          "retrocochlear_unknown","conductive_fixation","bells_palsy","cochlear_noise_and_heredity","mixed_cochlear_unk_fixation",
          "mixed_poss_noise_om","otitis_media","possible_menieres","possible_brainstem_disorder","cochlear_age_plus_poss_menieres",
          "mixed_cochlear_age_s_om","mixed_cochlear_unk_discontinuity","mixed_poss_central_om","poss_central"
      ,"cochlear_unknown","cochlear_age"};
      public static String[] segmentationClasses = {"BRICKFACE","SKY","FOLIAGE","CEMENT","WINDOW","PATH","GRASS"};
      
      public static String[] krkoptClasses = {"draw","zero","one","two","three","fourteen","four","five","sixteen","six","seven","eight",
          "nine","ten","eleven","twelve","thirteen","fifteen"};
      
      
      public static String[] yeastClasses = {"MIT","NUC","CYT","ME1","EXC","ME2","ME3","VAC","POX","ERL"};
      public static String[][] shorteningClasses = {segmentationClasses};//audiologyClasses, krkoptClasses, yeastClasses};
      
      public static String[] currentlyWorking = {"mfeatFac","mfeatFou","mfeatKar","mfeatMor",
        "mfeatPix","optdigits","pageBlocks","pendigits","shuttle","vowel",
        "yeast",
        "zoo", "segmentation"};
      //letterRecognition, segmentation
      
       public static String[] datasetsClassBalanced = {"audiology"};
    public static String[] datasetsFurthestCentroid = {"audiology", "letterRecognition", "segmentation"};
    public static String[] datasetsND = {//"audiology",
        //"krkopt", 
        //"letterRecognition", 
        //"segmentation", 
        "vowel"};
    public static String[] datasetsRandomPair = {//"audiology", "krkopt", "letterRecognition", 
        "segmentation"};
    
    //datasetes die wel gedaan en geslaagd zijn
    public static String[] restrictedND =  {"mfeatFac","mfeatFou","mfeatKar","mfeatMor",
        "mfeatPix","optdigits","pageBlocks","pendigits","segmentation","shuttle","yeast","zoo"};
    public static String[] restrictedRandomPair =  {"mfeatFac","mfeatFou","mfeatKar","mfeatMor",
        "mfeatPix","optdigits","pageBlocks","pendigits","segmentation","shuttle","vowel","yeast","zoo"};
       
      
      
}
