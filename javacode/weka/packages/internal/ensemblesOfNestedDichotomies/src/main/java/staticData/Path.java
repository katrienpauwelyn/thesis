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
      public static  String[] classifiers = {"classBalanced","furthestCentroid","nd","randomPair"};
      public static  String[] datasets = {"audiology","krkopt","letterRecognition","mfeatFac","mfeatFou","mfeatKar","mfeatMor",
        "mfeatPix","optdigits","pageBlocks","pendigits","segmentation","shuttle","vowel","yeast","zoo"};
      public static int nbFolds = 10;
      public static int nbSeeds = 10;
     
      //De originele .arff data files, direct te gebruiken maar nog niet verdeeld in folds.
      public static String pathToOriginalData = "/Users/katie/Dropbox/thesis/data";
       public static String audiologyFile = Path.pathToOriginalData+"/audiology/audiology.arff";
       public static String krkoptFile = Path.pathToOriginalData+"/krkoptChess/krkopt.arff";
       public static String letterRecognitionFile = Path.pathToOriginalData+"/letterRecognition/letterRecognition.arff";
       public static String mfeatFacFile = Path.pathToOriginalData+"/multipleFeatures/mfeatFac.arff";
       public static String mfeatFouFile =Path.pathToOriginalData+ "/multipleFeatures/mfeatFou.arff";
       public static String mfeatKarFile = Path.pathToOriginalData+"/multipleFeatures/mfeatKar.arff";
       public static String mfeatMorFile = Path.pathToOriginalData+"/multipleFeatures/mfeatMor.arff";
       public static String mfeatPixFile = Path.pathToOriginalData+"/multipleFeatures/mfeatPix.arff";
       public static String optdigitsFile = Path.pathToOriginalData+"/optdigits/optdigits.arff";
       public static String pageBlocksFile = Path.pathToOriginalData+"/pageBlocks/pageBlocks.arff";
       public static String pendigitsFile = Path.pathToOriginalData+"/pendigits/pendigits.arff";
       public static String segmentationFile = Path.pathToOriginalData+"/segmentation/segmentation.arff";
       public static String shuttleFile =Path.pathToOriginalData+ "/shuttle/shuttle.arff";
       public static String vowelFile = Path.pathToOriginalData+"/vowel/vowel.arff";
       public static String yeastFile = Path.pathToOriginalData+"/yeast/yeast.arff";
       public static String zooFile = Path.pathToOriginalData+"/zoo/zoo.arff";
       
        public static String[] allDataSetFiles = {audiologyFile, krkoptFile, letterRecognitionFile, mfeatFacFile, mfeatFouFile,
            mfeatKarFile, mfeatMorFile, mfeatPixFile, optdigitsFile, pageBlocksFile, pendigitsFile, segmentationFile, 
            shuttleFile, vowelFile, yeastFile, zooFile};
}
